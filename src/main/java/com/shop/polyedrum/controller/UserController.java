package com.shop.polyedrum.controller;


import com.shop.polyedrum.dto.ResponseHandler;
import com.shop.polyedrum.dto.UserDTO;
import com.shop.polyedrum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getUserDTOById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long id){
        return ResponseHandler.responseBuilder("", HttpStatus.OK,userService.updateUser(userDTO, id));
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getUserProfile(@RequestHeader(value="Authorization", defaultValue = "") String token){
        return ResponseHandler.responseBuilder("", HttpStatus.OK, userService.findUserByJwt(token));
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(){
        return ResponseHandler.responseBuilder("", HttpStatus.OK, userService.getUsers());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        return ResponseHandler.responseBuilder(userService.deleteUser(id), HttpStatus.OK, "");
    }
}
