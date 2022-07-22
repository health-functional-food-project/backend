package com.example.healthfunctionalfood.service.health;

import com.example.healthfunctionalfood.advice.exception.ApiRequestException;
import com.example.healthfunctionalfood.domain.openapi.HealthToken;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.request.HealthRequestDto;
import com.example.healthfunctionalfood.repository.HealthTokenRepository;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HealthServiceImpl implements HealthService{

    private final UserRepository userRepository;

    private final TilkoServiceImpl tilkoService;

    private final HealthTokenRepository healthTokenRepository;

    @Override
    @Transactional
    public void addHealthInsuranceLoginToken(HealthRequestDto.userInfo userInfo) throws ParseException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        // 로그인 미완성으로 임시 하드코딩
        // 카카오 로그인으로 사용자 정보 받는 로직 추가 필요
        User user = userRepository.findById(1L).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 사용자입니다."));

        String sign = tilkoService.getSign(userInfo);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(sign);
        JSONObject resultData = (JSONObject) jsonObject.get("ResultData");

        String cxId = (String) resultData.get("CxId");
        String reqTxId = (String) resultData.get("ReqTxId");
        String txId = (String) resultData.get("TxId");
        String token = (String) resultData.get("Token");

        Optional<HealthToken> healthTokenOptional = healthTokenRepository.findByUserId(user.getId());
        if(healthTokenOptional.isEmpty()){
            HealthToken healthToken = HealthToken.builder()
                    .user(user)
                    .cxId(cxId)
                    .reqTxId(reqTxId)
                    .token(token)
                    .txId(txId)
                    .build();
            healthTokenRepository.save(healthToken);
        }else {
            healthTokenOptional.get().updateHealthToken(cxId, reqTxId, token, txId);
        }
    }
}