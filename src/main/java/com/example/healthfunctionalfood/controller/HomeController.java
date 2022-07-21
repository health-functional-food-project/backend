package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.advice.Success;
import com.example.healthfunctionalfood.service.home.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Success> getHomeMain(Pageable pageable) {
        return new ResponseEntity<>(new Success("홈 메인 완료", homeService.getHomeMain(pageable)), HttpStatus.OK);
    }

    @GetMapping("/api/home/category")
    public ResponseEntity<Success> getHomeMain(@RequestParam("category") String category) {

        return new ResponseEntity<>(new Success("홈 메인 완료", homeService.getProductRankingListWithCategory(category)), HttpStatus.OK);
    }
}
