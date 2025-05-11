package org.example.diplomamainservice.Client;

import org.example.userservice.ui.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8083")
public interface UserServiceClient {

    @PostMapping(path = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    UserDTO createUser(@RequestBody UserDTO userDTO);

    @GetMapping(path = "/api/users/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);

    @GetMapping(path = "/api/users/email/{email}")
    UserDTO getUserByEmail(@PathVariable("email") String email);

    @GetMapping(path = "/api/users")
    List<UserDTO> getAllUsers();
}