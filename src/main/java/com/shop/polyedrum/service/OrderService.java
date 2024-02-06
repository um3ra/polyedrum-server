package com.shop.polyedrum.service;

import com.shop.polyedrum.domain.Order;
import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.dto.BucketDTO;
import com.shop.polyedrum.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    String createOrder(BucketDTO bucketDTO, User user);
    List<Order> getAllByUser(User user);
    List<OrderDTO> getOrders();
}
