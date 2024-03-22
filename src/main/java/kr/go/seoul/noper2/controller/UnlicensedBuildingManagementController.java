package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.domain.Code;
import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.BoardService;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.CommonService;
import kr.go.seoul.noper2.service.UnlicensedBuildingManagementService;
import kr.go.seoul.noper2.util.DTOConverter;
import kr.go.seoul.noper2.util.TypeCasting;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/unlcebldmng")
@Controller
public class UnlicensedBuildingManagementController {
    private final BoardService boardService;
    private final UnlicensedBuildingManagementService unBuildManageService;
    private final CodeService CodeService;
    private final CommonService commonService;

    /* 대장조회 select box*/
    @MenuId("DK")
    @GetMapping("/ledger")
    public String ledgerList(Model model,
                             @ModelAttribute LedgerDTO ledgerDTO,
                             SearchParamDTO searchParamDTO,
                             @AuthenticationPrincipal UserAuthentication auth){
        List<Code> codeLedget = CodeService.findLedget();
        List<Code> codePosseion = CodeService.findPossession();
        List<Code> codeUsage = CodeService.findUsage();
        List<Code> findGmcsah = CodeService.findGmcsah(); //대지/산
        List<LedgerDTO> mngAdminstrBld = unBuildManageService.mngAdminstrBld(auth.getSkkCd()); //관리행정동
        List<CodeDTO> skk =  CodeService.findSkkList();
        String userName = auth.getUserName();

        // 관리자가 아닐때 관리행정동
        if(!auth.getIsAdmin()){
            model.addAttribute("mngAdminstrBld" , mngAdminstrBld);
        }

        model.addAttribute("skk" , skk);
        model.addAttribute("userName" , userName);
        model.addAttribute("codeLedget" , codeLedget);
        model.addAttribute("codePosseion" , codePosseion);
        model.addAttribute("codeUsage" , codeUsage);
        model.addAttribute("findGmcsah" , findGmcsah);
        model.addAttribute("ledgerFindForm",ledgerDTO); // 검색
        model.addAttribute("searchParamDTO",searchParamDTO);
        return "pages/buildingmanagement/ledger";
    }

    @GetMapping("/ledgerStatus")
    public String ledgerStatus(Model model, @ModelAttribute LedgerDTO ledgerDTO){
        model.addAttribute("skk", CodeService.findSkkList());
        return "pages/buildingmanagement/ledgerStatus";
    }

    @GetMapping("/ledgerStatus/period")
    public String ledgerStatusByPeriod(Model model, @ModelAttribute LedgerDTO ledgerDTO){
        model.addAttribute("skk", CodeService.findSkkList());
        model.addAttribute("lnb", "period");
        return "pages/buildingmanagement/ledgerStatusByPeriod";
    }

    @MenuId("DK")
    @GetMapping("/ledgerAdd")
    public String ledgerAdd(Model model, @ModelAttribute LedgerDTO ledgerDTO, @AuthenticationPrincipal UserAuthentication auth){
        List<Code> codeLedget = CodeService.findLedget(); //대장리스트
        List<Code> codePosseion = CodeService.findPossession(); //용도리스트
        List<Code> codeUsage = CodeService.findUsage(); //소유구분리스트
        List<Code> codeRedevelopmentDistrict = CodeService.findRedevelopmentDistrict(); //재개발지구여부
        List<Code> codePdointOut = CodeService.findPdointOut(); //지목
        List<Code> codeDistrict = CodeService.findDistrict(); //지구구분
        List<Code> codeUrbanPlanning = CodeService.findUrbanPlanning(); //도시계획
        List<Code> codeConditions = CodeService.findConditions(); //지구여건
        List<Code> codeStructure = CodeService.findStructure(); //구조
        List<LedgerDTO> mngAdminstrBld = unBuildManageService.mngAdminstrBld(auth.getSkkCd()); //관리행정도

        model.addAttribute("ledgerFindForm", TypeCasting.dtoToMap(ledgerDTO));
        model.addAttribute("codeLedget" , codeLedget);
        model.addAttribute("codePosseion" , codePosseion);
        model.addAttribute("codeUsage" , codeUsage);
        model.addAttribute("codeRedevelopmentDistrict" , codeRedevelopmentDistrict);
        model.addAttribute("codePdointOut" , codePdointOut);
        model.addAttribute("codeDistrict" , codeDistrict);
        model.addAttribute("codeUrbanPlanning" , codeUrbanPlanning);
        model.addAttribute("codeConditions" , codeConditions);
        model.addAttribute("codeStructure" , codeStructure);
        model.addAttribute("mngAdminstrBld", mngAdminstrBld);
        model.addAttribute("gmskk", auth.getSkkCd());
        return "pages/buildingmanagement/ledgerAdd";
    }

    @MenuId("ND")
    @GetMapping("/newLedger")
    public String newLedgerList(Model model, @ModelAttribute LedgerDTO ledgerDTOh){
        List<Code> codeLedget = CodeService.findLedget();
        List<Code> codePosseion = CodeService.findPossession();
        List<Code> codeUsage = CodeService.findUsage();

        model.addAttribute("ledgerFindForm", ledgerDTOh);
        model.addAttribute("skk" , CodeService.findSkkList());
        model.addAttribute("codeLedget" , codeLedget);
        model.addAttribute("codePosseion" , codePosseion);
        model.addAttribute("codeUsage" , codeUsage);
        return "pages/buildingmanagement/newLedger";
    }

