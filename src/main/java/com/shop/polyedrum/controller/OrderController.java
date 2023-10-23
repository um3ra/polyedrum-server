package com.shop.polyedrum.controller;

import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.dto.BucketDTO;
import com.shop.polyedrum.dto.ResponseHandler;
import com.shop.polyedrum.service.BucketService;
import com.shop.polyedrum.service.OrderService;
import com.shop.polyedrum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final BucketService bucketService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getOrders(){
        return ResponseHandler
                .responseBuilder("", HttpStatus.OK, orderService.getOrders());
    }

    @PostMapping
    public ResponseEntity<Object> checkOut(@RequestHeader(value = "Authorization", defaultValue = "") String token){
        User user = userService.findUserByJwt(token);
        BucketDTO bucketDTO = bucketService.getBucketByUser(user);
        return ResponseHandler
                .responseBuilder(orderService.createOrder(bucketDTO, user), HttpStatus.OK, "");
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getOrders(@RequestHeader(value = "Authorization", defaultValue = "") String token){
        User user = userService.findUserByJwt(token);
        return ResponseHandler.responseBuilder("", HttpStatus.OK, orderService.getAllByUser(user));
    }
}
