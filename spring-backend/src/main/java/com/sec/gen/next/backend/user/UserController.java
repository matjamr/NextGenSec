package com.sec.gen.next.backend.user;

import com.sec.gen.next.backend.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.api.internal.ClaimsUser;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.user.service.UserService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

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

    @GetMapping("/{email}")
    public User findByEmail(@PathVariable final String email, final ServletRequest servletRequest) {

        return userService.findUserByEmail(email);
    }

    @PutMapping
    public User updateData(@RequestBody AdditionalInformationUpdateModel additionalInformationUpdateModel,
                           final ServletRequest servletRequest) {
        return userService.update(additionalInformationUpdateModel);
    }
}