    @MenuId("ND")
    @GetMapping("/newLedgerDtail")
    public String newLedgerDtail(@RequestParam String gmbunho, LedgerDTO dto, Model model) {
        String[] gmbunhoArr = gmbunho.split("-");
        dto.setGmskk(gmbunhoArr[0]);
        String str = gmbunhoArr[1];
        switch(str) {
            case "등재" : dto.setGmdjgb("0"); break;
            case "미등재" : dto.setGmdjgb("1"); break;
            default: dto.setGmdjgb("2"); break;
        }
        dto.setGmseqco(gmbunhoArr[2]);
        LedgerDTO ledger = unBuildManageService.newLedgerDtail(dto);
        List<Code> codeLedget = CodeService.findLedget();
        List<Code> codePosseion = CodeService.findPossession();
        List<Code> codeUsage = CodeService.findUsage();

        model.addAttribute("ledgerFindForm", TypeCasting.dtoToMap(dto));
        model.addAttribute("ledger", ledger);
        model.addAttribute("codeLedget" , codeLedget);
        model.addAttribute("codePosseion" , codePosseion);
        model.addAttribute("codeUsage" , codeUsage);
        return "pages/buildingmanagement/newLedgerDtail";
    }

    @MenuId("DK")
    @GetMapping("/ledgerDtail")
    public String ledgerDtail(@RequestParam String gmbunho,
                              SearchParamDTO searchParamDTO,
                              LedgerDTO dto,Model model,
                              @AuthenticationPrincipal UserAuthentication auth) {
        Map<String,Object> ledgerFindForm = new HashMap<>();
        ledgerFindForm = TypeCasting.dtoToMap(dto);
        String userName = auth.getUserName();
        String gmskk = auth.getSkkCd();
        String[] gmbunhoArr = gmbunho.split("-");
        dto.setGmskk(gmbunhoArr[0]);
        String str = gmbunhoArr[1];
        switch(str) {
            case "등재" : dto.setGmdjgb("0"); break;
            case "미등재" : dto.setGmdjgb("1"); break;
            default: dto.setGmdjgb("2"); break;
        }
        dto.setGmseqco(gmbunhoArr[2]);
        List<Code> codePosseion = CodeService.findPossession(); //용도리스트
        List<Code> codeUsage = CodeService.findUsage(); //소유구분리스트
        List<Code> codeRedevelopmentDistrict = CodeService.findRedevelopmentDistrict(); //재개발지구여부
        List<Code> codePdointOut = CodeService.findPdointOut(); //지목
        List<Code> codeDistrict = CodeService.findDistrict(); //지구구분
        List<Code> codeUrbanPlanning = CodeService.findUrbanPlanning(); //도시계획
        List<Code> codeConditions = CodeService.findConditions(); //지구여건
        List<Code> codeStructure = CodeService.findStructure(); //구조
        List<LedgerDTO> mngAdminstrBld = unBuildManageService.mngAdminstrBld(gmskk); //관리행정도
        List<Code> codeGmjjgnTemp = CodeService.findGmjjgnTemp(); // (내국인/외국인)

        // 담당자 행정구
        LedgerDTO bjdongMgm = unBuildManageService.cmpBjdongMgm(gmskk);

        // 건축물현황
        LedgerDTO ledger = unBuildManageService.newLedgerDtail(dto);
        // 건물주 일련번호채번
        String gmjiNo = unBuildManageService.noperGmjilNo(gmbunhoArr[0], dto.getGmdjgb(), gmbunhoArr[2]);
        // 건물주현황
        List<NoperOwnerDTO> findNoperOwner = unBuildManageService.findNoperOwner(gmbunhoArr[0], dto.getGmdjgb(), gmbunhoArr[2]);
        // 소유자 변경 이력
        List<NoperOwnerDTO> findNoperOwnerChange = unBuildManageService.findNoperOwnerChange(gmbunhoArr[0], dto.getGmdjgb(), gmbunhoArr[2]);
        // 개보수신고대장
        List<NoperFixDTO> findnoperFix = unBuildManageService.findnoperFix(gmbunhoArr[0], dto.getGmdjgb(), gmbunhoArr[2]);

        String noperChkino = unBuildManageService.noperChkino(dto.getGmskk(), dto.getGmdjgb(), gmbunhoArr[2]);
        // 기존무허가 현장점검
        List<NoperSiteChkDTO> findNoperLedgerSiteChk = unBuildManageService.findNoperLedgerSiteChk(dto.getGmskk(), dto.getGmdjgb(), gmbunhoArr[2]);

        if(ledger.getDhbh() == null || ledger.getDhbh().isEmpty()) ledger.setDhbh("-");
        if(ledger.getDaejang() == null || ledger.getDaejang().isEmpty()) ledger.setDaejang("0");
        if(ledger.getNewaddr1() == null || ledger.getNewaddr1().isEmpty()) ledger.setNewaddr1("-");
        if(ledger.getGmbsdong() == null || ledger.getGmbsdong().isEmpty()) ledger.setGmbsdong("0");
        if(ledger.getGmfoors() == null || ledger.getGmfoors().isEmpty()) ledger.setGmfoors("1");
        if(ledger.getJigudz() == null || ledger.getJigudz().isEmpty()) ledger.setJigudz("0");
        if(ledger.getGmwebanno() == null || ledger.getGmwebanno().isEmpty()) ledger.setGmwebanno("0");
        if(ledger.getReason() == null || ledger.getReason().isEmpty()) ledger.setReason("-");

        model.addAttribute("ledgerFindForm",ledgerFindForm);
        model.addAttribute("userName", userName);
        model.addAttribute("ledger", ledger);
        model.addAttribute("bjdongMgm", bjdongMgm);
        model.addAttribute("gmjiNo", gmjiNo);
        model.addAttribute("findNoperOwner", findNoperOwner);
        model.addAttribute("findNoperOwnerChange", findNoperOwnerChange);
        model.addAttribute("findNoperSiteChk", findNoperLedgerSiteChk);
        model.addAttribute("findnoperFix", findnoperFix);
        //model.addAttribute("noperimgFile", noperimgFile);
        model.addAttribute("codeRedevelopmentDistrict", codeRedevelopmentDistrict);
        model.addAttribute("codeGmjjgnTemp", codeGmjjgnTemp);
        model.addAttribute("codePosseion", codePosseion);
        model.addAttribute("codePdointOut", codePdointOut);
        model.addAttribute("codeDistrict", codeDistrict);
        model.addAttribute("codeUrbanPlanning", codeUrbanPlanning);
        model.addAttribute("codeUrbanPlanning", codeUrbanPlanning);
        model.addAttribute("codeConditions", codeConditions);
        model.addAttribute("codeStructure", codeStructure);
        model.addAttribute("codeUsage", codeUsage);
        model.addAttribute("mngAdminstrBld", mngAdminstrBld);
        model.addAttribute("gmbunho", gmbunho.toString());
        model.addAttribute("SearchParamDTO", searchParamDTO);
        return "pages/buildingmanagement/ledgerDtail";
    }

