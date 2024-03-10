package com.shop.polyedrum.service.impl;

import com.shop.polyedrum.dao.BucketRepository;
import com.shop.polyedrum.dao.ProductRepository;
import com.shop.polyedrum.domain.Bucket;
import com.shop.polyedrum.domain.Product;
import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.dto.BucketDTO;
import com.shop.polyedrum.dto.BucketDetails;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;

    @Override
    public BucketDTO getBucketByUser(User user) {
        if (user == null || user.getBucket() == null){
            return new BucketDTO();
        }

        BucketDTO bucket = new BucketDTO();
        Map<Long, BucketDetails> mapByProductId = new HashMap<>();
        List<Product> products = user.getBucket().getProducts();

        for (Product pr : products){
            BucketDetails bucketDetails = mapByProductId.get(pr.getId());
            if(bucketDetails == null){
                mapByProductId.put(pr.getId(), new BucketDetails(pr));
            }else{
                bucketDetails.setAmount(bucketDetails.getAmount() + 1.0);
                bucketDetails.setSum(bucketDetails.getSum().add(pr.getPrice()));
            }
        }
        bucket.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucket.aggregate();
        return bucket;
    }

    @Override
    public Bucket createBucket(List<Long> productsIDs, User user) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> products = getCollectRefProductsByIds(productsIDs);
        bucket.setProducts(products);
        return bucketRepository.save(bucket);
    }

    @Override
    public void addProducts(List<Long> productIDs, Bucket bucket) {
        List<Product> products = bucket.getProducts();
        List<Product> newProducts = products == null ?
                new ArrayList<>() : new ArrayList<>(products);

        newProducts.addAll(getCollectRefProductsByIds(productIDs));
        bucket.setProducts(newProducts);
        bucketRepository.save(bucket);
    }

    @Override
    public String addProductToUserBucket(Long productID, User user) {

        if (user == null){
            throw new ApiException("User is null", HttpStatus.BAD_REQUEST);
        }
        Bucket bucket = user.getBucket();
        if (bucket == null){
            createBucket(Collections.singletonList(productID), user);
        }else {
            addProducts(Collections.singletonList(productID), bucket);
        }
        return "product added to bucket";
    }

    @Transactional
    @Override
    public String deleteProductFromBucket(Long productId, User user) {
        Bucket bucket = user.getBucket();

        List<Product> products = bucket.getProducts();
        for(Product pr : products){
            if (pr.getId().equals(productId)){
                products.remove(pr);
                break;
            }
        }
        bucket.setProducts(products);
        return "product deleted";
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIDs){
        return productIDs.stream().map(productRepository::getOne).collect(Collectors.toList());
    }
}