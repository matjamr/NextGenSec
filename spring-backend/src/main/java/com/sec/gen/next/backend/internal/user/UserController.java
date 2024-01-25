package com.sec.gen.next.backend.internal.user;

import com.sec.gen.next.backend.internal.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.internal.api.external.SensitiveDataModel;
import com.sec.gen.next.backend.internal.api.external.UserModel;
import com.sec.gen.next.backend.internal.api.external.UserPlaceModel;
import com.sec.gen.next.backend.internal.api.internal.ClaimsUser;
import com.sec.gen.next.backend.internal.api.internal.User;
import com.sec.gen.next.backend.internal.user.service.UserService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final static String PRINCIPAL = "PRINCIPAL";

    @PostMapping("/verify")
    public User verifyUser(ServletRequest servletRequest) {
        return userService.verify((ClaimsUser) servletRequest.getAttribute(PRINCIPAL));
    }

    @PostMapping("/verify/place")
    public User verifyUserWithPlace(ServletRequest servletRequest, @RequestBody UserPlaceModel userPlaceModel) {
        return userService.verifyPlace((ClaimsUser) servletRequest.getAttribute(PRINCIPAL), userPlaceModel);
    }

    @GetMapping("/{email}")
    public User findByEmail(@PathVariable final String email, final ServletRequest servletRequest) {
        return userService.findUserByEmail(email);
    }

    @PutMapping
    public User updateData(@RequestBody AdditionalInformationUpdateModel additionalInformationUpdateModel,
                           final ServletRequest servletRequest) {
        return userService.update(additionalInformationUpdateModel);
    }

    @GetMapping
    public List<UserModel> findAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/sensitive")
    public List<SensitiveDataModel> addSensitiveData(ServletRequest servletRequest, @RequestBody SensitiveDataModel sensitiveDataModel) {
        return userService.addSensitiveData((ClaimsUser) servletRequest.getAttribute(PRINCIPAL), sensitiveDataModel);
    }

    @GetMapping("/sensitive/{email}")
    public List<SensitiveDataModel> getSensitiveData(@PathVariable String email) {
        return userService.getSensitiveData(email);
    }
}
