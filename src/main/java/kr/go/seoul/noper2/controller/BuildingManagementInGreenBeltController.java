package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.domain.Code;
import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.BuildingManagementInGreenBeltService;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.util.DTOConverter;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bldmngingb")
@Controller
public class BuildingManagementInGreenBeltController {
    private final BuildingManagementInGreenBeltService bldmngingbService;
    private final CodeService CodeService;

    /* 일일처리내역 */
    @MenuId("PL")
    @GetMapping("/dailyHistory")
    public String dailyHistoryList(HistoryMainDTO dto, Model model) {
        List<Code> codeGreenBeltWorkId = CodeService.findGreenBeltWorkId();
        List<Code> codeGmskk = CodeService.findGmskk();
        //List<HistoryMain> historyList = bldmngingbService.findHistoryList(dto);

        //model.addAttribute("historyList", historyList);
        model.addAttribute("skk", CodeService.findSkkList());
        model.addAttribute("codeGreenBeltWorkId", codeGreenBeltWorkId);
        model.addAttribute("codeGmskk", codeGmskk);

        return "pages/buildingManagementInGreenBelt/dailyHistory";
    }

    /* 대장관리 */
    @GetMapping("/ledger")
    @MenuId("DM")
    public String greenBeltLedgerList(Model model, LedgerDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        String gmskk = auth.getSkkCd();
        List<LedgerDTO> mngAdminstrBld = bldmngingbService.mngAdminstrBld(gmskk); //관리행정동
        List<LedgerDTO> ledgerList = bldmngingbService.findLedgerList(dto);
        List<Code> findGmcsah = CodeService.findGmcsah(); //대지/산

        model.addAttribute("ledgerList", ledgerList);
        model.addAttribute("findGmcsah", findGmcsah);
        model.addAttribute("mngAdminstrBld", mngAdminstrBld);
        model.addAttribute("gmskk", gmskk);

        return "pages/buildingManagementInGreenBelt/greenBeltLedger";
    }

    @MenuId("DM")
    @GetMapping("/ledgerAdd")
    public String ledgerAdd(Model model, @ModelAttribute LedgerDTO ledgerDTO, @AuthenticationPrincipal UserAuthentication auth){
        String gmskk = auth.getSkkCd();
        List<Code> codeStCd = CodeService.findStCd(); //구조리스트
        List<Code> codeJydCd = CodeService.findJydCd(); //구조리스트
        List<LedgerDTO> mngAdminstrBld = bldmngingbService.mngAdminstrBld(gmskk); //관리행정동

        model.addAttribute("codeStCd" , codeStCd);
        model.addAttribute("codeJydCd" , codeJydCd);
        model.addAttribute("mngAdminstrBld", mngAdminstrBld);
        model.addAttribute("gmskk", gmskk);
        return "pages/buildingManagementInGreenBelt/greenBeltLedgerAdd";
    }

    @MenuId("DM")
    @GetMapping("/ledgerDetail")
    public String ledgerDetail(@RequestParam String admSeq, LedgerDTO dto, Model model, @AuthenticationPrincipal UserAuthentication auth) {
        String gmskk = auth.getSkkCd();
        List<Code> codeStCd = CodeService.findStCd(); //구조리스트
        List<Code> codeJydCd = CodeService.findJydCd(); //구조리스트
        List<LedgerDTO> mngAdminstrBld = bldmngingbService.mngAdminstrBld(gmskk); //관리행정동

        //var limitedDto = new LedgerDTO.LegerLimited();
        //limitedDto.setAdmSeq(admSeq);
        var ownerDto = new LedgerDTO.LegerOwner();
        ownerDto.setAdmSeq(admSeq);
        var dongDto = new LedgerDTO.LegerDong();
        dongDto.setAdmSeq(admSeq);
        var structuresDto = new LedgerDTO.LegerStructures();
        structuresDto.setAdmSeq(admSeq);
        var groundDto = new LedgerDTO.LegerGround();
        groundDto.setAdmSeq(admSeq);
        var permitDto = new LedgerDTO.LegerPermit();
        permitDto.setAdmSeq(admSeq);

        LedgerDTO.LegerLimited legerLimited = bldmngingbService.legerLimited(dto);   // 건축물현황

        List<LedgerDTO.LegerOwner> legerOwner = bldmngingbService.legerOwner(ownerDto);    // 소유주현황
        //System.out.println("legerOwner@@"+legerOwner);
        List<LedgerDTO.LegerDong> legerDong = bldmngingbService.legerDong(dongDto);    // 동별현황
        //System.out.println("legerDong@@"+legerDong);
        List<LedgerDTO.LegerStructures> legerStructures = bldmngingbService.legerStructures(structuresDto);    // 구조물현황
        //System.out.println("legerStructures@@"+legerStructures);
        List<LedgerDTO.LegerGround> legerGround = bldmngingbService.legerGround(groundDto);    // 대지현황
        //System.out.println("legerGround@@"+legerGround);
        List<LedgerDTO.LegerPermit> legerPermit = bldmngingbService.legerPermit(permitDto);    // 허가신고사항조회
        //System.out.println("legerPermit@@"+legerPermit);
        // 배치도/사진
        //List<NoperSiteChkDTO> findNoperLedgerSiteChk = bldmngingbService.findLedgerSiteChk("B11", gmskk, dto.getGmdjgb(), admSeq);


        model.addAttribute("codeStCd" , codeStCd);
        model.addAttribute("codeJydCd" , codeJydCd);
        model.addAttribute("mngAdminstrBld", mngAdminstrBld);
        model.addAttribute("gmskk", gmskk);

        model.addAttribute("admSeq", admSeq);
        model.addAttribute("legerLimited", legerLimited);
        model.addAttribute("legerOwner", legerOwner);
        model.addAttribute("legerDong", legerDong);
        model.addAttribute("legerStructures", legerStructures);
        model.addAttribute("legerGround", legerGround);
        model.addAttribute("legerPermit", legerPermit);
        //model.addAttribute("findLedgerSiteChk", findLedgerSiteChk);

        return "pages/buildingManagementInGreenBelt/greenBeltLedgerDetail";
    }

    @MenuId("DM")
    @PostMapping(value = "/download/ledgerExcel")
    public String downloadExcel(Model model, @RequestBody GreenBeltLedgerExcelDTO dto) {
        try {
            model.addAttribute("list", DTOConverter.convertToExcelList(bldmngingbService.findLedgerDataExcel(dto)));
            return "excelView";
        } catch (IOException e) {
            log.error("ERROR-01: 파일 처리 중 에러", e);
        } catch (NoSuchElementException e) {
            log.error("ERROR-02: 데이터를 찾을 수 없음", e);
        } catch (Exception e) {
            log.error("ERROR-03: 알 수 없는 오류 발생", e);
        }
        return "errorView";
    }

    @PostMapping(value = "/download/dailyHistoryExcel")
    public String downloadExcel(Model model, @RequestBody HistoryMainDTO dto) {
        try {
            model.addAttribute("list", DTOConverter.convertToExcelList(bldmngingbService.findDailyHistoryDataExcel(dto)));
            return "excelView";
        } catch (IOException e) {
            log.error("ERROR-01: 파일 처리 중 에러", e);
        } catch (NoSuchElementException e) {
            log.error("ERROR-02: 데이터를 찾을 수 없음", e);
        } catch (Exception e) {
            log.error("ERROR-03: 알 수 없는 오류 발생", e);
        }
        return "errorView";
    }


}
