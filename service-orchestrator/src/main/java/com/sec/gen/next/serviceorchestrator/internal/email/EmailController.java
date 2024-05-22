package com.sec.gen.next.serviceorchestrator.internal.email;

import com.next.gen.api.Email;
import com.next.gen.sec.model.MailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RequestMapping("/api/email")
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final Consumer<MailModel> saveEmailService;
    private final Supplier<List<Email>> simpleQueryEmailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmail(@RequestBody MailModel mailModel) {
        saveEmailService.accept(mailModel);
    }

    @GetMapping
    public List<Email> getEmails() {
        return simpleQueryEmailService.get();
    }
}
