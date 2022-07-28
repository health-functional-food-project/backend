package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.advice.Success;
import com.example.healthfunctionalfood.service.compare.CompareServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/compare")
public class CompareController {

    private final CompareServiceImpl compareService;

    @GetMapping("")
    public ResponseEntity<Success> getCompare(@RequestParam("firstProductId") Long firstProductId,
                                              @RequestParam("secondProductId") Long secondProductId) {

        return new ResponseEntity<>(new Success("비교하기 데이터 ", compareService.getCompareProduct(firstProductId, secondProductId)), HttpStatus.OK);
    }
}
