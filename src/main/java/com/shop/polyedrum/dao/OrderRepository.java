package com.shop.polyedrum.dao;

import com.shop.polyedrum.domain.Order;
import com.shop.polyedrum.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user, Sort sort);
}
