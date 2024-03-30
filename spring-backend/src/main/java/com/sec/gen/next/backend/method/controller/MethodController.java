package com.sec.gen.next.backend.method.controller;

import com.sec.gen.next.backend.api.external.MethodModel;
import com.sec.gen.next.backend.method.service.MethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/method")
@RestController
@RequiredArgsConstructor
public class MethodController {

    private final MethodService methodService;

    @PostMapping
    public void addMethod(@RequestBody MethodModel methodModel) {
        methodService.add(methodModel);
    }

    @DeleteMapping
    public void deleteMethod(@RequestBody MethodModel methodModel) {
        methodService.delete(methodModel);
    }


    @PutMapping
    public void updateMethod(@RequestBody MethodModel methodModel) {
        methodService.update(methodModel);
    }

    @GetMapping
    public List<MethodModel> findAll() {
        return methodService.findAll();
    }
}
