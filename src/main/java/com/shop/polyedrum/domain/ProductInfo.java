package com.shop.polyedrum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products_info")
public class ProductInfo {

    private static final String SEQ_NAME = "product_info_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    @JsonIgnore
    private Long id;
    private String publisher;
    private Double weight;

    @Column(length = 512)
    private String description;
    private Long numberOfPages;
}