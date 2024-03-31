package com.sec.next.gen.userservice.controller;

import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.service.internal.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicing")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserModel saveUser(@RequestBody UserModel userModel) {
        return userService.saveUser(userModel);
    }

    @GetMapping("/{email}")
    public UserModel getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PutMapping
    public UserModel updateUser(@RequestBody UserModel userModel) {
        return userService.updateUser(userModel);
    }
}
