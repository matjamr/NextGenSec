package com.sec.next.gen.userservice.controller;

import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.service.internal.user.SaveUserContext;
import com.sec.next.gen.userservice.service.internal.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicing")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserModel saveUser(@RequestBody(required = false) UserModel userModel,
                              @RequestHeader(value = "source", required = false) RegistrationSource source,
                              @RequestHeader(value = "token", required = false) String token) {
        return userService.saveUser(new SaveUserContext()
                .setUserModel(userModel)
                .setToken(token)
                .setRegistrationSource(source));
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
