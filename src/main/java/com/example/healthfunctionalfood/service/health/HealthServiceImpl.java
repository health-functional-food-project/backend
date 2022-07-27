package com.example.healthfunctionalfood.service.health;

import com.example.healthfunctionalfood.advice.exception.ApiRequestException;
import com.example.healthfunctionalfood.domain.common.Address;
import com.example.healthfunctionalfood.domain.openapi.Diagnosis;
import com.example.healthfunctionalfood.domain.openapi.HealthToken;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.request.HealthRequestDto;
import com.example.healthfunctionalfood.dto.response.MyPageResponseDto;
import com.example.healthfunctionalfood.repository.DiagnosisRepository;
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
public class HealthServiceImpl implements HealthService{

    private final UserRepository userRepository;

    private final TilkoServiceImpl tilkoService;

    private final HealthTokenRepository healthTokenRepository;

    private final DiagnosisRepository diagnosisRepository;

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

    @Override
    @Transactional
    public List<MyPageResponseDto.Treatment> addDiagnosis(HealthRequestDto.userInfo userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        // 로그인 미완성으로 임시 하드코딩
        // 카카오 로그인으로 사용자 정보 받는 로직 추가 필요
        User user = userRepository.findById(1L).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 사용자입니다."));

        HealthToken healthToken = healthTokenRepository.findByUserId(user.getId()).get();

        String medication = tilkoService.getMedication(userInfo, healthToken);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(medication);
        JSONArray resultList = (JSONArray) jsonObject.get("ResultList");
        for(int i = 0; i< resultList.size(); i++){
            JSONObject object = (JSONObject) resultList.get(i);
            String name = (String) object.get("ByungEuiwonYakGukMyung");    //병원명
            String JinlyoGaesiil = (String) object.get("JinRyoGaesiIl");    //진료날짜
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(JinlyoGaesiil+" 00:00:00", formatter);
            String JinlyoHyeongtae = (String) object.get("JinRyoHyungTae");    //진료형태
            String CheobangHoesu = String.valueOf(object.get("CheoBangHoiSoo"));//처방횟수
            int BangmunIlsu = Integer.valueOf((String) object.get("BangMoonIpWonIlsoo"));   //방문일수
            int TuyagIlsu = Integer.valueOf((String) object.get("TuYakYoYangHoiSoo"));    //투약일수

            List<Diagnosis> diagnosisList = new ArrayList<>();
            JSONArray retrieveTreatmentInjectionInformationPersonDetailList = (JSONArray) object.get("RetrieveTreatmentInjectionInformationPersonDetailList");


            for (int j =0; j<retrieveTreatmentInjectionInformationPersonDetailList.size(); j++){
                Object RetrieveTreatmentInjectionInformationPersonDetailList = retrieveTreatmentInjectionInformationPersonDetailList.get(j);

                String RetrieveTreatmentInjectionInformationPersonDetailListStr = RetrieveTreatmentInjectionInformationPersonDetailList.toString();
                JSONObject retrieveTreatment = (JSONObject) jsonParser.parse(RetrieveTreatmentInjectionInformationPersonDetailListStr);
                retrieveTreatment.get("JinRyoHyungTae");
                retrieveTreatment.get("ChoBangHoetSoo");
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

            }List<Diagnosis> diagnosisOptional = diagnosisRepository.findByUserId(user.getId());
            if(diagnosisList.stream().count()!=diagnosisOptional.stream().count()){
                List<Diagnosis> diagnoses = diagnosisRepository.saveAll(diagnosisList);
            }
        }
        List<Diagnosis> diagnosisList = diagnosisRepository.findByUserId(user.getId());

        return diagnosisList.stream()
                .map(MyPageResponseDto.Treatment::new)
                .collect(Collectors.toList());
    }
}