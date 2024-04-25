package com.shop.polyedrum.service.impl;

import com.shop.polyedrum.dao.BucketRepository;
import com.shop.polyedrum.dao.OrderDetailsRepository;
import com.shop.polyedrum.dao.OrderRepository;
import com.shop.polyedrum.dao.ProductRepository;
import com.shop.polyedrum.domain.Order;
import com.shop.polyedrum.domain.OrderDetails;
import com.shop.polyedrum.domain.OrderStatus;
import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.dto.BucketDTO;
import com.shop.polyedrum.dto.BucketDetails;
import com.shop.polyedrum.dto.OrderDTO;
import com.shop.polyedrum.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;
    private final BucketRepository bucketRepository;

    @Transactional(readOnly = true)
    @Override
    public List<OrderDTO> getOrders(){
        return orderRepository
                .findAll()
                .stream()
                .map(this::mapOrderToDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getAllByUser(User user) {
        return orderRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "dateOfCreation"));
    }

    @Transactional
    @Override
    public String createOrder(BucketDTO bucketDTO, User user) {
        Order order = Order
                .builder()
                .status(OrderStatus.NEW)
                .dateOfCreation(new Date())
                .sum(bucketDTO.getSum())
                .user(user)
                .build();

        for(BucketDetails bucketDetails : bucketDTO.getBucketDetails()){
            OrderDetails orderDetails = OrderDetails
                    .builder()
                    .order(order)
                    .product(productRepository
                            .findById(bucketDetails.getProductId())
                            .orElseThrow(null))
                    .amount(BigDecimal.valueOf(bucketDetails.getAmount()))
                    .sum(bucketDetails.getSum())
                    .build();
            orderDetailsRepository.save(orderDetails);
        }
        orderRepository.save(order);

        bucketRepository.deleteById(user.getBucket().getId());
        user.setBucket(null);
        return "order created successfully";
    }

    private OrderDTO mapOrderToDTO(Order order){
        return OrderDTO
                .builder()
                .id(order.getId())
                .firstName(order.getUser().getFirstName())
                .lastName(order.getUser().getLastName())
                .orderDetails(order.getOrderDetails())
                .build();
    }
}
