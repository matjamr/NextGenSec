package com.sec.gen.next.backend.user;

import com.sec.gen.next.backend.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.api.external.SensitiveDataModel;
import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.external.UserPlaceModel;
import com.sec.gen.next.backend.api.internal.RegisterSource;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.user.service.UserService;
import jakarta.servlet.ServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/verify")
    public UserModel verifyUser() {
        return userService.verify();
    }

    @PostMapping("/verify/place")
    public User verifyUserWithPlace(@RequestBody UserPlaceModel userPlaceModel) {
        return userService.verifyPlace(userPlaceModel);
    }

    @GetMapping("/{email}")
    public User findByEmail(@PathVariable final String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping("/update")
    @Transactional
    public UserModel updateData(@RequestBody UserModel userModel) {
        return userService.update(userModel);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserModel userModel, @RequestHeader RegisterSource source) {
        return userService.save(userModel, source);
    }

    @PostMapping("/add")
    public User registerUser(@RequestBody UserModel userModel) {
        return userService.save(userModel, RegisterSource.JWT);
    }

    @GetMapping
    public List<UserModel> findAllUsers(@RequestHeader(required = false, defaultValue = "false") boolean placeRestriction) {
        return userService.findAll(placeRestriction);
    }

    @PostMapping("/sensitive")
    public List<SensitiveDataModel> addSensitiveData(@RequestBody SensitiveDataModel sensitiveDataModel) {
        return userService.addSensitiveData(sensitiveDataModel);
    }

    @PostMapping("/oauth2")
    public UserModel oauth2Login(@RequestHeader RegisterSource source) {
        return userService.oauth2Login(source);
    }

    @GetMapping("/sensitive/{email}")
    public List<SensitiveDataModel> getSensitiveData(@PathVariable String email) {
        return userService.getSensitiveData(email);
    }
}
