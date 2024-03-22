package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.domain.example.Board;
import kr.go.seoul.noper2.dto.BoardDTO;
import kr.go.seoul.noper2.dto.HistoryMainDTO;
import kr.go.seoul.noper2.dto.LedgerDTO;
import kr.go.seoul.noper2.dto.NoperFixDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.BoardService;
import kr.go.seoul.noper2.service.BuildingManagementInGreenBeltService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/bldmngingb")
@RestController
public class BuildingManagementInGreenBeltApiController {
    private final BuildingManagementInGreenBeltService bldmngingbService;

    /* 일일처리내역 */
    @PostMapping("/dailyHistory/search")
    public HashMap<String, Object> historySearch(Model model, @RequestBody HistoryMainDTO dto){
        List<HistoryMain> findHistoryList = bldmngingbService.findHistoryList(dto);
        int findHistoryListCount = findHistoryList.size();

        HashMap<String, Object> history = new HashMap<>();
        history.put("findHistoryList", findHistoryList);
        history.put("findHistoryListCount", findHistoryListCount);

        return history;
    }

    /* 대장관리 */
    @PostMapping("/ledger/search")
    public HashMap<String, Object> ledgerSearch(Model model, @RequestBody LedgerDTO dto){

        List<LedgerDTO> findLedgerList = bldmngingbService.findLedgerList(dto);
        int findLedgerListCount = findLedgerList.size();

        HashMap<String, Object> ledger = new HashMap<>();
        ledger.put("findLedgerList", findLedgerList);
        ledger.put("findLedgerListCount", findLedgerListCount);

        return ledger;
    }

    @PostMapping("/ledgerAdmSeq")
    public ResponseEntity<Integer> ledgerAdmSeq(@AuthenticationPrincipal UserAuthentication auth){
        String skkCd = auth.getSkkCd();

        // 기개발 대장번호 채번
        String admSeqco = bldmngingbService.findAdmSeq(skkCd);

        return new ResponseEntity<>(Integer.parseInt(admSeqco), HttpStatus.OK);
    }

