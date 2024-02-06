package com.shop.polyedrum.service.impl;

import com.shop.polyedrum.dao.UserRepository;
import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.dto.UserDTO;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.exception.ResourceNotFoundException;
import com.shop.polyedrum.security.JwtService;
import com.shop.polyedrum.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("User", "Id", id)
        );
    }

    @Override
    public UserDTO getUserDTOById(Long id){
        return toDto(getUserById(id));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(this :: toDto).collect(Collectors.toList());
    }

    private UserDTO toDto(User user){
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    public String deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
        return "user deleted successfully";
    }

    @Override
    public String updateUser(UserDTO userDTO, Long id) {
        User user = getUserById(id);
        boolean changed = false;

        if (userDTO.getFirstName() != null && !userDTO.getFirstName().isEmpty()){
            user.setFirstName(userDTO.getFirstName());
            changed = true;
        }
        if (userDTO.getLastName() != null && !userDTO.getLastName().isEmpty()){
            user.setLastName(userDTO.getLastName());
            changed = true;
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()){
            user.setEmail(userDTO.getEmail());
            changed = true;
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            if (!userDTO.getPassword().equals(userDTO.getMatchingPassword())){
                throw new ApiException("Password mismatch", HttpStatus.BAD_REQUEST);
            }
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            changed = true;
        }
        if (changed) userRepository.save(user);

        return "";
    }

    @Override
    public User findUserByJwt(String token){

        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return user;
    }
}