    // 기존무허가 확인원관리
    @MenuId("EU")
    @GetMapping("/confirmation")
    public String confirmation(Model model, @ModelAttribute ConfirmationDTO ConfirmationDTO, @AuthenticationPrincipal UserAuthentication auth) {
        List<Code> codeLedget = CodeService.findLedget();
        List<Code> gmcsahList = CodeService.findGmcsah();

        model.addAttribute("skk" , CodeService.findSkkList());
        model.addAttribute("codeLedget", codeLedget);
        model.addAttribute("findGmcsah", gmcsahList);
        model.addAttribute("userName", auth.getUserName());
        return "pages/buildingmanagement/confirmation";
    }

    @MenuId("EU")
    @GetMapping("/confirmationDetail")
    public String confirmationDetail(@RequestParam String gmbunho, String conilno, LedgerDTO dto, NoperConfirmDocDTO dto2, NoperOwnerDTO dto3, Model model, @AuthenticationPrincipal UserAuthentication auth) {
        String gmskk = auth.getSkkCd();
        String[] gmbunhoArr = gmbunho.split("-");
        dto.setGmskk(gmbunhoArr[0]);
        dto2.setGmskk(gmbunhoArr[0]);
        dto3.setGmskk(gmbunhoArr[0]);
        String str = gmbunhoArr[1];
        switch (str) {
            case "등재":
                dto.setGmdjgb("0");
                dto2.setGmdjgb("0");
                dto3.setGmdjgb("0");
                break;
            case "미등재":
                dto.setGmdjgb("1");
                dto2.setGmdjgb("1");
                dto3.setGmdjgb("1");
                break;
            default:
                dto.setGmdjgb("2");
                dto2.setGmdjgb("2");
                dto3.setGmdjgb("2");
                break;
        }
        dto.setGmseqco(gmbunhoArr[2]);
        dto2.setGmseqco(gmbunhoArr[2]);
        dto3.setGmseqco(gmbunhoArr[2]);
        dto2.setConilno(conilno);

        LedgerDTO ledger = unBuildManageService.confirmationDetail(dto);
        NoperConfirmDocDTO confirmation = unBuildManageService.confirmationDetail2(dto2);
        List<NoperOwnerDTO> owner = unBuildManageService.confirmationDetail3(dto3);
        List<RenovationDTO> confirmationIssuerKey = unBuildManageService.confirmationIssuerKey(gmskk);
        List<Code> codeGmjjgnTemp = CodeService.findGmjjgnTemp();

        model.addAttribute("codeGmjjgnTemp", codeGmjjgnTemp);
        model.addAttribute("conilno", conilno);
        model.addAttribute("ledger", ledger);
        model.addAttribute("confirmation", confirmation);
        model.addAttribute("confirmationIssuerKey", confirmationIssuerKey);
        model.addAttribute("owner", owner);
        return "pages/buildingmanagement/confirmationDetail";
    }

    @MenuId("EU")
    @GetMapping("/confirmationAdd")
    public String confirmationAdd(Model model, String gmskk, String gmdjgb, String gmseqco, ConfirmationDTO dto, String flag, @AuthenticationPrincipal UserAuthentication auth) {
        gmskk = auth.getSkkCd();
        log.debug("\n[ 신규 확인원 발급 ]\ngmskk: {}\ngmdjgb: {}\ngmseqco: {}", gmskk, gmdjgb, gmseqco);

        // 기존무허가확인원 채번
        String conilno = unBuildManageService.confirmationNo(gmskk);

        List<Code> codeLedget = CodeService.findLedget(); //대장리스트
        List<Code> codeGmjjgnTemp = CodeService.findGmjjgnTemp();

        List<RenovationDTO> confirmationIssuerKey = unBuildManageService.confirmationIssuerKey(gmskk);

        model.addAttribute("conilno", conilno);
        model.addAttribute("userName", auth.getUserName());
        model.addAttribute("codeLedget", codeLedget);
        model.addAttribute("codeGmjjgnTemp", codeGmjjgnTemp);
        model.addAttribute("confirmationIssuerKey", confirmationIssuerKey);
        model.addAttribute("flag", flag);
        model.addAttribute("gmskk", gmskk);
        return "pages/buildingmanagement/confirmationAdd";
    }


