package com.example.healthfunctionalfood.service.health;

import com.example.healthfunctionalfood.domain.openapi.HealthToken;
import com.example.healthfunctionalfood.dto.request.HealthRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class TilkoServiceImpl implements TilkoService {

    String apiHost = "";
    String apiKey = "";

    public String getSign(HealthRequestDto.userInfo userInfo) throws IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        TilkoServiceImpl tc = new TilkoServiceImpl();

        // RSA Public Key 조회
        String rsaPublicKey = tc.getPublicKey();

        // AES Secret Key 및 IV 생성
        byte[] aesKey = new byte[16];
        new Random().nextBytes(aesKey);

        byte[] aesIv = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};


        // AES Key를 RSA Public Key로 암호화
        String aesCipherKey = rsaEncrypt(rsaPublicKey, aesKey);

        // API URL 설정
        // HELP: https://tilko.net/Help/Api/POST-api-apiVersion-NhisSimpleAuth-SimpleAuthRequest
        String url = tc.apiHost + "api/v1.0/NhisSimpleAuth/SimpleAuthRequest";


        // API 요청 파라미터 설정
        JSONObject json = new JSONObject();
        json.put("PrivateAuthType", "0");
        json.put("UserName", tc.aesEncrypt(aesKey, aesIv, userInfo.getUserName()));
        json.put("BirthDate", tc.aesEncrypt(aesKey, aesIv, new String(String.valueOf(userInfo.getBirthDate()))));
        json.put("UserCellphoneNumber", tc.aesEncrypt(aesKey, aesIv, new String(String.valueOf(userInfo.getUserCellphoneNumber()))));


        // API 호출
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("API-KEY", tc.apiKey)
                .addHeader("ENC-KEY", aesCipherKey)
                .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), json.toJSONString())).build();

        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseStr);

        return responseStr;
    }

    public String getHealthCheckUp(HealthRequestDto.userInfo userInfo, HealthToken healthToken) throws IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        TilkoServiceImpl healthCheckUp = new TilkoServiceImpl();


        // RSA Public Key 조회
        String rsaPublicKey = healthCheckUp.getPublicKey();

        // AES Secret Key 및 IV 생성
        byte[] aesKey = new byte[16];
        new Random().nextBytes(aesKey);

        byte[] aesIv = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};


        // AES Key를 RSA Public Key로 암호화
        String aesCipherKey = rsaEncrypt(rsaPublicKey, aesKey);

        // API URL 설정
        // HELP: https://tilko.net/Help/Api/POST-api-apiVersion-NhisSimpleAuth-Ggpab003M0105
        String url = healthCheckUp.apiHost + "api/v1.0/NhisSimpleAuth/Ggpab003M0105";


        // 간편인증 요청 후 받은 값 정리
        JSONObject reqData = new JSONObject();
        reqData.put("CxId", healthToken.getCxId());
        reqData.put("PrivateAuthType", "0");
        reqData.put("ReqTxId", healthToken.getReqTxId());
        reqData.put("Token", healthToken.getToken());
        reqData.put("TxId", healthToken.getTxId());
        reqData.put("UserName", userInfo.getUserName());
        reqData.put("BirthDate", userInfo.getBirthDate());
        reqData.put("UserCellphoneNumber", userInfo.getUserCellphoneNumber());


        // API 요청 파라미터 설정
        JSONObject json = new JSONObject();

        // 위에서 정의한 reqData 객체를 활용해보세요.
        json.put("CxId", (String) reqData.get("CxId"));
        json.put("PrivateAuthType", (String) reqData.get("PrivateAuthType"));
        json.put("ReqTxId", (String) reqData.get("ReqTxId"));
        json.put("Token", (String) reqData.get("Token"));
        json.put("TxId", (String) reqData.get("TxId"));
        json.put("UserName", healthCheckUp.aesEncrypt(aesKey, aesIv, (String) reqData.get("UserName")));
        json.put("BirthDate", healthCheckUp.aesEncrypt(aesKey, aesIv, (String) reqData.get("BirthDate")));
        json.put("UserCellphoneNumber", healthCheckUp.aesEncrypt(aesKey, aesIv, (String) reqData.get("UserCellphoneNumber")));

        // API 호출
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("API-KEY", healthCheckUp.apiKey)
                .addHeader("ENC-KEY", aesCipherKey)
                .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), json.toJSONString())).build();

        Response response = client.newCall(request).execute();
        String responseStr = Objects.requireNonNull(response.body()).string();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseStr);
        String message = (String) jsonObject.get("Message");

        return responseStr;
    }

    public String getDiagnosis(HealthRequestDto.userInfo userInfo, HealthToken healthToken) throws IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        TilkoServiceImpl diagnosis = new TilkoServiceImpl();

        // RSA Public Key 조회
        String rsaPublicKey		= diagnosis.getPublicKey();

        // AES Secret Key 및 IV 생성
        byte[] aesKey			= new byte[16];
        new Random().nextBytes(aesKey);

        byte[] aesIv			= new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

        // AES Key를 RSA Public Key로 암호화
        String aesCipherKey		= rsaEncrypt(rsaPublicKey, aesKey);

        // API URL 설정
        // HELP: https://tilko.net/Help/Api/POST-api-apiVersion-NhisSimpleAuth-RetrieveCareDescList
        String url				= diagnosis.apiHost + "api/v1.0/NhisSimpleAuth/RetrieveCareDescList";

        // 간편인증 요청 후 받은 값 정리
        JSONObject reqData = new JSONObject();
        reqData.put("CxId", healthToken.getCxId());
        reqData.put("PrivateAuthType", "0");
        reqData.put("ReqTxId", healthToken.getReqTxId());
        reqData.put("Token", healthToken.getToken());
        reqData.put("TxId", healthToken.getTxId());
        reqData.put("UserName", userInfo.getUserName());
        reqData.put("BirthDate", userInfo.getBirthDate());
        reqData.put("UserCellphoneNumber", userInfo.getUserCellphoneNumber());

        // API 요청 파라미터 설정
        JSONObject json			= new JSONObject();

        // 위에서 정의한 reqData 객체를 활용해보세요.
        json.put("CxId"                 , (String) reqData.get("CxId"));
        json.put("PrivateAuthType"      , (String) reqData.get("PrivateAuthType"));
        json.put("ReqTxId"              , (String) reqData.get("ReqTxId"));
        json.put("Token"                , (String) reqData.get("Token"));
        json.put("TxId"                 , (String) reqData.get("TxId"));
        json.put("UserName"             , diagnosis.aesEncrypt(aesKey, aesIv, (String) reqData.get("UserName")));
        json.put("BirthDate"            , diagnosis.aesEncrypt(aesKey, aesIv, (String) reqData.get("BirthDate")));
        json.put("UserCellphoneNumber"  , diagnosis.aesEncrypt(aesKey, aesIv, (String) reqData.get("UserCellphoneNumber")));
        json.put("기타필요한파라미터"       , diagnosis.aesEncrypt(aesKey, aesIv, (String) reqData.get("UserName")));

        // API 호출
        OkHttpClient client		= new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request			= new Request.Builder()
                .url(url)
                .addHeader("API-KEY"			, diagnosis.apiKey)
                .addHeader("ENC-KEY"			, aesCipherKey)
                .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), json.toJSONString())).build();

        Response response		= client.newCall(request).execute();
        return response.body().string();
    }

    public String getMedication(HealthRequestDto.userInfo userInfo, HealthToken healthToken) throws IOException, ParseException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        TilkoServiceImpl medication = new TilkoServiceImpl();

        // RSA Public Key 조회
        String rsaPublicKey		= medication.getPublicKey();

        // AES Secret Key 및 IV 생성
        byte[] aesKey			= new byte[16];
        new Random().nextBytes(aesKey);

        byte[] aesIv			= new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

        // AES Key를 RSA Public Key로 암호화
        String aesCipherKey		= rsaEncrypt(rsaPublicKey, aesKey);

        // API URL 설정
        // HELP: https://tilko.net/Help/Api/POST-api-apiVersion-NhisSimpleAuth-RetrieveTreatmentInjectionInformationPerson
        String url				= medication.apiHost + "api/v1.0/NhisSimpleAuth/RetrieveTreatmentInjectionInformationPerson";

        // 간편인증 요청 후 받은 값 정리
        JSONObject reqData = new JSONObject();
        reqData.put("CxId", healthToken.getCxId());
        reqData.put("PrivateAuthType", "0");
        reqData.put("ReqTxId", healthToken.getReqTxId());
        reqData.put("Token", healthToken.getToken());
        reqData.put("TxId", healthToken.getTxId());
        reqData.put("UserName", userInfo.getUserName());
        reqData.put("BirthDate", userInfo.getBirthDate());
        reqData.put("UserCellphoneNumber", userInfo.getUserCellphoneNumber());

        // API 요청 파라미터 설정
        JSONObject json			= new JSONObject();

        // 위에서 정의한 reqData 객체를 활용해보세요.
        json.put("CxId"                 , (String) reqData.get("CxId"));
        json.put("PrivateAuthType"      , (String) reqData.get("PrivateAuthType"));
        json.put("ReqTxId"              , (String) reqData.get("ReqTxId"));
        json.put("Token"                , (String) reqData.get("Token"));
        json.put("TxId"                 , (String) reqData.get("TxId"));
        json.put("UserName"             , medication.aesEncrypt(aesKey, aesIv, (String) reqData.get("UserName")));
        json.put("BirthDate"            , medication.aesEncrypt(aesKey, aesIv, (String) reqData.get("BirthDate")));
        json.put("UserCellphoneNumber"  , medication.aesEncrypt(aesKey, aesIv, (String) reqData.get("UserCellphoneNumber")));
        json.put("기타필요한파라미터"       , medication.aesEncrypt(aesKey, aesIv, (String) reqData.get("UserName")));

        // API 호출
        OkHttpClient client		= new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request			= new Request.Builder()
                .url(url)
                .addHeader("API-KEY"			, medication.apiKey)
                .addHeader("ENC-KEY"			, aesCipherKey)
                .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), json.toJSONString())).build();

        Response response		= client.newCall(request).execute();

        return response.body().string();
    }

    // AES 암호화 함수
    public String aesEncrypt(byte[] key, byte[] iv, String plainText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");    // JAVA의 PKCS5Padding은 PKCS7Padding과 호환
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] byteEncryptedData = cipher.doFinal(plainText.getBytes("UTF-8"));

        // Base64로 인코딩

        return Base64.getEncoder().encodeToString(byteEncryptedData);
    }


    // RSA 암호화 함수
    public static String rsaEncrypt(String rsaPublicKey, byte[] aesKey) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String encryptedData = null;

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] keyBytes = Base64.getDecoder().decode(rsaPublicKey.getBytes("UTF-8"));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        PublicKey fileGeneratedPublicKey = keyFactory.generatePublic(spec);
        RSAPublicKey key = (RSAPublicKey) (fileGeneratedPublicKey);

        // 만들어진 공개키객체를 기반으로 암호화모드로 설정하는 과정
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // 평문을 암호화하는 과정
        byte[] byteEncryptedData = cipher.doFinal(aesKey);

        // Base64로 인코딩

        return Base64.getEncoder().encodeToString(byteEncryptedData);
    }


    // RSA 공개키(Public Key) 조회 함수
    public String getPublicKey() throws IOException, ParseException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiHost + "/api/Auth/GetPublicKey?APIkey=" + apiKey)
                .header("Content-Type", "application/json").build();

        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseStr);

        return (String) jsonObject.get("PublicKey");
    }
}