package kr.go.seoul.noper2.controller.api;


import kr.go.seoul.noper2.domain.Code;
import kr.go.seoul.noper2.dto.LedgerDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common/api")
public class CommonApiController {

    @Value("${noper.report.url}")
    private String reportURL;

    private final CommonService commonService;
    private final CodeService codeService;

    @PostMapping("/ledger")
    public Map<String, Object> ledgerList(@AuthenticationPrincipal UserAuthentication auth, @RequestBody LedgerDTO dto) {
        Map<String, Object> map = new HashMap<>();

        //selectBox 등재관리코드 리스트
        List<Code> gmdjgbList = codeService.findLedget();

        //selectBox 대지,산,블록 리스트
        List<Code> gmcsahList = codeService.findGmcsah();

        //selectBox 법정동주소 리스트
        dto.setUserId(auth.getUserId());
        List<LedgerDTO> bjdJusoList = commonService.findBjdJusoList(dto, auth);

        map.put("gmdjgbList", gmdjgbList);
        map.put("gmcsahList", gmcsahList);
        map.put("bjdJusoList", bjdJusoList);

        return map;
    }

    @PostMapping("/noperNumSearch")
    public Map<String, Object> noperNumSearch(@RequestBody LedgerDTO dto) {
        List<LedgerDTO> noperNumList = null;
        String jumin1 = "";
        String jumin2 = "";
        String jumin = "";
        if (!dto.getGmjjuminTemp1().isEmpty() && !dto.getGmjjuminTemp2().isEmpty()) {
            jumin1 = dto.getGmjjuminTemp1();
            jumin2 = dto.getGmjjuminTemp2();
            jumin = jumin1 + "-" + jumin2;
            dto.setGmjjumin(jumin);
        }
        if (dto.getPopFlag().equals("confirmation")) {
            noperNumList = commonService.findconfirmationPopup(dto);
        } else if(dto.getPopFlag().equals("change")){
            noperNumList = commonService.findChangePopup(dto);
        } else if(dto.getPopFlag().equals("absorption")){
            noperNumList = commonService.findAbsorptionPopup(dto);
        } else if(dto.getPopFlag().equals("newLedger")){
            noperNumList = commonService.findNewNoperNumList(dto);
        } else if(dto.getPopFlag().equals("owner")){
            noperNumList = commonService.findNoperOwnerPopup(dto);
        } else {
            noperNumList = commonService.findNoperNumListByGmskk(dto);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("noperNumList", noperNumList);

        return map;
    }

    @GetMapping("/report/url")
    public ResponseEntity<String> reportUrl() {
        return new ResponseEntity<>(reportURL, HttpStatus.OK);
    }
}
