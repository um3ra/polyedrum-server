package com.shop.polyedrum.controller;

import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.dto.ResponseHandler;
import com.shop.polyedrum.service.BucketService;
import com.shop.polyedrum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bucket")
@RequiredArgsConstructor
public class BucketController {
    private final BucketService bucketService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getBucket(@RequestHeader(value = "Authorization", defaultValue = "") String token){
        User user = checkingUserByToken(token);
        return ResponseHandler.responseBuilder("", HttpStatus.OK, bucketService.getBucketByUser(user));
    }

    @PutMapping("add/{id}")
    public ResponseEntity<Object> addItemToCart(@RequestHeader(value = "Authorization", defaultValue = "") String token,
                                                @PathVariable Long id){
        User user = checkingUserByToken(token);

        return ResponseHandler.responseBuilder(bucketService.addProductToUserBucket(id, user), HttpStatus.OK, "");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteProductFromBucket(@RequestHeader(value = "Authorization", defaultValue = "") String token,
                                                          @PathVariable Long id){
        User user = checkingUserByToken(token);
        return ResponseHandler.responseBuilder(bucketService.deleteProductFromBucket(id, user), HttpStatus.OK, "");
        }

    private User checkingUserByToken(String token){
        return token.equals("") ? null :
                userService.findUserByJwt(token);
    }
}
