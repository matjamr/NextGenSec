package com.sec.gen.next.serviceorchestrator.internal.email;

import com.next.gen.api.Email;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.MailRetrieveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@RequestMapping("/api/email")
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final Consumer<MailModel> saveEmailService;
    private final Function<Pageable, Page<MailRetrieveResponse>> simpleQueryEmailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmail(@RequestBody MailModel mailModel) {
        saveEmailService.accept(mailModel);
    }

    @GetMapping
    public Page<MailRetrieveResponse> getEmails(Pageable pageable) {
        return simpleQueryEmailService.apply(pageable);
    }
}
