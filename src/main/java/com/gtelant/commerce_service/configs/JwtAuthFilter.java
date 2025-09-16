package com.gtelant.commerce_service.configs;

import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.repositories.UserRepository;
import com.gtelant.commerce_service.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    //過濾請求/賦予權限
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 從http headers中獲取Authorization欄位-> Bearer ......
        String authHeader = request.getHeader("Authorization");
        // 檢查Authorization格式是否正確
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            //該次請求過濾器結束生命週期=>將請求繼續往下傳遞...
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // 若開頭格式（Bearer...）正確，則擷取第七字元開始的字串（實際jwť）
            String jwtToken = authHeader.substring(7);
            String email = jwtService.getEmailFromToken(jwtToken);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // db裡面找到對應的username
                Optional<User> user = userRepository.findByEmail(email);
                if (user.isPresent()) {
                    //*** 若使用Spring Security (library)必須包含 授權 (Authorization)邏輯->「該用戶能做什麼？」***
                    List<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.get().getRole()));
                    //該token並非jwt token，而是Spring Security內部使用的token(包含user & authorities)
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.get(), null, authorities);
                    // 將 內部使用的token 投進 Spring Security 認證箱（SecurityContextHolder）
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            // Simply re-throw the exception to be handled by the container
            throw new ServletException(e);
        }
        //該次請求過濾器結束生命週期=>將請求繼續往下傳遞...
        filterChain.doFilter(request, response);
    }
}
