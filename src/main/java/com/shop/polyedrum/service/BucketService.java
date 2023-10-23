package com.shop.polyedrum.service;

import com.shop.polyedrum.domain.Bucket;
import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    void addProducts(List<Long> products, Bucket bucket);
    Bucket createBucket(List<Long> products, User user);
    String addProductToUserBucket(Long productID, User user);
    BucketDTO getBucketByUser(User user);
    String deleteProductFromBucket(Long productId, User user);
    String deleteBucketById(Long id);
}