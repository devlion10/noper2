package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.domain.Code;
import kr.go.seoul.noper2.domain.NoperUserDamo;
import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.repository.UserRepository;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.UnlicensedBuildingManagementService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/unlicensedBuilingManagement")
@RestController
public class UnlicensedBuildingManagementApiController {
    //private final ExcelService excelService;
    private final UnlicensedBuildingManagementService unBuildManageService;
    private final CodeService CodeService;
    private final UserRepository userRepository;

    @PostMapping("/search")
    public List<LedgerDTO> ledgetSearch(Model model, @RequestBody LedgerDTO dto){
        List<LedgerDTO> findLedgrtList = null;
        if(dto.getLegerFlag().equals("ledger")){
            findLedgrtList = unBuildManageService.findLedgrtList(dto);
        }else if(dto.getLegerFlag().equals("newLedger")){
            findLedgrtList = unBuildManageService.newFindLedgerList(dto);
        }
//        log.debug("gmcsah: {}, gmcSah: {}", dto.getGmcsah(), dto.getGmcSah());
        return findLedgrtList;
    }

    @PostMapping("/bldSearch")
    public List<LedgerDTO> mngAdminstrBldSearch( @RequestBody LedgerDTO dto){
        List<LedgerDTO> mngAdminstrBld = unBuildManageService.mngAdminstrBld(dto.getGmskk()); //관리행정동
        return mngAdminstrBld;
    }

    @PostMapping("/ledgerStatus")
    public List<NoperStatusDTO> ledgerStatus(SearchParamDTO param){
        return unBuildManageService.noperStatus(param);
    }

    @PostMapping("/ledgerStatus/period")
    public List<NoperStatusByPeriodDTO> ledgerStatusByPeriod(SearchParamDTO param){
        return unBuildManageService.noperStatusByPeriod(param);
    }

