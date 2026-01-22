package com.elliekavanagh.rummikub.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "Rummikub API is running";
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
