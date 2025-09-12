package com.gtelant.commerce_service.services;

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
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
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
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            if(stockFrom != null && stockTo == null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("stock"), stockFrom));
            }

            if(stockFrom != null && stockTo != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("stock"), stockFrom));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("stock"), stockTo));
            }

            Predicate[] predicateArray = predicates.toArray(new Predicate[0]);
            return criteriaBuilder.and(predicateArray);
        }));
    }
}
