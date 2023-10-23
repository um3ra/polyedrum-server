package com.shop.polyedrum.dao;

import com.shop.polyedrum.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
