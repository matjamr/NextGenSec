package com.sec.gen.next.outbound.controller;

import com.sec.gen.next.outbound.model.KafkaReceiveModel;
import com.sec.gen.next.outbound.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.context.Context;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SmokeController {

    private final EmailService emailService;

    @SneakyThrows
    @GetMapping()
    public void smoke() {
        var a = new KafkaReceiveModel()
                .setEmail("jamr.mat@gmail.com")
                .setParams(Map.of("email", "jamr.mat@gmail.com"))
                .setStrategy("ACCOUNT_CREATE");

        Context context = new Context();
        a.getParams().forEach(context::setVariable);

        emailService.sendHtmlMail("jamr.mat@gmail.com", "Subject Here", "accountCreate", context);
    }
}