    @MenuId("NC")
    @GetMapping("/ownerChange")
    public String ownerChangeList(Model model, @RequestParam(value = "gmskk", required = false) String gmskk,  @AuthenticationPrincipal UserAuthentication auth) {
        List<Code> codeLedget = CodeService.findLedget();
        List<Code> findGmcsah = CodeService.findGmcsah();

        if(!StringUtils.isEmpty(gmskk)){
            model.addAttribute("gmskk", gmskk);
        }

        model.addAttribute("skk" , CodeService.findSkkList());
        model.addAttribute("codeLedget", codeLedget);
        model.addAttribute("findGmcsah", findGmcsah);
        model.addAttribute("userName", auth.getUserName());
        return "pages/buildingmanagement/ownerChange";
    }


    @MenuId("NC")
    @GetMapping("/ownerChangeDetail")
    public String ownerChangeDetail(Model model, @RequestParam String gmbunho, OwnerChangeDTO ownerChange, LedgerDTO ledger, @AuthenticationPrincipal UserAuthentication auth) {
        String[] gmbunhoArr = gmbunho.split("-");
        String str = gmbunhoArr[1];
        String gmdjgb;
        switch (str) {
            case "등재":
                gmdjgb = "0";
                break;
            case "미등재":
                gmdjgb = "1";
                break;
            default:
                gmdjgb = "2";
                break;
        }

        // 기존무허가 소유자변경관리 건물 상세
        LedgerDTO ledgerInfo = unBuildManageService.findOwnerChangeGmj(gmbunhoArr[0], gmdjgb, gmbunhoArr[2]);
        // 소유자 변경현황
        List<OwnerChangeDTO> ownerChangeList = unBuildManageService.OwnerChangeDetailList(gmbunhoArr[0], gmdjgb, gmbunhoArr[2]);
        // 소유자 현황
        List<OwnerChangeDTO> ownerInfoList = unBuildManageService.OwnerInfoDetailList(gmbunhoArr[0], gmdjgb, gmbunhoArr[2]);
        // 소유자변경 현 소유주 일련정보gmjilno
        String OwnerChangeGmilNo = unBuildManageService.OwnerChangeGmilNo(gmbunhoArr[0], gmdjgb, gmbunhoArr[2]);
        // 소유자변경 파일이 정보
        List<OwnerChangeDTO> OwnerChangeFileList = unBuildManageService.OwnerChangeFileList(gmbunhoArr[0], gmdjgb, gmbunhoArr[2], OwnerChangeGmilNo);

        // 현소유주
//        model.addAttribute("ownerChange", ownerChangeList.isEmpty() ? new OwnerChangeDTO() : ownerChangeList.get(0));
        model.addAttribute("ownerInfoList", ownerInfoList.isEmpty() ? new OwnerChangeDTO() : ownerInfoList);
        // 신고사항
        model.addAttribute("OwnerChangeFileList", OwnerChangeFileList.isEmpty() ? new OwnerChangeDTO() : OwnerChangeFileList.get(0));
        // 소유주 이력
        model.addAttribute("ownerChangeList", ownerChangeList);
        // 기본현황
        model.addAttribute("ledgerInfo", ledgerInfo);
        model.addAttribute("gmbunho", gmbunho);
        model.addAttribute("userName", auth.getUserName());
        return "pages/buildingmanagement/ownerChangeDetail";
    }

