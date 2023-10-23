package com.shop.polyedrum.dto;


import com.shop.polyedrum.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketDetails {
    private String title;
    private Long productId;
    private BigDecimal price;
    private Double amount;
    private BigDecimal sum;
    private String imageURL;


    public BucketDetails(Product product) {
        this.title = product.getTitle();
        this.productId = product.getId();
        this.imageURL = product.getImageURL();
        this.price = product.getPrice();
        this.amount = 1.0;
        this.sum = product.getPrice();
    }
}
