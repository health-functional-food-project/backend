package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.advice.Success;
import com.example.healthfunctionalfood.dto.request.HealthRequestDto;
import com.example.healthfunctionalfood.dto.response.MyPageResponseDto;
import com.example.healthfunctionalfood.service.health.HealthService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HealthController {

    private final HealthService healthService;

    @PostMapping("/health-insurance-login")
    ResponseEntity<Success> healthInsuranceLoginTokenAdd(@RequestBody HealthRequestDto.userInfo userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, ParseException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        healthService.addHealthInsuranceLoginToken(userInfo);
        return new ResponseEntity<>(new Success("건강보험공단 간편 로그인 성공!",""), HttpStatus.OK);
    }

    @PostMapping("/treatment-medication-information")
    public ResponseEntity<Success> DiagnosisAdd(@RequestBody HealthRequestDto.userInfo userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        List<MyPageResponseDto.Treatment> treatmentList = healthService.addDiagnosis(userInfo);
        return new ResponseEntity<>(new Success("진료 및 투악정보 조회 성공!",treatmentList),HttpStatus.OK);
    }

    @PostMapping("/health-check-up")
    ResponseEntity<Success> healthCheckAdd(@RequestBody HealthRequestDto.userInfo userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        healthService.addHealthCheck(userInfo);
        return new ResponseEntity<>(new Success("건강검진내역 조회 성공!",""),HttpStatus.OK);
    }
}