    @PostMapping("/ledgerAdd")
    public ResponseEntity<Void> redgerADD(LedgerDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        dto.setUserId(auth.getUserId());
        unBuildManageService.legerAdd(dto);
        String gmseqco = unBuildManageService.findGesedqco();
        // 무허가 관리번호 채번
        dto.setGmseqco(gmseqco);
        dto.setNewyn("N");
        dto.setDeptCd(auth.getDeptCd());
        dto.setWorkid("KGZ1000P");
        dto.setCrgb("i");
        dto.setUserId(auth.getUserId());
        dto.setUserName(auth.getUserName());
        unBuildManageService.historyMain(dto);
        unBuildManageService.hislegerAdd(dto);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/ledgerDell")
    public ResponseEntity<Void> ledgerDell(@RequestBody LedgerDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        dto.setUserId(auth.getUserId());
        dto.setDeptCd(auth.getDeptCd());
        dto.setWorkid("KGZ1000P");
        dto.setCrgb("d");
        dto.setUserName(auth.getUserName());
        dto.setNewyn("N");
        unBuildManageService.ledgerDell(dto);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/ledgerApproval")
    public ResponseEntity<Void> ledgerApproval(@RequestBody LedgerDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        unBuildManageService.ledgerApproval(dto, auth);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

   /* @PostMapping("/findGmbalno")
    public String findGmbalno(@RequestBody Ledger ledger){
        String gmbalno = unBuildManageService.noperGmbalno(ledger.getLedger().getGmskk(), ledger.getLedger().getGmdjgb(), ledger.getLedger().getGmseqco());
        return gmbalno;
    }*/

    @PostMapping("/fixAction")
    public List<NoperFixDTO> fixAction(@RequestBody NoperFixDTO dto, @AuthenticationPrincipal UserAuthentication auth){

        if(dto.getFixParam().equals("Dell")){
            dto.setDeptCd(auth.getDeptCd());
            dto.setWorkid("KGZ1000P");
            dto.setUserId(auth.getUserId());
            dto.setUserName(auth.getUserName());
            dto.setCrgb("d");
            unBuildManageService.delNoperFix(dto);
        }
        if(dto.getFixParam().equals("Add")){
            dto.setDeptCd(auth.getDeptCd());
            dto.setWorkid("KGZ1000P");
            dto.setUserId(auth.getUserId());
            dto.setUserName(auth.getUserName());
            dto.setCrgb("i");
            String gmjilno = unBuildManageService.noperGmilno(dto.getGmskk(),dto.getGmdjgb(), dto.getGmseqco());
            String gmbalno = unBuildManageService.noperGmbalno(dto.getGmskk(),dto.getGmdjgb(), dto.getGmseqco());
            dto.setGmilno(gmjilno);
            dto.setGmbalno(gmbalno);
            unBuildManageService.addNoperFix(dto);
            unBuildManageService.noperHisNoperFix(dto);
        }
        if(dto.getFixParam().equals("Mody")){
            dto.setDeptCd(auth.getDeptCd());
            dto.setWorkid("KGZ1000P");
            dto.setUserId(auth.getUserId());
            dto.setUserName(auth.getUserName());
            dto.setCrgb("u");
            unBuildManageService.updateNoperFix(dto);
        }
        List<NoperFixDTO> findnoperFix = unBuildManageService.findnoperFix(dto.getGmskk(),dto.getGmdjgb(), dto.getGmseqco());
        return findnoperFix;
    }


    @PostMapping("/siteChkAction")
    public List<NoperSiteChkDTO> siteChkAction(@RequestBody NoperSiteChkDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        if(dto.getSitechkParam().equals("Dell")){
            dto.setCrgb("d");
           // unBuildManageService.delNoperFix(dto);
        }
        if(dto.getSitechkParam().equals("Add")){
            dto.setCrgb("i");
            dto.setChkChargeId(auth.getUserId());
            dto.setChkChargeNm(auth.getUserName());
            dto.setWorkid("KGZ1000P");
            dto.setChkChargeGmskk(auth.getSkkCd());
            dto.setRegtId(auth.getUserId());
            String noperChkino = unBuildManageService.noperChkino(dto.getGmskk(),dto.getGmdjgb(), dto.getGmseqco());
            dto.setChkilno(noperChkino);
            dto.setUserId(auth.getUserId());
            dto.setDeptCd(auth.getDeptCd());
            dto.setUserName(auth.getUserName());
            unBuildManageService.addNoperSiteChk(dto);
        }
        if(dto.getSitechkParam().equals("Mody")){
            dto.setCrgb("u");
            dto.setRegtId(auth.getUserId());
            dto.setChkChargeId(auth.getRegistId());
            dto.setDeptCd(auth.getDeptCd());
            dto.setWorkid("KGZ1000P");
            dto.setUserName(auth.getUserName());
            dto.setUserId(auth.getUserId());
            unBuildManageService.updateNoperSiteChk(dto);
        }
        if(dto.getSitechkParam().equals("AcMody")){
            dto.setCrgb("i");
            dto.setWorkid("KGZ1000P");
            dto.setRegtId(auth.getUserId());
            dto.setTrsctCnfmId(auth.getUserId());
            dto.setTrsctCnfmNm(auth.getUserName());
            dto.setTrsctCnfmGmskk(auth.getSkkCd());
            dto.setUserId(auth.getUserId());
            dto.setUserName(auth.getUserName());
           unBuildManageService.updateActionNoperSiteChk(dto);
        }
        List<NoperSiteChkDTO> findNoperLedgerSiteChk = unBuildManageService.findNoperLedgerSiteChk(dto.getGmskk(),dto.getGmdjgb(), dto.getGmseqco());
        return findNoperLedgerSiteChk;
    }

    @PostMapping("/ledgerUpdate")
    public ResponseEntity<Void> ledgerUpdate(@RequestBody Ledger ledger, @AuthenticationPrincipal UserAuthentication auth){

        if(ledger.getLedger() != null && !ledger.getLedger().equals("")){
            // 대장 수정
            unBuildManageService.updateNoperLeger(ledger.getLedger(), auth);
        }
        if(ledger.getOwnerDto() != null && !ledger.getOwnerDto().equals("")){
            // 건물주 일련번호채번
            String gmjilno = unBuildManageService.noperGmjilNo(ledger.getOwnerDto().getGmskk(), ledger.getOwnerDto().getGmdjgb(), ledger.getOwnerDto().getGmseqco());
            // 소유주 일련번호채번
            String noperSuilNo = unBuildManageService.noperSuilNo(ledger.getOwnerDto().getGmskk(), ledger.getOwnerDto().getGmdjgb(), ledger.getOwnerDto().getGmseqco(), gmjilno);
            ledger.getOwnerDto().setGmjilno(gmjilno);
            ledger.getOwnerDto().setSuilno(noperSuilNo);
            ledger.getOwnerDto().setUserId(auth.getUserId());
            ledger.getOwnerDto().setIsLocal("true");
            unBuildManageService.saveNoperOwner(ledger.getOwnerDto());
            unBuildManageService.noperHisNoperOwer(ledger.getOwnerDto());
        }

        /*
        // 건물주 추가
        for (NoperOwnerDTO owner : ledger.getOwner()) {
            owner.setGmskk(ledger.getLedger().getGmskk());
            owner.setGmdjgb(ledger.getLedger().getGmdjgb());
            owner.setGmseqco(ledger.getLedger().getGmseqco());
            // 건물주 일련번호채번
            String gmjilno = unBuildManageService.noperGmjilNo(ledger.getLedger().getGmskk(), ledger.getLedger().getGmdjgb(), ledger.getLedger().getGmseqco());
            // 소유주 일련번호채번
            String noperSuilNo = unBuildManageService.noperSuilNo(ledger.getLedger().getGmskk(), ledger.getLedger().getGmdjgb(), ledger.getLedger().getGmseqco(), gmjilno);
            owner.setGmjilno(gmjilno);
            owner.setSuilno(noperSuilNo);
            owner.setRegistId(registId);
            owner.setIsLocal("true");
            owner.setGmjjumin(owner.getGmjjumin1() + owner.getGmjjumin2());
            unBuildManageService.saveNoperOwner(owner);
            unBuildManageService.noperHisNoperOwer(owner);
        }

        // 개보수신고내역
        for (NoperFixDTO fix : ledger.getFix()) {
            fix.setGmskk(ledger.getLedger().getGmskk());
            fix.setGmdjgb(ledger.getLedger().getGmdjgb());
            fix.setGmseqco(ledger.getLedger().getGmseqco());
            // 개보수 일련번호채번
            String gmjilno = unBuildManageService.noperGmilno(ledger.getLedger().getGmskk(), ledger.getLedger().getGmdjgb(), ledger.getLedger().getGmseqco());
            fix.setGmilno(gmjilno);
            fix.setRegistId(registId);
            unBuildManageService.addNoperFix(fix);
            unBuildManageService.noperHisNoperFix(fix);
        }
        */
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //현장점검대장조회 List 조회
    @PostMapping("/noperSiteChk/search")
    public List<NoperSiteChkDTO> noperSiteChkSearch(Model model, @RequestBody NoperSiteChkDTO dto){
        // 점검년도(chkDate)가 전체(all)가 아닐때
        // 점검년도(yyyy)에 0101/1231을 추가하여 yyyy0101 / yyyy1231로 변경.
        if (!dto.getChkDate().equals("")) {
            dto.setChkStDate(dto.getChkDate()+"0101");
            dto.setChkEndDate(dto.getChkDate()+"1231");
        }
        List<NoperSiteChkDTO> noperSiteChkList = null;
        noperSiteChkList = unBuildManageService.findNoperSiteChk(dto);
        return noperSiteChkList;
    }

    @PostMapping("/confirmation")
    public List<ConfirmationDTO> confirmation(Model model, @RequestBody ConfirmationDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        List<ConfirmationDTO> findConfirmationList = null;
        String delc = ""; // 삭제사유
        dto.setDecision(auth.getDeptCd());
        dto.setDeptCd(auth.getDeptCd());
        dto.setWorkid("KGZ1400P");
        dto.setCrgb(auth.getUserName());
        if(dto.getConfirmationFlag().equals("lsit")){
            findConfirmationList = unBuildManageService.findConfirmation(dto);
        }else if(dto.getConfirmationFlag().equals("add")){
            dto.setUserId(auth.getUserId());
            dto.setSinjumin(dto.getSinjumin1() + "-" + dto.getSinjumin2());
            // confirmationHisAdd 는 해당 서비스 메서드안에서 호출함.
            findConfirmationList = unBuildManageService. confirmationAdd(dto, auth);
        }else if(dto.getConfirmationFlag().equals("mody")){
            dto.setCrgb("u");
            dto.setUserId(auth.getUserId());
            dto.setSinjumin(dto.getSinjumin1() + "-" + dto.getSinjumin2());
            unBuildManageService.confirmationUpdate(dto);
            unBuildManageService.confirmationHisAdd(dto);
        } else if (dto.getConfirmationFlag().equals("del")) {
            delc = dto.getDelc(); // 삭제사유
            dto = unBuildManageService.getConfirmationDOC(dto);
            dto.setCrgb("d");
            dto.setUserId(auth.getUserId());
            dto.setDelc(delc);
            unBuildManageService.confirmationDel(dto);
            unBuildManageService.confirmationHisAdd(dto);
        }
        return findConfirmationList;
    }

    @PostMapping("/ownerChange")
    public List<OwnerChangeDTO> ownerChange(Model model, Ledger ledger, @RequestBody OwnerChangeDTO dto, @AuthenticationPrincipal UserAuthentication auth) {
        List<OwnerChangeDTO> findOwnerChangeList = null;
        dto.setWorkid("KGZ1520P");
        dto.setDeptCd(auth.getDeptCd());
        dto.setCrgb("i");
        dto.setUserId(auth.getUserId());
        dto.setUserName(auth.getUserName());
        switch (dto.getOwnerChangeFlag()) {
            case "list":
                findOwnerChangeList = unBuildManageService.findOwnerChange(dto);
                break;
            case "save":
                if (dto.getChangDivision().equals("absorption")) {
                    List<NoperOwnerDTO> absorptionNoperOwners = unBuildManageService.absorptionNoperOwner(dto.getGmskk(), dto.getGmdjgb(), dto.getGmseqco());
                    for (NoperOwnerDTO absorptionNoperOwner : absorptionNoperOwners) {
                        dto.setJgmjname(absorptionNoperOwner.getGmjname());
                        dto.setJgmjjumin(absorptionNoperOwner.getGmjjumin1() + absorptionNoperOwner.getGmjjumin2());
                        dto.setJnewAddr1(absorptionNoperOwner.getNewAddr1());
                        dto.setJnewAddr2(absorptionNoperOwner.getNewAddr2());
                        unBuildManageService.OwnerChangeUpdte(dto);
                    }
                } else {
                    unBuildManageService.OwnerChangeUpdte(dto);
                }
                unBuildManageService.historyMain(dto, auth);
                break;
            case "mody":
                dto.setGmskk(auth.getSkkCd());
                unBuildManageService.OwnerChangeUpdts(dto);
                break;
            case "absorptionDetail":
                LedgerDTO absorptionNoperMaster = unBuildManageService.absorptionNoperMaster(dto.getGmskk(), dto.getGmdjgb(), dto.getGmseqco());
                List<NoperOwnerDTO> absorptionNoperOwner = unBuildManageService.absorptionNoperOwner(dto.getGmskk(), dto.getGmdjgb(), dto.getGmseqco());
                model.addAttribute("absorptionNoperMaster", absorptionNoperMaster);
                model.addAttribute("absorptionNoperOwner", absorptionNoperOwner);
                Map<String, Object> data = new HashMap<>();
                data.put("absorptionNoperMaster", absorptionNoperMaster);
                data.put("absorptionNoperOwner", absorptionNoperOwner);
                unBuildManageService.historyMain(dto, auth);
                break;
            case "dell":
                dto.setCrgb("d");
                unBuildManageService.OwnerChangeDell(dto);
                unBuildManageService.historyMain(dto, auth);
                break;
        }
        return findOwnerChangeList;
    }

    @PostMapping("/ownerChangeDetail")
    public Object ownerChangeDetail(Model model,@RequestBody OwnerChangeDTO dto, @AuthenticationPrincipal UserAuthentication auth) {
        dto.setWorkid("KGZ1520P");
        dto.setDeptCd(auth.getDeptCd());
        dto.setCrgb("i");
        dto.setUserId(auth.getUserId());
        dto.setUserName(auth.getUserName());
        Map<String, Object> data = new HashMap<>();
        if (dto.getOwnerChangeFlag().equals("absorptionDetail")) {
            LedgerDTO absorptionNoperMaster = unBuildManageService.absorptionNoperMaster(dto.getGmskk(), dto.getGmdjgb(), dto.getGmseqco());
            List<NoperOwnerDTO> absorptionNoperOwner = unBuildManageService.absorptionNoperOwner(dto.getGmskk(), dto.getGmdjgb(), dto.getGmseqco());
            data.put("absorptionNoperMaster", absorptionNoperMaster);
            data.put("absorptionNoperOwner", absorptionNoperOwner);
        }
        return data;
    }


    // 개보수신고관리 조회 - List 조회
    @PostMapping("/renovation/search")
    public List<RenovationDTO> renovationSearch(@RequestBody RenovationDTO dto){
        List<RenovationDTO> renovationList = null;
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
        renovationList = unBuildManageService.findRenovationList(dto);
        return renovationList;
    }

    // 개보수신고관리 등록화면 - 조회
    @PostMapping("/renovation/addSearch")
    public ResponseEntity<Map<String, Object>> renovationAddSearch(@RequestBody RenovationDTO dto) {
        Map<String, Object> map = new HashMap<>();

        List<RenovationDTO> renovationAddData = unBuildManageService.findRenovationAddSearch(dto);
        if (renovationAddData.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        dto.setGmskk(renovationAddData.get(0).getGmskk());
        List<RenovationDTO> renovationIssuerKey = unBuildManageService.findRenovationIssuerKey(dto);

        map.put("renovationAddData", renovationAddData);
        map.put("renovationIssuerKey", renovationIssuerKey);

        return ResponseEntity.ok(map);
    }

    // 개보수신고관리 등록화면 - 신고사항 등록
    @PostMapping("/renovation/addSave")
    public RenovationDTO saveRenovation(@RequestBody RenovationDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1200P";
//        String crgb = "i";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";
        String deptCd = user.getDeptCd();
        String workid = "KGZ1200P";
        String crgb = "i";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        return unBuildManageService.saveRenovation(dto);
    }

    // 개보수신고관리 수정화면 - 신고사항 수정
    @PostMapping("/renovation/modify")
    public void modifyRenovation(@RequestBody RenovationDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);
//        String deptCd = "11130";
//        String workid = "KGZ1200P";
//        String crgb = "u";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1200P";
        String crgb = "u";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        unBuildManageService.modifyRenovation(dto);
    }

    // 철거신고관리 조회 화면 - List 조회
    @PostMapping("/demolition/search")
    public List<DemolitionDTO> demolitionSearch(@RequestBody DemolitionDTO dto) {
        List<DemolitionDTO> demolitionList = null;
        if (dto.getDeilja() != null || dto.getDeilja1() != null) {
            dto.setDeilja(dto.getDeilja().replaceAll("-", ""));
            dto.setDeilja1(dto.getDeilja1().replaceAll("-", ""));
        }
        // 철거신고 조회
        demolitionList = unBuildManageService.findDemolitionList(dto);

        return demolitionList;
    }

    // 철거신고관리 등록 - 철거 기본 정보 조회
    @PostMapping("/demolition/add/search")
    public Map<String, Object> demolitionAddSearch(@RequestBody DemolitionDTO dto) {
        Map<String, Object> map = new HashMap<>();
        // 발급기관키 정보(기존 selectOrgKeyList)
        List<DemolitionDTO> demolitionIssuerKey = unBuildManageService.findDemolitionIssuerKey(dto); // param = gmskk

        // 철거유형 정보(Select Box)
        List<Code> demolitionDeuh = CodeService.findDemolitionDeuh();

        // 건축물현황 정보(기존 selectNoperInfoS2)
        List<DemolitionDTO> demolitionNoperInfo = unBuildManageService.findDemolitionAddNoperInfo(dto); // param = gmskk,gmdjgb,gmseqco

        // 지목코드 조회
        dto.setCdid("18");

        if (!demolitionNoperInfo.isEmpty()) {
            if (!demolitionNoperInfo.get(0).getTojijmg().equals("-")){
                dto.setCdkey(demolitionNoperInfo.get(0).getTojijmg());
                List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);
                demolitionNoperInfo.get(0).setTojijmgnm(tojijmgList.get(0).getCdvalue());
            }
        }

        // 행정동코드 조회
        if (!demolitionNoperInfo.isEmpty()) {
            dto.setGmhjdcd(demolitionNoperInfo.get(0).getGmhjdcd());
        }
        String gmhjdnm = unBuildManageService.findDemolitionNoperInfoGmhjdnm(dto);
        if (!demolitionNoperInfo.isEmpty()) {
            demolitionNoperInfo.get(0).setGmhjdnm(gmhjdnm);
        }

        map.put("demolitionIssuerKey", demolitionIssuerKey);
        map.put("demolitionDeuh", demolitionDeuh);

        if (!demolitionNoperInfo.isEmpty()) {
            map.put("demolitionNoperInfo", demolitionNoperInfo);
        }

        return map;
    }

    // 철거신고관리 등록 - 신고사항 등록
    @PostMapping("/demolition/add/reportSave")
    public int demolitionDetailReportSave(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

        String deptCd = user.getDeptCd();
        String workid = "KGZ1300P";
        String crgb = "i";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        if (dto.getDeilja() != null) {
            dto.setDeilja(dto.getDeilja().replaceAll("-", ""));
        }
        if (dto.getGmgunil() != null) {
            dto.setGmgunil(dto.getGmgunil().replaceAll("-", ""));
        }

        int result = unBuildManageService.saveDemolitionRemoveMaster(dto);

        return result;
    }

    // 철거신고관리 상세 - 신고사항 변경 및 List 조회
    @PostMapping("/demolition/detail/reportModify")
    public List<DemolitionDTO> demolitionDetailReportModify(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);
//        String deptCd = "11130";
//        String workid = "KGZ1300P";
//        String crgb = "u";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1300P";
        String crgb = "u";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        if (dto.getDeilja() != null) {
            dto.setDeilja(dto.getDeilja().replaceAll("-", ""));
        }
        unBuildManageService.modifyDemolitionDetailReport(dto);

        List<DemolitionDTO> demolitionList;
        demolitionList = unBuildManageService.findDemolitionNoperInfo(dto);

        return demolitionList;
    }

    // 철거신고관리 상세 - 건물주보상현황 추가 및 List 조회
    @PostMapping("/demolition/detail/compensationAdd")
    public List<DemolitionDTO> demolitionDetailCompensationAdd(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1301P";
//        String crgb = "i";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1301P";
        String crgb = "i";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        if (dto.getGmjbsilja() != null) {
            dto.setGmjbsilja(dto.getGmjbsilja().replaceAll("-", ""));
        }
        if (dto.getGmjjbilja() != null) {
            dto.setGmjjbilja(dto.getGmjjbilja().replaceAll("-", ""));
        }
        if (dto.getGmjbsgum() != null) {
            dto.setGmjbsgum(dto.getGmjbsgum().replaceAll(",", ""));
        }

        int result = unBuildManageService.saveDemolitionDetailCompensation(dto);
        List<DemolitionDTO> demolitionCompensationList = null;

        if (result != 0) {
            // 건물주보상현황리스트(기존 selectOwnerGmjList)
            demolitionCompensationList = unBuildManageService.findDemolitionCompensationList(dto); // param = gmskk,gmdjgb,gmseqco
        }

        return demolitionCompensationList;
    }

    // 철거신고관리 상세 - 건물주보상현황 삭제 및 List 조회
    @PostMapping("/demolition/detail/compensationDelete")
    public List<DemolitionDTO> demolitionDetailCompensationDelete(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1301P";
//        String crgb = "d";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1301P";
        String crgb = "d";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        int result = unBuildManageService.deleteDemolitionDetailCompensation(dto);
        List<DemolitionDTO> demolitionCompensationList = unBuildManageService.findDemolitionCompensationList(dto); // param = gmskk,gmdjgb,gmseqco
        return demolitionCompensationList;
    }

    // 철거신고관리 상세 - 세입자보상현황 추가 및 List 조회
    @PostMapping("/demolition/detail/tenantAdd")
    public List<DemolitionDTO> demolitionDetailTenantAdd(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1302P";
//        String crgb = "i";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1302P";
        String crgb = "i";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        if (dto.getSebsilja() != null) {
            dto.setSebsilja(dto.getSebsilja().replaceAll("-", ""));
        }
        if (dto.getSejaptilja() != null) {
            dto.setSejaptilja(dto.getSejaptilja().replaceAll("-", ""));
        }
        if (dto.getSebsgum() != null) {
            dto.setSebsgum(dto.getSebsgum().replaceAll(",", ""));
        }
        if (dto.getSeisgum() != null) {
            dto.setSeisgum(dto.getSeisgum().replaceAll(",", ""));
        }

        int resultNum = unBuildManageService.saveDemolitionDetailTenant(dto);
        List<DemolitionDTO> demolitionTenantList = null;

        if (resultNum != 0) {
            // 세입자현황리스트(기존 selectOwnerSejList)
            demolitionTenantList = unBuildManageService.findDemolitionTenantList(dto); // param = gmskk,gmdjgb,gmseqco
        }

        return demolitionTenantList;
    }

    // 철거신고관리 상세 - 세입자보상현황 삭제 및 List 조회
    @PostMapping("/demolition/detail/tenantDelete")
    public List<DemolitionDTO> demolitionDetailTenantDelete(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1302P";
//        String crgb = "d";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1302P";
        String crgb = "d";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        int result = unBuildManageService.deleteDemolitionDetailTenant(dto);
        List<DemolitionDTO> demolitionTenantList = unBuildManageService.findDemolitionTenantList(dto); // param = gmskk,gmdjgb,gmseqco
        return demolitionTenantList;
    }

    // 부분철거신고관리 조회 화면 - List 조회
    @PostMapping("/partDemolition/search")
    public List<DemolitionDTO> partDemolitionSearch(@RequestBody DemolitionDTO dto) {
        List<DemolitionDTO> partDemolitionList = null;
        if (dto.getGmbilja1() != null || dto.getGmbilja2() != null) {
            dto.setGmbilja1(dto.getGmbilja1().replaceAll("-", ""));
            dto.setGmbilja2(dto.getGmbilja2().replaceAll("-", ""));
        }
        // 부분철거신고 조회
        partDemolitionList = unBuildManageService.findPartDemolitionList(dto);

        return partDemolitionList;
    }

    // 부분철거신고관리 등록 - 철거 기본 정보 조회
    @PostMapping("/partDemolition/add/search")
    public Map<String, Object> partDemolitionAddSearch(@RequestBody DemolitionDTO dto) {
        Map<String, Object> map = new HashMap<>();

        // 건축물현황 정보(기존 selectNoperInfo)
        List<DemolitionDTO> partDemolitionAddNoperInfo = unBuildManageService.findPartDemolitionAddNoperInfo(dto); // param = gmskk,gmdjgb,gmseqco

        // 지목코드 조회
        if ( partDemolitionAddNoperInfo.get(0).getTojijmg() != null ){
            dto.setCdid("18");
            dto.setCdkey(partDemolitionAddNoperInfo.get(0).getTojijmg());
            List<DemolitionDTO> tojijmgList = CodeService.findComCode(dto);
            partDemolitionAddNoperInfo.get(0).setTojijmgnm(tojijmgList.get(0).getCdvalue());
        }

        // 행정동코드 조회
        dto.setGmhjdcd(partDemolitionAddNoperInfo.get(0).getGmhjdcd());
        String gmhjdnm = unBuildManageService.findDemolitionNoperInfoGmhjdnm(dto);
        partDemolitionAddNoperInfo.get(0).setGmhjdnm(gmhjdnm);

        if (!partDemolitionAddNoperInfo.isEmpty()) {
            map.put("partDemolitionAddNoperInfo", partDemolitionAddNoperInfo);
        }
        return map;
    }

    // 부분철거신고관리 상세 - 건물주보상현황 추가 및 List 조회
    @PostMapping("/partDemolition/detail/compensationAdd")
    public List<DemolitionDTO> partDemolitionDetailCompensationAdd(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1601P";
//        String crgb = "i";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1601P";
        String crgb = "i";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        if (dto.getGmbilja() != null) {
            dto.setGmbilja(dto.getGmbilja().replaceAll("-", ""));
        }
        if (dto.getGmjbsilja() != null) {
            dto.setGmjbsilja(dto.getGmjbsilja().replaceAll("-", ""));
        }
        if (dto.getGmjjbilja() != null) {
            dto.setGmjjbilja(dto.getGmjjbilja().replaceAll("-", ""));
        }
        if (dto.getGmjbsgum() != null) {
            dto.setGmjbsgum(dto.getGmjbsgum().replaceAll(",", ""));
        }

        int resultNum = unBuildManageService.savePartDemolitionDetailCompensation(dto);
        List<DemolitionDTO> partDemolitionCompensationList = null;
//        if (resultNum > 0){
        // 건물주보상현황리스트(기존 selectOwnerGmjList)
        partDemolitionCompensationList = unBuildManageService.findPartDemolitionCompensationList(dto); // param = gmskk,gmdjgb,gmseqco
//        }
        return partDemolitionCompensationList;
    }

    // 부분철거신고관리 상세 - 건물주보상현황 삭제 및 List 조회
    @PostMapping("/partDemolition/detail/compensationDelete")
    public List<DemolitionDTO> partDemolitionDetailCompensationDelete(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1601P";
//        String crgb = "d";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1601P";
        String crgb = "d";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        int resultNum = unBuildManageService.deletePartDemolitionDetailCompensation(dto);
        List<DemolitionDTO> partDemolitionCompensationList = unBuildManageService.findPartDemolitionCompensationList(dto); // param = gmskk,gmdjgb,gmseqco
        return partDemolitionCompensationList;
    }

    // 부분철거신고관리 상세 - 세입자보상현황 추가 및 List 조회
    @PostMapping("/partDemolition/detail/tenantAdd")
    public List<DemolitionDTO> partDemolitionDetailTenantAdd(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1602P";
//        String crgb = "i";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1602P";
        String crgb = "i";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        if (dto.getSebsilja() != null) {
            dto.setSebsilja(dto.getSebsilja().replaceAll("-", ""));
        }
        if (dto.getSejaptilja() != null) {
            dto.setSejaptilja(dto.getSejaptilja().replaceAll("-", ""));
        }
        if (dto.getSebsgum() != null) {
            dto.setSebsgum(dto.getSebsgum().replaceAll(",", ""));
        }
        if (dto.getSeisgum() != null) {
            dto.setSeisgum(dto.getSeisgum().replaceAll(",", ""));
        }

        int resultNum = unBuildManageService.savePartDemolitionDetailTenant(dto);
        List<DemolitionDTO> partDemolitionTenantList = null;

        if (resultNum != 0) {
            // 세입자현황리스트(기존 selectOwnerSejList)
            partDemolitionTenantList = unBuildManageService.findDemolitionTenantList(dto); // param = gmskk,gmdjgb,gmseqco
        }

        return partDemolitionTenantList;
    }

    // 부분철거신고관리 상세 - 세입자보상현황 삭제 및 List 조회
    @PostMapping("/partDemolition/detail/tenantDelete")
    public List<DemolitionDTO> partDemolitionDetailTenantDelete(@RequestBody DemolitionDTO dto, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        NoperUserDamo user = userRepository.findUserById(auth.getUserId()).orElseThrow(NoSuchElementException::new);

//        String deptCd = "11130";
//        String workid = "KGZ1602P";
//        String crgb = "d";
//        String registId = "A1";
//        String userName = "관리자";
//        String degb = "";

        String deptCd = user.getDeptCd();
        String workid = "KGZ1602P";
        String crgb = "d";
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        String degb = "";

        dto.setDeptCd(deptCd);
        dto.setWorkid(workid);
        dto.setCrgb(crgb);
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setDegb(degb);

        int resultNum = unBuildManageService.deletePartDemolitionDetailTenant(dto);
        List<DemolitionDTO> partDemolitionTenantList = unBuildManageService.findPartDemolitionTenantList(dto); // param = gmskk,gmdjgb,gmseqco
        return partDemolitionTenantList;
    }
}

@Getter
class Ledger {
    LedgerDTO ledger;
    NoperOwnerDTO ownerDto;
    ArrayList<NoperOwnerDTO> owner;
    ArrayList<NoperFixDTO> fix;
}

