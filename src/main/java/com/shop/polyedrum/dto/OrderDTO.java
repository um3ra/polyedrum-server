package com.shop.polyedrum.dto;


import com.shop.polyedrum.domain.OrderDetails;
import com.shop.polyedrum.domain.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date date;
    private BigDecimal sum;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private List<OrderDetails> orderDetails;
}