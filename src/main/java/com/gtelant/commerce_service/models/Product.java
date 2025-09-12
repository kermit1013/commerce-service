package com.gtelant.commerce_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    @Column(name ="reference", nullable = false)
    private String reference;

    @Column(name ="width", nullable = false)
    private double width;

    @Column(name ="height", nullable = false)
    private double height;

    @Column(name ="price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "sales", nullable = false)
    private int sales;

    @Column(name ="description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name= "category_id")
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