    @PostMapping("/ledgerAdd")
    public ResponseEntity<Void> redgerADD(@RequestBody LedgerDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        //String registId = auth.getUserId(); //NOPER_USER geon3
        String skkCd = auth.getSkkCd();
        dto.getLegerLimited().setSkkCd(skkCd);    //dto.getlegerLimited().setSkkCd(skkCd);

        // 기개발 대장번호 채번
        String admSeqco = bldmngingbService.findAdmSeq(skkCd);
        dto.getLegerLimited().setAdmSeq(admSeqco);

        bldmngingbService.legerAdd(dto, auth);
        //bldmngingbService.hisLegerAdd(dto, auth);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/ownerAction")
    public List<LedgerDTO.LegerOwner> ownerAction(@RequestBody LedgerDTO.LegerOwner dto, @AuthenticationPrincipal UserAuthentication auth){
        String skkCd = auth.getSkkCd();
        String deptCd = auth.getDeptCd();
        String registId = auth.getUserId();
        String updateId = auth.getUserId();
        dto.setRegistId(registId);
        dto.setSkkCd(skkCd);
        dto.setDeptCd(deptCd);
        //dto.setCrgb("u");

        if(dto.getOwnerParam().equals("Dell")){
            dto.setCrgb("d");
            bldmngingbService.hisLegerOwnerAdd(dto);// his
            bldmngingbService.delLegerOwner(dto); // delete
        }
        if(dto.getOwnerParam().equals("Add")){
            dto.setCrgb("i");
            dto.setDeptCd(deptCd);
            bldmngingbService.legerOwnerAdd(dto);// insert
            bldmngingbService.hisLegerOwnerAdd(dto);// his
        }
        if(dto.getOwnerParam().equals("Mody")){
            dto.setCrgb("u");
            dto.setUpdateId(updateId);
            bldmngingbService.legerOwnerUpdate(dto);// update
            bldmngingbService.hisLegerOwnerAdd(dto);// his
        }
        List<LedgerDTO.LegerOwner> legerOwner = bldmngingbService.legerOwner(dto); // select
        return legerOwner;
    }
    @PostMapping("/dongAction")
    public List<LedgerDTO.LegerDong> dongAction(@RequestBody LedgerDTO.LegerDong dto, @AuthenticationPrincipal UserAuthentication auth){
        String skkCd = auth.getSkkCd();
        String deptCd = auth.getDeptCd();
        String registId = auth.getUserId();
        String updateId = auth.getUserId();
        //dto.setUpdateId(updateId);
        dto.setRegistId(registId);
        dto.setSkkCd(skkCd);
        dto.setDeptCd(deptCd);
        //dto.setCrgb("u");

        if(dto.getDongParam().equals("Dell")){
            dto.setCrgb("d");
            bldmngingbService.hisLegerDongAdd(dto);// his
            bldmngingbService.delLegerDong(dto); // delete
        }
        if(dto.getDongParam().equals("Add")){
            dto.setCrgb("i");
            bldmngingbService.legerDongAdd(dto);// insert
            bldmngingbService.hisLegerDongAdd(dto);// his
        }
        if(dto.getDongParam().equals("Mody")){
            dto.setCrgb("u");
            dto.setUpdateId(updateId);
            bldmngingbService.legerDongUpdate(dto);// update
            bldmngingbService.hisLegerDongAdd(dto);// his
        }
        List<LedgerDTO.LegerDong> legerDong = bldmngingbService.legerDong(dto); // select
        return legerDong;
    }
    @PostMapping("/structuresAction")
    public List<LedgerDTO.LegerStructures> structuresAction(@RequestBody LedgerDTO.LegerStructures dto, @AuthenticationPrincipal UserAuthentication auth){
        String skkCd = auth.getSkkCd();
        String deptCd = auth.getDeptCd();
        String registId = auth.getUserId();
        String updateId = auth.getUserId();
        dto.setRegistId(registId);
        dto.setDeptCd(deptCd);
        dto.setSkkCd(skkCd);
        //dto.setCrgb("u");

        if(dto.getStParam().equals("Dell")){
            dto.setCrgb("d");
            bldmngingbService.hisLegerStructuresAdd(dto);// his
            bldmngingbService.delLegerStructures(dto); // delete
        }
        if(dto.getStParam().equals("Add")){
            dto.setCrgb("i");
            dto.setDeptCd(deptCd);
            bldmngingbService.legerStructuresAdd(dto);// insert
            bldmngingbService.hisLegerStructuresAdd(dto);// his
        }
        if(dto.getStParam().equals("Mody")){
            dto.setCrgb("u");
            dto.setUpdateId(updateId);
            bldmngingbService.legerStructuresUpdate(dto);// update
            bldmngingbService.hisLegerStructuresAdd(dto);// his
        }
        List<LedgerDTO.LegerStructures> legerStructures = bldmngingbService.legerStructures(dto); // select
        return legerStructures;
    }
    @PostMapping("/groundAction")
    public List<LedgerDTO.LegerGround> groundAction(@RequestBody LedgerDTO.LegerGround dto, @AuthenticationPrincipal UserAuthentication auth){
        String skkCd = auth.getSkkCd();
        String deptCd = auth.getDeptCd();
        String registId = auth.getUserId();
        String updateId = auth.getUserId();
        dto.setRegistId(registId);
        dto.setDeptCd(deptCd);
        dto.setSkkCd(skkCd);
        //dto.setCrgb("u");

        if(dto.getGroundParam().equals("Dell")){
            dto.setCrgb("d");
            bldmngingbService.hisLegerGroundAdd(dto);// his
            bldmngingbService.delLegerGround(dto); // delete
        }
        if(dto.getGroundParam().equals("Add")){
            dto.setCrgb("i");
            bldmngingbService.legerGroundAdd(dto);// insert
            bldmngingbService.hisLegerGroundAdd(dto);// his
        }
        if(dto.getGroundParam().equals("Mody")){
            dto.setCrgb("u");
            dto.setUpdateId(updateId);
            bldmngingbService.legerGroundUpdate(dto);// update
            bldmngingbService.hisLegerGroundAdd(dto);// his
        }
        List<LedgerDTO.LegerGround> legerGround = bldmngingbService.legerGround(dto); // select
        return legerGround;
    }
    @PostMapping("/permitAction")
    public List<LedgerDTO.LegerPermit> permitAction(@RequestBody LedgerDTO.LegerPermit dto, @AuthenticationPrincipal UserAuthentication auth){
        String skkCd = auth.getSkkCd();
        String deptCd = auth.getDeptCd();
        String registId = auth.getUserId();
        String updateId = auth.getUserId();
        dto.setRegistId(registId);
        dto.setDeptCd(deptCd);
        dto.setSkkCd(skkCd);
        //dto.setCrgb("u");

        if(dto.getPermitParam().equals("Dell")){
            dto.setCrgb("d");
            bldmngingbService.hisLegerPermitAdd(dto);// his
            bldmngingbService.delLegerPermit(dto); // delete
        }
        if(dto.getPermitParam().equals("Add")){
            dto.setCrgb("i");
            bldmngingbService.legerPermitAdd(dto);// insert
            bldmngingbService.hisLegerPermitAdd(dto);// his
        }
        if(dto.getPermitParam().equals("Mody")){
            dto.setCrgb("u");
            dto.setUpdateId(updateId);
            bldmngingbService.legerPermitUpdate(dto);// update
            bldmngingbService.hisLegerPermitAdd(dto);// his
        }
        List<LedgerDTO.LegerPermit> legerPermit = bldmngingbService.legerPermit(dto); // select
        return legerPermit;
    }

    //건축물현황
    @PostMapping("/ledgerLimitedUpdate")
    public ResponseEntity<Void> ledgerLimitedUpdate(@RequestBody LedgerDTO.LegerLimited dto, @AuthenticationPrincipal UserAuthentication auth){

        if (dto != null && dto.getLimitedParam() != null && dto.getLimitedParam().equals("Mody")) {
            // 대장 수정
            dto.setCrgb("u");
            dto.setRegistId(auth.getUserId());
            dto.setUpdateId(auth.getUserId());
            bldmngingbService.legerLimitedUpdate(dto);
            bldmngingbService.hisLegerLimitedAdd(dto);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //건축물현황 폐쇄처리
    @PostMapping("/deleteLimitedHead")
    public ResponseEntity<Void> deleteLimitedHead(@RequestBody LedgerDTO.LegerLimited dto, @AuthenticationPrincipal UserAuthentication auth){

        if (dto != null && dto.getLimitedParam() != null && dto.getLimitedParam().equals("Dell")) {
            // 대장 폐쇄
            dto.setCrgb("d");
            dto.setRegistId(auth.getUserId());
            dto.setUpdateId(auth.getUserId());
            bldmngingbService.deleteLimitedHead(dto);
            bldmngingbService.hisLegerLimitedAdd(dto);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
