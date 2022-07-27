package com.example.healthfunctionalfood.service.health;

import com.example.healthfunctionalfood.advice.exception.ApiRequestException;
import com.example.healthfunctionalfood.domain.common.Address;
import com.example.healthfunctionalfood.domain.openapi.Diagnosis;
import com.example.healthfunctionalfood.domain.openapi.HealthHistory;
import com.example.healthfunctionalfood.domain.openapi.HealthToken;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.request.HealthRequestDto;
import com.example.healthfunctionalfood.dto.response.MyPageResponseDto;
import com.example.healthfunctionalfood.repository.DiagnosisRepository;
import com.example.healthfunctionalfood.repository.HealthHistoryRepository;
import com.example.healthfunctionalfood.repository.HealthTokenRepository;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HealthServiceImpl implements HealthService {

    private final UserRepository userRepository;

    private final TilkoServiceImpl tilkoService;

    private final HealthTokenRepository healthTokenRepository;

    private final DiagnosisRepository diagnosisRepository;

    private final HealthHistoryRepository healthHistoryRepository;

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
        if (healthTokenOptional.isEmpty()) {
            HealthToken healthToken = HealthToken.builder()
                    .user(user)
                    .cxId(cxId)
                    .reqTxId(reqTxId)
                    .token(token)
                    .txId(txId)
                    .build();
            healthTokenRepository.save(healthToken);
        } else {
            healthTokenOptional.get().updateHealthToken(cxId, reqTxId, token, txId);
        }
    }

    @Override
    @Transactional
    public List<MyPageResponseDto.Treatment> addDiagnosis(HealthRequestDto.userInfo userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        // 로그인 미완성으로 임시 하드코딩
        // 카카오 로그인으로 사용자 정보 받는 로직 추가 필요
        User user = userRepository.findById(1L).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 사용자입니다."));
        Optional<Diagnosis> diagnosisOptional1 = diagnosisRepository.findByUserId(user.getId()).stream().findFirst();
        if (diagnosisOptional1.isEmpty()) {
            HealthToken healthToken = healthTokenRepository.findByUserId(user.getId()).get();

            String medication = tilkoService.getMedication(userInfo, healthToken);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(medication);
            JSONArray resultList = (JSONArray) jsonObject.get("ResultList");
            for (int i = 0; i < resultList.size(); i++) {
                JSONObject object = (JSONObject) resultList.get(i);
                String name = (String) object.get("ByungEuiwonYakGukMyung");    //병원명
                String JinlyoGaesiil = (String) object.get("JinRyoGaesiIl");    //진료날짜
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(JinlyoGaesiil + " 00:00:00", formatter);
                String JinlyoHyeongtae = (String) object.get("JinRyoHyungTae");    //진료형태
                String CheobangHoesu = String.valueOf(object.get("CheoBangHoiSoo"));//처방횟수
                int BangmunIlsu = Integer.parseInt((String) object.get("BangMoonIpWonIlsoo"));   //방문일수
                int TuyagIlsu = Integer.parseInt((String) object.get("TuYakYoYangHoiSoo"));    //투약일수

                List<Diagnosis> diagnosisList = new ArrayList<>();
                JSONArray retrieveTreatmentInjectionInformationPersonDetailList = (JSONArray) object.get("RetrieveTreatmentInjectionInformationPersonDetailList");


                for (int j = 0; j < retrieveTreatmentInjectionInformationPersonDetailList.size(); j++) {
                    Object RetrieveTreatmentInjectionInformationPersonDetailList = retrieveTreatmentInjectionInformationPersonDetailList.get(j);

                    String RetrieveTreatmentInjectionInformationPersonDetailListStr = RetrieveTreatmentInjectionInformationPersonDetailList.toString();
                    JSONObject retrieveTreatment = (JSONObject) jsonParser.parse(RetrieveTreatmentInjectionInformationPersonDetailListStr);
                    Object retrieveMdsupDtlInfo = retrieveTreatment.get("RetrieveMdsupDtlInfo");
                    String retrieveMdsupDtlInfoStr = retrieveMdsupDtlInfo.toString();
                    JSONObject retrieveMdsupDtlInfoObj = (JSONObject) jsonParser.parse(retrieveMdsupDtlInfoStr);
                    String drugCode = String.valueOf(retrieveMdsupDtlInfoObj.get("DrugCode"));
                    String mediPrdcNm = String.valueOf(retrieveMdsupDtlInfoObj.get("MediPrdcNm"));
                    String cmpnInfo = String.valueOf(retrieveMdsupDtlInfoObj.get("CmpnInfo"));
                    String tmsgGnlSpcd = String.valueOf(retrieveMdsupDtlInfoObj.get("TmsgGnlSpcd"));
                    String snglCmtnYn = String.valueOf(retrieveMdsupDtlInfoObj.get("SnglCmtnYn"));
                    String upsoName = String.valueOf(retrieveMdsupDtlInfoObj.get("UpsoName"));
                    String upso1 = String.valueOf(retrieveMdsupDtlInfoObj.get("Upso1"));
                    String fomlCdXplnCnte = String.valueOf(retrieveMdsupDtlInfoObj.get("FomlCdXplnCnte"));
                    String mdctPathXplnCnte = String.valueOf(retrieveMdsupDtlInfoObj.get("MdctPathXplnCnte"));
                    String mohwClsfNoXplnCnte = String.valueOf(retrieveMdsupDtlInfoObj.get("MohwClsfNoXplnCnte"));
                    String atcInfo = String.valueOf(retrieveMdsupDtlInfoObj.get("AtcInfo"));
                    String kpicInfo = String.valueOf(retrieveMdsupDtlInfoObj.get("KpicInfo"));
                    String efftEftCnte = String.valueOf(retrieveMdsupDtlInfoObj.get("EfftEftCnte"));
                    String usagCpctCnte = String.valueOf(retrieveMdsupDtlInfoObj.get("UsagCpctCnte"));
                    String useAtntMttCnte = String.valueOf(retrieveMdsupDtlInfoObj.get("UseAtntMttCnte"));
                    String cmnTmdcGdncCnte = String.valueOf(retrieveMdsupDtlInfoObj.get("CmnTmdcGdncCnte"));

                    Address address = Address.builder()
                            .address(name)
                            .build();

                    Diagnosis diagnosisDetails = Diagnosis.builder()
                            .atcInfo(atcInfo)
                            .drugCode(drugCode)
                            .dosage(usagCpctCnte)
                            .drugName(mediPrdcNm)
                            .efficacyEffect(efftEftCnte)
                            .formulation(fomlCdXplnCnte)
                            .importer(upso1)
                            .ingredientInfo(cmpnInfo)
                            .kpicInfo(kpicInfo)
                            .medicationGuidance(cmnTmdcGdncCnte)
                            .ministryOfWelfareClassification(mohwClsfNoXplnCnte)
                            .numberOfPrescriptions(CheobangHoesu)
                            .precautions(useAtntMttCnte)
                            .professionalGeneral(tmsgGnlSpcd)
                            .routeOfAdministration(mdctPathXplnCnte)
                            .singleCompound(snglCmtnYn)
                            .typeOfTreatment(JinlyoHyeongtae)
                            .vendor(upsoName)
                            .address(address)
                            .dosingDay(TuyagIlsu)
                            .visitCount(BangmunIlsu)
                            .treatmentStartDate(localDateTime)
                            .user(user).build();
                    diagnosisList.add(diagnosisDetails);
                }
                return diagnosisRepository.saveAll(diagnosisList).stream()
                        .map(MyPageResponseDto.Treatment::new)
                        .collect(Collectors.toList());
            }
        }
        List<Diagnosis> diagnosisList = diagnosisRepository.findByUserId(user.getId());

        return diagnosisList.stream()
                .map(MyPageResponseDto.Treatment::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MyPageResponseDto.health> addHealthCheck(HealthRequestDto.userInfo userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        // 로그인 미완성으로 임시 하드코딩
        // 카카오 로그인으로 사용자 정보 받는 로직 추가 필요
        User user = userRepository.findById(1L).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 사용자입니다."));

        HealthToken healthToken = healthTokenRepository.findByUserId(user.getId()).get();
        String healthCheckUp = tilkoService.getHealthCheckUp(userInfo, healthToken);

        Optional<HealthHistory> healthHistoryOptional = healthHistoryRepository.findByUserId(user.getId()).stream().findFirst();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(healthCheckUp);
        JSONArray resultData = (JSONArray) jsonObject.get("ResultList");
        for (int i = 0; i < resultData.size(); i++) {
            JSONObject object = (JSONObject) resultData.get(i);
            String year = (String) object.get("Year");
            String checkUpDate = (String) object.get("CheckUpDate");
            String code = (String) object.get("Code");
            String location = (String) object.get("Location");
            String description = (String) object.get("Description");

            List<String> healthHistories = new ArrayList<>();
            JSONArray inspections = (JSONArray) object.get("Inspections");
            for (Object o : inspections) {
                String s = o.toString();
                JSONObject object1 = (JSONObject) jsonParser.parse(s);
                JSONArray illnesses = (JSONArray) object1.get("Illnesses");

                for (Object o1 : illnesses) {
                    String s1 = o1.toString();
                    JSONObject parse = (JSONObject) jsonParser.parse(s1);
                    JSONArray items = (JSONArray) parse.get("Items");

                    for (Object o2 : items) {
                        String s2 = o2.toString();
                        JSONObject parse1 = (JSONObject) jsonParser.parse(s2);
                        Object name1 = parse1.get("Name");
                        Object value = parse1.get("Value");
                        JSONArray itemReferences = (JSONArray) parse1.get("ItemReferences");
                        healthHistories.add((String) value);
                        for (Object o3 : itemReferences) {
                            String s3 = o3.toString();
                            JSONObject parse2 = (JSONObject) jsonParser.parse(s3);
                        }
                    }
                }
            }
            if (healthHistoryOptional.isEmpty()) {
                HealthHistory healthHistory = HealthHistory.builder()
                        .year(Integer.parseInt(year))
                        .checkUpDate(checkUpDate)
                        .code(code)
                        .location(location)
                        .description(description)
                        .kidney(healthHistories.get(0))
                        .weight(healthHistories.get(1))
                        .waistCircumference(healthHistories.get(2))
                        .bodyMassIndex(healthHistories.get(3))
                        .vision(healthHistories.get(4))
                        .ear(healthHistories.get(5))
                        .bloodPressure(healthHistories.get(6))
                        .urineProtein(healthHistories.get(7))
                        .hemoglobin(healthHistories.get(8))
                        .fastingBloodSugar(healthHistories.get(9))
                        .totalCholesterol(healthHistories.get(10))
                        .HDLCholesterol(healthHistories.get(11))
                        .triglycerides(healthHistories.get(12))
                        .LDLCholesterol(healthHistories.get(13))
                        .serumCreatinine(healthHistories.get(14))
                        .gfr(healthHistories.get(15))
                        .sgot(healthHistories.get(16))
                        .sgpt(healthHistories.get(17))
                        .yGtp(healthHistories.get(18))
                        .PTCD(healthHistories.get(19))
                        .osteoporosis(healthHistories.get(20))
                        .user(user).build();
                healthHistoryRepository.save(healthHistory);
            }
        }
        List<HealthHistory> healthHistories = healthHistoryRepository.findByUserId(user.getId());
        return healthHistories.stream()
                .map(MyPageResponseDto.health::new)
                .collect(Collectors.toList());
    }
}