    @MenuId("NC")
    @GetMapping("/ownerChangeAdd")
    public String ownerChangeAdd(@RequestParam(value = "gmbunho", required = false) String gmbunho,
                                 @RequestParam(value = "addFlage", required = false) String addFlage,
                                 @RequestParam(value = "gmjilno", required = false) String gmjilno,
                                 @AuthenticationPrincipal UserAuthentication auth,
                                 Model model) {
        LedgerDTO ledgerInfo;
        List<OwnerChangeDTO> ownerChangeList;
        // addFlage = "absorption";
        List<Code> codeLedger = CodeService.findLedget(); // 대장구분
        List<Code> codeGmjjgnTemp = CodeService.findGmjjgnTemp(); // 내국인/외국인
        List<BjdongDTO> mngAdminstrBld = CodeService.findBjdNameListBySkkCd(auth.getSkkCd()); //관리행정동
        String suilnoVal = "1";
        if (gmbunho != null) {
            addFlage = "change";
            String[] gmbunhoArr = gmbunho.split("-");
            String str = gmbunhoArr[1];
            String gmdjgb = null;
            switch (str) {
                case "등재":
                    gmdjgb = "0";
                    break;
                case "미등재":
                    gmdjgb = "1";
                    break;
                default:
                    gmdjgb = "2";
                    break;
            }
            if(gmbunhoArr.length > 3){
                suilnoVal = gmbunhoArr[3];
            }

            ledgerInfo = unBuildManageService.findOwnerChangeGmj(gmbunhoArr[0], gmdjgb, gmbunhoArr[2]);
            ownerChangeList = unBuildManageService.OwnerChangeDetailList(gmbunhoArr[0], gmdjgb, gmbunhoArr[2]);
            List<OwnerChangeDTO> ownerInfoList = unBuildManageService.OwnerInfoDetailList(gmbunhoArr[0], gmdjgb, gmbunhoArr[2]);
            List<OwnerChangeDTO> OwnerChangeFileList = unBuildManageService.OwnerChangeFileList(gmbunhoArr[0], gmdjgb, gmbunhoArr[2], suilnoVal);
            OwnerChangeDTO OwnerChange = unBuildManageService.OwnerChangeDetail(gmbunhoArr[0], gmdjgb, gmbunhoArr[2], suilnoVal, gmjilno);

            // 기본현황
            model.addAttribute("ledgerInfo", ledgerInfo);
            model.addAttribute("ownerChangeList", ownerChangeList);
            model.addAttribute("ownerInfoList", ownerInfoList);
            model.addAttribute("issue", OwnerChange.getIssue());
            model.addAttribute("OwnerChange", OwnerChange);
            model.addAttribute("OwnerChangeFileList", OwnerChangeFileList);
        }

        model.addAttribute("authSkk", auth.getSkkCd());
        model.addAttribute("gmbunho", gmbunho);
        model.addAttribute("addFlage", addFlage); // 변경/병합 구분
        model.addAttribute("codeLedger", codeLedger); // 등재 선택
        model.addAttribute("codeGmjjgnTemp", codeGmjjgnTemp); // 내국인/외국인
        model.addAttribute("mngAdminstrBld", mngAdminstrBld); // 관리행정동
        return "pages/buildingmanagement/ownerChangeAdd";
    }

    // 현장점검대장조회 화면 이동
    @MenuId("LC")
    @GetMapping("/noperSiteChk/list")
    public String noperSiteChkList(Model model) {
        model.addAttribute("codeLedger", CodeService.findLedget()); // 등재 선택
        model.addAttribute("skk" , CodeService.findSkkList());
        return "pages/buildingmanagement/noperSiteChk";
    }

