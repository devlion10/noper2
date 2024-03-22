package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.UserService;
import kr.go.seoul.noper2.service.ViolationBuildingService;
import kr.go.seoul.noper2.util.DTOConverter;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/viobldmng")
public class ViolationBuildingController {
    private final ViolationBuildingService violationBuildingService;
    private final UserService userService;
    private final CodeService codeService;

    @MenuId("MV")
    @GetMapping("/list")
    public String violationBuildingList(@AuthenticationPrincipal UserAuthentication auth, SearchParamDTO param, Model model) {
        model.addAttribute("total", 0);
        model.addAttribute("saveBtn", "위반건축물 등록");
        model.addAttribute("searchParam", param);
        model.addAttribute("statusToList", param.isStatusToList());
        model.addAttribute("codeLedget", codeService.findLedget());
        model.addAttribute("skk", codeService.findSkkList());
        model.addAttribute("admin", userService.selectUser(auth.getUserId(), "register").getPerId().startsWith("AD"));
        return "pages/violatingbuildingmanagement/violated";
    }

    @MenuId("MV")
    @GetMapping("/status")
    public String selectViolationBuildingStats(@AuthenticationPrincipal UserAuthentication auth, SearchParamDTO param, Model model) {
        model.addAttribute("total", 0);
        model.addAttribute("searchParam", param);
        model.addAttribute("skk", codeService.findSkkList());
        model.addAttribute("admin", userService.selectUser(auth.getUserId(), "register").getPerId().startsWith("AD"));
        return "pages/violatingbuildingmanagement/violatedStatus";
    }

    @MenuId("MV")
    @GetMapping("/info")
    public String violationBuildingInfo(@AuthenticationPrincipal UserAuthentication auth, ViomaMasterDTO id, Model model) {
        ViomaMasterDTO viomaMaster = violationBuildingService.selectViolationBuilding(id);
        model.addAttribute("vioma", viomaMaster);
        model.addAttribute("resi", violationBuildingService.selectViolationBuildingResi(id));
        model.addAttribute("impo", violationBuildingService.selectViolationBuildingImpo(id));
        model.addAttribute("coll", violationBuildingService.selectViolationBuildingColl(id));
        return "pages/violatingbuildingmanagement/violatedInfo";
    }

    @MenuId("MV")
    @PostMapping("/modify")
    public String updateViolationBuilding(ViomaMasterDTO id, Model model) {
        ViomaMasterDTO viomaMaster = violationBuildingService.selectViolationBuilding(id);
        model.addAttribute("codeLedget", codeService.findLedget());
        model.addAttribute("vioma", viomaMaster);
        model.addAttribute("resi", violationBuildingService.selectViolationBuildingResi(id));
        model.addAttribute("impo", violationBuildingService.selectViolationBuildingImpo(id));
        model.addAttribute("coll", violationBuildingService.selectViolationBuildingColl(id));
        model.addAttribute("bjdCd", codeService.findBjdNameListBySkkCd(viomaMaster.getGmskkcd()));
        model.addAttribute("codeUsage", codeService.findStructure());
        return "pages/violatingbuildingmanagement/violatedAdd";
    }

    @MenuId("MV")
    @GetMapping("/create")
    public String registration(@AuthenticationPrincipal UserAuthentication auth, HttpServletRequest request, Model model) {
        model.addAttribute("codeLedget", codeService.findLedget());
        model.addAttribute("vioma", new ViomaMasterDTO());
        model.addAttribute("resi", new ArrayList<ViomaMasterDTO>());
        model.addAttribute("impo", new ArrayList<ViomaEnfoImpoDTO>());
        model.addAttribute("coll", new ArrayList<ViomaEnfoCollDTO>());
        model.addAttribute("codeUsage", codeService.findStructure());
        model.addAttribute("bjdCd", codeService.findBjdNameListBySkkCd(auth.getSkkCd()));
        return "pages/violatingbuildingmanagement/violatedAdd";
    }
    @MenuId("MV")
    @GetMapping("/info/{path}")
    public String infoPage(HttpServletRequest request, @PathVariable String path, Model model) {
        return "pages/violatingbuildingmanagement/violatedInfo";
    }

    @MenuId("MV")
    @PostMapping("/excel")
    public String downloadExcel(@AuthenticationPrincipal UserAuthentication auth, SearchParamDTO param, Model model) {
        try {
            param.setDeptCd(auth.getIsAdmin() ? "관리자" : auth.getDeptName());
            model.addAttribute("list", DTOConverter.convertToExcelList(violationBuildingService.selectViolationBuildingList(param)));
            return "excelView";
        } catch (DataRetrievalFailureException e) {
            log.error("ERROR-01: 데이터 검색 중 에러", e);
        } catch (Exception e) {
            log.error("ERROR-02: 알 수 없는 오류 발생", e);
        }
        return "errorView";
    }


    @PostMapping(value = "/status/excel")
    public String downloadStatusExcel(@AuthenticationPrincipal UserAuthentication auth, SearchParamDTO param, Model model) {
        try {
            param.setDeptCd(auth.getIsAdmin() ? "" : auth.getDeptName());
            model.addAttribute("list", DTOConverter.convertToExcelList(violationBuildingService.selectViolationBuildingStatus(param)));
            return "excelView";
        } catch (DataRetrievalFailureException e) {
            log.error("ERROR-01: 데이터 검색 중 에러", e);
        } catch (Exception e) {
            log.error("ERROR-02: 알 수 없는 오류 발생", e);
        }
        return "errorView";
    }

}