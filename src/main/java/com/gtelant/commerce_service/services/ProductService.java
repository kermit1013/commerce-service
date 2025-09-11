package com.gtelant.commerce_service.services;

import com.gtelant.commerce_service.dtos.UserResponse;
import com.gtelant.commerce_service.models.Category;
import com.gtelant.commerce_service.models.Product;
import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.models.UserSegment;
import com.gtelant.commerce_service.repositories.ProductRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> getAllProduct(String query, Integer categoryId, Integer stockFrom, Integer stockTo, PageRequest pageRequest) {
        Specification<Product> spec = productSpecification(query, categoryId, stockFrom, stockTo);
        return  productRepository.findAll(spec, pageRequest);
    }

    private Specification<Product> productSpecification(String queryName, Integer categoryId, Integer stockFrom, Integer stockTo) {
        return (((root, query, criteriaBuilder) ->  {
            List<Predicate> predicates = new ArrayList<>();
            if(queryName != null && !queryName.isEmpty()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("reference")), "%"+ queryName.toLowerCase()+"%")
                ));
            }

            if(categoryId != null) {
//                Join<Product , Category> productCategoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            if(stockFrom != null && stockTo != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("stock"), stockFrom));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("stock"), stockTo));
            }

            Predicate[] predicateArray = predicates.toArray(new Predicate[0]);
            return criteriaBuilder.and(predicateArray);
        }));
    }

    private Specification<User> userSpecification(String queryName, Boolean hasNewsletter, Integer segmentId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // if predicates.size() = 3 how many "AND"? => 2
            //if predicates.size() = 8  how many "AND"? => 7

            if(queryName != null && !queryName.isEmpty()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%"+ queryName.toLowerCase()+"%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%"+ queryName.toLowerCase()+"%")
                ));
            }
            if(hasNewsletter != null) {
                predicates.add(criteriaBuilder.equal(root.get("hasNewsletter"), hasNewsletter));
            }

            if(segmentId != null) {
                Join<User , UserSegment> userUserSegmentJoin = root.join("userSegments");
                predicates.add(criteriaBuilder.equal(userUserSegmentJoin.get("segment").get("id"), segmentId));

                //如果 userSegment有 屬性segmentId 則可以直接使用
                //predicates.add(criteriaBuilder.equal(userUserSegmentJoin.get("segmentId"), segmentId));

                //如果欲查詢Segment參數為字串（name）=> segmentName
                //predicates.add(criteriaBuilder.equal(userUserSegmentJoin.get("segment").get("name"), segmentName)
            }

            Predicate[] predicateArray = predicates.toArray(new Predicate[0]);
            return criteriaBuilder.and(predicateArray);
        });
    }
}
