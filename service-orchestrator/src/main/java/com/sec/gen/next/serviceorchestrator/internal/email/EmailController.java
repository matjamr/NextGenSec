package com.sec.gen.next.serviceorchestrator.internal.email;

import com.next.gen.sec.model.MailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;

@RequestMapping("/api/email")
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final Consumer<MailModel> saveEmailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmail(@RequestBody MailModel mailModel) {
        saveEmailService.accept(mailModel);
    }
}
