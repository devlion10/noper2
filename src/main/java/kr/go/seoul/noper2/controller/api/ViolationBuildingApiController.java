package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.ExcelService;
import kr.go.seoul.noper2.service.ViolationBuildingService;
import kr.go.seoul.noper2.util.DTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/viobldmng")
public class ViolationBuildingApiController {
    private final ViolationBuildingService violationBuildingService;
    private final CodeService codeService;
    private final ExcelService excelService;

    @GetMapping("/list")
    public List<ViomaMasterDTO> selectViolationBuildingList(SearchParamDTO param) {
        return violationBuildingService.selectViolationBuildingList(param);
    }

    @GetMapping("/status")
    public List<ViomaStatusDTO> selectViolationBuildingStatus(SearchParamDTO param) {
        return violationBuildingService.selectViolationBuildingStatus(param);
    }

    @PostMapping("/update")
    public String addViolationBuilding(@AuthenticationPrincipal UserAuthentication auth, ViomaFullDTO viomaFull) {
        viomaFull.setUpdateId(auth.getUserId());
        viomaFull.setGmskkcd(auth.getSkkCd());
        return violationBuildingService.updateViolationBuilding(viomaFull);
    }
}
