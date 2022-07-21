package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.service.home.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public void test (){
        log.info("HomeController");
    }

    @GetMapping("/api/home")
    public String getHomeMain(Pageable pageable) {
        homeService.getHomeMain(pageable);
        return "success";
    }
}