    // 현장점검대장조회 화면  - List 조회 후 Excel Download
    @PostMapping("/download/noperSiteChkExcel")
    public String noperSiteChkExcel(Model model, @RequestBody NoperSiteChkExcelDTO dto) throws IOException, NoSuchElementException {
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.findNoperSiteChkExcel(dto)));
        return "excelView";
    }

    // 개보수신고관리 화면 이동
    @MenuId("RR")
    @GetMapping("/renovation/list")
    public String renovationList(Model model) {
        // selectBox 대지,산,블록 리스트
        List<Code> gmcsahList = CodeService.findGmcsah();
        // selectBox 대장구분
        List<Code> gmdjgbList = CodeService.findLedget();

        model.addAttribute("skk" , CodeService.findSkkList());
        model.addAttribute("gmcsahList", gmcsahList);
        model.addAttribute("gmdjgbList", gmdjgbList);
        return "pages/buildingmanagement/renovation";
    }

    // 개보수신고관리 화면 - List 조회 후 Excel Download
    @MenuId("RR")
    @PostMapping("/download/renovationExcel")
    public String renovationListExcel(Model model, @RequestBody RenovationDTO dto) throws IOException, NoSuchElementException {
        if (dto.getGmgunil1() != null || dto.getGmgunil2() != null ||
                dto.getGbsgoil1() != null || dto.getGbsgoil2() != null ||
                dto.getJgong1() != null || dto.getJgong2() != null) {
            dto.setGmgunil1(dto.getGmgunil1().replaceAll("-", ""));
            dto.setGmgunil2(dto.getGmgunil2().replaceAll("-", ""));
            dto.setGbsgoil1(dto.getGbsgoil1().replaceAll("-", ""));
            dto.setGbsgoil2(dto.getGbsgoil2().replaceAll("-", ""));
            dto.setJgong1(dto.getJgong1().replaceAll("-", ""));
            dto.setJgong2(dto.getJgong2().replaceAll("-", ""));
        }
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.findRenovationListExcel(dto)));
        return "excelView";
    }

    // 개보수신고관리 등록 화면 이동
    @MenuId("RR")
    @GetMapping("/renovation/add")
    public String renovationAdd(Model model) {
        List<Code> gmdjgbList = CodeService.findLedget();

        model.addAttribute("gmdjgbList", gmdjgbList);
        return "pages/buildingmanagement/renovationAdd";
    }

    // 개보수신고관리 상세 화면 이동
    @MenuId("RR")
    @GetMapping({"/renovation/detail", "/renovation/modify"})
    public String renovationDetail(Model model, RenovationDTO dto) {

        // 신고현황 - 발급기관 조회(Select Box)
        List<RenovationDTO> renovationIssuerKey = unBuildManageService.findRenovationIssuerKey(dto);

        // 기본현황/소유자현황 조회
        RenovationDTO renovationDetailSearchDto = unBuildManageService.findRenovationDetailSearch(dto);

        // 신고현황 조회
        RenovationDTO renovationDetailDto = unBuildManageService.findRenovationDetail(dto);

        if(renovationDetailDto.getWbgb() == null || renovationDetailDto.getWbgb().isEmpty()) renovationDetailDto.setWbgb("-");

//        model.addAttribute("renovationList", renovationList);
        model.addAttribute("renovationIssuerKey", renovationIssuerKey);
        model.addAttribute("renovationDetailSearchDto", renovationDetailSearchDto);
        model.addAttribute("renovationDetailDto", renovationDetailDto);
        return "pages/buildingmanagement/renovationDetail";
    }

    // 철거신고관리 - 조회화면
    @MenuId("DR")
    @GetMapping("/demolition")
    public String demolition(Model model, @RequestParam(value = "gmskk", required = false) String gmskk) {

        //selectBox 대지,산,블록 리스트
        List<Code> gmcsahList = CodeService.findGmcsah();

        //selectBox 등재관리코드 리스트
        List<Code> gmdjgbList = CodeService.findLedget();

        if(!StringUtils.isEmpty(gmskk)){
            model.addAttribute("gmskk", gmskk);
        }

        model.addAttribute("skk" , CodeService.findSkkList());
        model.addAttribute("gmcsahList", gmcsahList);
        model.addAttribute("gmdjgbList", gmdjgbList);
        return "pages/buildingmanagement/demolition";
    }

    // 철거신고관리 - 상세 화면 이동 조회
    @MenuId("DR")
    @GetMapping("/demolition/detail")
    public String demolitionDetail(@AuthenticationPrincipal UserAuthentication auth, Model model, DemolitionDTO dto, LedgerDTO ledgerDto) {
        // 건물주 구분(1내국인/2법인/3외국인)
        List<Code> gmjjgnList = CodeService.findGmjjgnTemp();

        // 발급기관키 정보(기존 selectOrgKeyList)
        List<DemolitionDTO> demolitionIssuerKey = unBuildManageService.findDemolitionIssuerKey(dto); // param = gmskk

        // 철거유형 정보(Select Box)
        List<Code> demolitionDeuh = CodeService.findDemolitionDeuh();

        // 건축물현황 정보(기존 selectNoperInfo)
        List<DemolitionDTO> demolitionNoperInfo = unBuildManageService.findDemolitionNoperInfo(dto); // param = gmskk,gmdjgb,gmseqco

        // 건물주보상현황리스트(기존 selectOwnerGmjList)
        List<DemolitionDTO> demolitionCompensationList = unBuildManageService.findDemolitionCompensationList(dto); // param = gmskk,gmdjgb,gmseqco

        // 세입자현황리스트(기존 selectOwnerSejList)
        List<DemolitionDTO> demolitionTenantList = unBuildManageService.findDemolitionTenantList(dto); // param = gmskk,gmdjgb,gmseqco

        // 루핑을 돌기위해 건물주정보의 count를 가져온다.
        // 건물주정보 count 값 조회(기존 getCountNoperOwner)
        int cntgmj = unBuildManageService.findDemolitionCountNoperOwner(dto); // param = gmskk,gmdjgb,gmseqco
        // 루핑돌면서 해당 건물 소유자현황을 가져온다(기존 selectOwnerChangeList1~9)
        for (int i = 1; i <= cntgmj; i++) {
            List<DemolitionDTO> demolitionOwnerChangeInfo = null;
            if (i == 1) {
                int gmjilno = 1;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
            if (i == 2) {
                int gmjilno = 2;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
            if (i == 3) {
                int gmjilno = 3;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
            if (i == 4) {
                int gmjilno = 4;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
            if (i == 5) {
                int gmjilno = 5;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
            if (i == 6) {
                int gmjilno = 6;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
            if (i == 7) {
                int gmjilno = 7;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
            if (i == 8) {
                int gmjilno = 8;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
            if (i == 9) {
                int gmjilno = 9;
                dto.setGmjilno(gmjilno);
                demolitionOwnerChangeInfo = unBuildManageService.findDemolitionOwnerChangeInfoByGmjilno(dto); // param = gmskk,gmdjgb,gmseqco, gmjilno
                if (demolitionOwnerChangeInfo.size() > 0){
                    model.addAttribute("demolitionOwnerChangeInfo", demolitionOwnerChangeInfo);
                }
            }
        }

        // 소유자변경현황리스트(기존 selectOwnerChangeList)
        List<DemolitionDTO> demolitionOwnerChangeList = unBuildManageService.findDemolitionOwnerChangeList(dto); // param = gmskk,gmdjgb,gmseqco

        // 대지산구분코드 조회
//        dto.setCdid("87");
//        List<DemolitionDTO> gmcsahList = CodeService.findComCode(dto);

        // 소유권구분코드 조회
//        dto.setCdid("29");
//        dto.setCdkey(); //tojisg<-소유권명
//        List<DemolitionDTO> tojisgList = CodeService.findComCode(dto);

        // 지목코드 조회 값이 없어서? / 조회해서 for문 돌려서 값 일치하는 거 입력하는 방식으로?
        dto.setCdid("18");
        if (!demolitionNoperInfo.isEmpty()) {
            if (!demolitionNoperInfo.get(0).getTojijmg().equals("-")) {
                String tojijmg = demolitionNoperInfo.get(0).getTojijmg().toString();
                dto.setCdkey(tojijmg);
                List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);
                demolitionNoperInfo.get(0).setTojijmgnm(tojijmgList.get(0).getCdvalue());
            }
        }
        // 주민구분코드 조회
//        dto.setCdid("14");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 도시계획지역 조회
//        dto.setCdid("N04");
//        dto.setCdkey(); tojidp=도시계획명
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 재개발지구여부 조회
//        dto.setCdid("N05");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

//         지구구분 조회
//        dto.setCdid("N06");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 지구여건 조회
//        dto.setCdid("N07");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 구조코드 조회
//        dto.setCdid("N03");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 용도코드 조회
//        dto.setCdid("N02");
//        dto.setCdkey(dto.getGmyd());
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 행정동코드 조회
        if (!demolitionNoperInfo.isEmpty()) {
            if (!demolitionNoperInfo.get(0).getGmhjdcd().equals("-")) {
                dto.setGmhjdcd(demolitionNoperInfo.get(0).getGmhjdcd());
                String gmhjdnm = unBuildManageService.findDemolitionNoperInfoGmhjdnm(dto);
                demolitionNoperInfo.get(0).setGmhjdnm(gmhjdnm);
            }
        }

        // 법정동코드 조회
        ledgerDto.setGmskk(dto.getGmskk());
        dto.setUserId(auth.getUserId());
        List<LedgerDTO> bjdJusoList = commonService.findBjdJusoList(ledgerDto, auth);

        // 서울시 시군구코드 조회

        // 등재관리코드 리스트
        List<Code> gmdjgbList = CodeService.findLedget();

        if(demolitionNoperInfo.get(0).getDesau() == null || demolitionNoperInfo.get(0).getDesau().isEmpty()) demolitionNoperInfo.get(0).setDesau("-");

        model.addAttribute("gmjjgnList", gmjjgnList);                                       // 주민구분코드 정보
        model.addAttribute("demolitionIssuerKey", demolitionIssuerKey);                     // 발급기관키 정보
        model.addAttribute("demolitionDeuh", demolitionDeuh);                               // 철거유형 정보
        model.addAttribute("demolitionNoperInfo", demolitionNoperInfo);                     // 건축물현황 정보
        if (demolitionCompensationList.size() > 0) {
            model.addAttribute("demolitionCompensationList", demolitionCompensationList);       // 건물주보상현황리스트
        }
        if (demolitionTenantList.size() > 0) {
            model.addAttribute("demolitionTenantList", demolitionTenantList);                   // 세입자현황리스트
        }
        if (demolitionOwnerChangeList.size() > 0) {
            model.addAttribute("demolitionOwnerChangeList", demolitionOwnerChangeList);                             // 소유자변경현황리스트
        }

        return "pages/buildingmanagement/demolitionDetail";
    }


    @PostMapping(value = "/download/ledgerExcel")
    public String downloadExcel(Model model, @RequestBody LedgerDTO dto) throws IOException, NoSuchElementException {
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.findLedgerDataExcel(dto)));
        return "excelView";
    }

    @PostMapping(value = "/download/confirmationExcel")
    public String downloadExcel(Model model, @RequestBody ConfirmationDTO dto) throws IOException, NoSuchElementException {
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.findConfirmationDTODataExcel(dto)));
        return "excelView";
    }

    @PostMapping(value = "/download/ownerChangeExcel")
    public String downloadExcel(Model model, @RequestBody OwnerChangeDTO dto) throws IOException, NoSuchElementException {
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.findOwnerChangeDTODataExcel(dto)));
        return "excelView";
    }

    // 철거신고관리 - 등록 화면 이동
    @MenuId("DR")
    @GetMapping("/demolition/add")
    public String demolitionAdd(Model model) {
        //selectBox 등재관리코드 리스트
        List<Code> gmdjgbList = CodeService.findLedget();

        model.addAttribute("gmdjgbList", gmdjgbList);

        return "pages/buildingmanagement/demolitionAdd";
    }

    // 철거신고관리 - 철거철회 후 화면 이동
    @MenuId("DR")
    @PostMapping("/demolition/delete")
    public String demolitionDelete(Model model, DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) {
        String workid = "KGZ1300P";
        String crgb = "d";
        String degb = "N";
        dto.setDegb(degb);
        dto.setUserId(auth.getUserId());
        dto.setDeptCd(auth.getDeptCd());
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserName(auth.getUserName());
        // 철거철회
        unBuildManageService.demolitionDelete(dto);

        return "redirect:/unlcebldmng/demolition";
    }

    // 부분철거신고관리 - 조회화면
    @MenuId("PR")
    @GetMapping("/partDemolition")
    public String partDemolition(Model model) {
        //selectBox 대지,산,블록 리스트
        List<Code> gmcsahList = CodeService.findGmcsah();

        //selectBox 등재관리코드 리스트
        List<Code> gmdjgbList = CodeService.findLedget();

        model.addAttribute("skk" , CodeService.findSkkList());
        model.addAttribute("gmcsahList", gmcsahList);
        model.addAttribute("gmdjgbList", gmdjgbList);
        return "pages/buildingmanagement/partDemolition";
    }

    // 부분철거신고관리 - 상세 화면 이동 조회
    @MenuId("PR")
    @GetMapping("/partDemolition/detail")
    public String partDemolitionDetail(@AuthenticationPrincipal UserAuthentication auth, Model model, DemolitionDTO dto, LedgerDTO ledgerDto) {
        // 건물주 구분(1내국인/2법인/3외국인)
        List<Code> gmjjgnList = CodeService.findGmjjgnTemp();

        // 발급기관키 정보(기존 selectOrgKeyList)
        List<DemolitionDTO> demolitionIssuerKey = unBuildManageService.findDemolitionIssuerKey(dto); // param = gmskk

        // 건축물현황 정보(기존 selectNoperInfo)
        List<DemolitionDTO> partDemolitionNoperInfo = unBuildManageService.findPartDemolitionNoperInfo(dto); // param = gmskk,gmdjgb,gmseqco

        // 건물주보상현황리스트(기존 selectOwnerGmjList)
        List<DemolitionDTO> partDemolitionCompensationList = unBuildManageService.findPartDemolitionCompensationList(dto); // param = gmskk,gmdjgb,gmseqco

        // 세입자현황리스트(기존 selectOwnerSejList)
        List<DemolitionDTO> partDemolitionTenantList = unBuildManageService.findPartDemolitionTenantList(dto); // param = gmskk,gmdjgb,gmseqco

        // 대지산구분코드 조회
//        dto.setCdid("87");
//        List<DemolitionDTO> gmcsahList = CodeService.findComCode(dto);

        // 소유권구분코드 조회
//        dto.setCdid("29");
//        dto.setCdkey(); //tojisg<-소유권명
//        List<DemolitionDTO> tojisgList = CodeService.findComCode(dto);

        // 지목코드 조회 값이 없어서? / 조회해서 for문 돌려서 값 일치하는 거 입력하는 방식으로?
        dto.setCdid("18");
        if ( partDemolitionNoperInfo.get(0).getTojijmg() != null ) {
            String tojijmg = partDemolitionNoperInfo.get(0).getTojijmg().toString();
            dto.setCdkey(tojijmg);
            List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);
            partDemolitionNoperInfo.get(0).setTojijmgnm(tojijmgList.get(0).getCdvalue());
        }

        // 주민구분코드 조회
//        dto.setCdid("14");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 도시계획지역 조회
//        dto.setCdid("N04");
//        dto.setCdkey(); tojidp=도시계획명
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 재개발지구여부 조회
//        dto.setCdid("N05");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

//         지구구분 조회
//        dto.setCdid("N06");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 지구여건 조회
//        dto.setCdid("N07");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 구조코드 조회
//        dto.setCdid("N03");
//        dto.setCdkey();
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 용도코드 조회
//        dto.setCdid("N02");
//        dto.setCdkey(dto.getGmyd());
//        List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);

        // 행정동코드 조회
        dto.setGmhjdcd(partDemolitionNoperInfo.get(0).getGmhjdcd());
        String gmhjdnm = unBuildManageService.findDemolitionNoperInfoGmhjdnm(dto);
        partDemolitionNoperInfo.get(0).setGmhjdnm(gmhjdnm);

        // 법정동코드 조회
        ledgerDto.setGmskk(dto.getGmskk());
        dto.setUserId(auth.getUserId());
        List<LedgerDTO> bjdJusoList = commonService.findBjdJusoList(ledgerDto, auth);

        // 서울시 시군구코드 조회

        // 등재관리코드 리스트
        List<Code> gmdjgbList = CodeService.findLedget();

        model.addAttribute("gmjjgnList", gmjjgnList);                                       // 주민구분코드 정보
        model.addAttribute("demolitionIssuerKey", demolitionIssuerKey);                     // 발급기관키 정보
        model.addAttribute("partDemolitionNoperInfo", partDemolitionNoperInfo);                     // 건축물현황 정보
        if (partDemolitionCompensationList.size() > 0) {
            model.addAttribute("partDemolitionCompensationList", partDemolitionCompensationList);       // 건물주보상현황리스트
        }
        if (partDemolitionTenantList.size() > 0) {
            model.addAttribute("partDemolitionTenantList", partDemolitionTenantList);                   // 세입자현황리스트
        }

        return "pages/buildingmanagement/partDemolitionDetail";
    }

    // 부분철거신고관리 - 등록 화면 이동
    @MenuId("PR")
    @GetMapping("/partDemolition/add")
    public String partDemolitionAdd(Model model) {
        //selectBox 등재관리코드 리스트
        List<Code> gmdjgbList = CodeService.findLedget();

        model.addAttribute("gmdjgbList", gmdjgbList);

        return "pages/buildingmanagement/partDemolitionAdd";
    }

    // 철거신고관리 조회 화면 - List 조회 후 Excel Download
    @PostMapping("/download/demolitionExcel")
    public String demolitionExcel(Model model, @RequestBody DemolitionExcelDTO dto) throws IOException, NoSuchElementException {
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.findDemolitionExcelList(dto)));
        return "excelView";
    }

    // 철거신고관리 조회 화면 - List 조회 후 Excel Download
    @PostMapping("/download/partDemolitionExcel")
    public String partDemolitionExcel(Model model, @RequestBody PartDemolitionExcelDTO dto) throws IOException, NoSuchElementException {
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.findPartDemolitionExcelList(dto)));
        return "excelView";
    }

    @PostMapping("/download/leaderStatus")
    public String leaderStatusExcel(SearchParamDTO searchParam, Model model) throws IOException, NoSuchElementException {
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.noperStatus(searchParam)));
        return "excelView";
    }
    @PostMapping("/download/leaderStatus/period")
    public String leaderStatusPeriodExcel(SearchParamDTO searchParam, Model model) throws IOException, NoSuchElementException {
        model.addAttribute("list", DTOConverter.convertToExcelList(unBuildManageService.noperStatusByPeriod(searchParam)));
        return "excelView";
    }
}