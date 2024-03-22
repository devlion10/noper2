package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.repository.CodeRepository;
import kr.go.seoul.noper2.repository.UnlicensedBuildingManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UnlicensedBuildingManagementService {
    private final UnlicensedBuildingManagementRepository UnlicensedBuildingManagementRepository;
    private final CodeRepository CodeRepository;

    //@Transactional 저장 업데이트

    //대장리스트



    public List<LedgerDTO> findLedgrtList(LedgerDTO dto) { return UnlicensedBuildingManagementRepository.findLedgrtList(dto);}

    public List<LedgerDTO> findLedgerDataExcel(LedgerDTO dto) {
        List<LedgerDTO> list;
        if(dto.getLegerFlag().equals("newLedger")){
            list = UnlicensedBuildingManagementRepository.newFindLedgerList(dto);
        }else{
            list = UnlicensedBuildingManagementRepository.findLedgrtList(dto);
        }
        return list;
    }
    public List<ConfirmationDTO> findConfirmationDTODataExcel(ConfirmationDTO dto) {
        List<ConfirmationDTO> list = UnlicensedBuildingManagementRepository.findConfirmation(dto);
        return list;
    }

    public List<OwnerChangeDTO> findOwnerChangeDTODataExcel(OwnerChangeDTO dto) {
        List<OwnerChangeDTO> list = UnlicensedBuildingManagementRepository.findOwnerChange(dto);
        return list;
    }

    public List<LedgerDTO> mngAdminstrBld(String gmskk) {return UnlicensedBuildingManagementRepository.mngAdminstrBld(gmskk);}

    @Transactional
    public void legerAdd(LedgerDTO LedgerDTO) {UnlicensedBuildingManagementRepository.legerAdd(LedgerDTO);}
    public String findGesedqco(){return UnlicensedBuildingManagementRepository.findGesedqco();}
    @Transactional
    public void hislegerAdd(LedgerDTO dto) {UnlicensedBuildingManagementRepository.hislegerAdd(dto);}

    public List<LedgerDTO> newFindLedgerList(LedgerDTO dto) { return UnlicensedBuildingManagementRepository.newFindLedgerList(dto);}

    public LedgerDTO newLedgerDtail(LedgerDTO dto) { return UnlicensedBuildingManagementRepository.newLedgerDtail(dto);}

    public void ledgerDell(LedgerDTO dto) {
        UnlicensedBuildingManagementRepository.ledgerDell(dto);
        UnlicensedBuildingManagementRepository.historyMain(dto);
        dto.setNewyn("Y");
        UnlicensedBuildingManagementRepository.hislegerAdd(dto);
    }
    public void historyMain(LedgerDTO dto) {UnlicensedBuildingManagementRepository.historyMain(dto);}
    @Transactional
    public void ledgerApproval(LedgerDTO dto, @AuthenticationPrincipal UserAuthentication auth) {
        dto.setNewyn("Y");
        dto.setUserId(auth.getUserId());
        UnlicensedBuildingManagementRepository.ledgerApproval(dto);
        UserDTO user = new UserDTO();
        user.setRegistId(auth.getRegistId());
        user.setUserId(auth.getUserId());
        user.setUserName(auth.getUserName());
        user.setDeptCd(auth.getDeptCd());
        user.setWorkid("KGZ1000P");
        user.setCrgb("u");
        user.setUserName(auth.getUserName());
        user.setNewyn("UPDATEC");
        user.setGmskk(dto.getGmskk());
        user.setGmdjgb(dto.getGmdjgb());
        user.setGmseqco(dto.getGmseqco());
        user.setNewyn("Y");
        UnlicensedBuildingManagementRepository.historyMain(user);
        UnlicensedBuildingManagementRepository.hislegerAdd(dto);
    }

    public List<NoperImgFileinfoDTO> fintNoperImgFileinfo(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.fintNoperImgFileinfo(gmskk, gmdjgb, gmseqco);}

    public List<NoperFixDTO> findnoperFix(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.findnoperFix(gmskk, gmdjgb, gmseqco);}

    public List<NoperOwnerDTO> findNoperOwnerChange(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.findNoperOwnerChange(gmskk, gmdjgb, gmseqco);}

    public List<NoperOwnerDTO> findNoperOwner(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.findNoperOwner(gmskk, gmdjgb, gmseqco);}

    public List<NoperSiteChkDTO> findNoperSiteChk(NoperSiteChkDTO dto) {
        return UnlicensedBuildingManagementRepository.findNoperSiteChk(dto);};
    @Transactional
    public void updateNoperLeger(LedgerDTO dto, @AuthenticationPrincipal UserAuthentication auth) {
        dto.setUserId(auth.getUserId());
        UnlicensedBuildingManagementRepository.updateNoperLeger(dto);
        UserDTO user = new UserDTO();
        user.setUserId(auth.getUserId());
        user.setDeptCd(auth.getDeptCd());
        user.setWorkid("KGZ1000P");
        user.setCrgb("u");
        user.setUserName(auth.getUserName());
        user.setNewyn("UPDATEC");
        user.setGmskk(dto.getGmskk());
        user.setGmdjgb(dto.getGmdjgb());
        user.setGmseqco(dto.getGmseqco());
        UnlicensedBuildingManagementRepository.historyMain(user);
        UnlicensedBuildingManagementRepository.hislegerupdate(dto);

    }
    public String noperGmjilNo(String gmskk, String gmdjgb, String gmseqco){return UnlicensedBuildingManagementRepository.noperGmjilNo(gmskk, gmdjgb, gmseqco);}
    @Transactional
    public void noperHisNoperOwer(NoperOwnerDTO dto) {
        UnlicensedBuildingManagementRepository.noperHisNoperOwer(dto);
    }
    @Transactional
    public void saveNoperOwner(NoperOwnerDTO dto) {
        UnlicensedBuildingManagementRepository.saveNoperOwner(dto);
        UnlicensedBuildingManagementRepository.saveNoperOwnerChange(dto);
    }

    public List<NoperSiteChkDTO> findNoperLedgerSiteChk(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.findNoperLedgerSiteChk(gmskk, gmdjgb, gmseqco);}

    public String noperSuilNo(String gmskk, String gmdjgb, String gmseqco, String gmjilno){
        return UnlicensedBuildingManagementRepository.noperSuilNo(gmskk, gmdjgb, gmseqco, gmjilno);
    }
    public String noperGmbalno(String gmskk, String gmdjgb, String gmseqco){
        return UnlicensedBuildingManagementRepository.noperGmbalno(gmskk, gmdjgb, gmseqco);
    }
    public String noperGmilno(String gmskk, String gmdjgb, String gmseqco){
        return UnlicensedBuildingManagementRepository.noperGmilno(gmskk, gmdjgb, gmseqco);
    }

    @Transactional
    public void addNoperFix(NoperFixDTO dto) {
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.addNoperFix(dto);
    }
    @Transactional
    public void  noperHisNoperFix(NoperFixDTO dto) {
        UnlicensedBuildingManagementRepository.noperHisNoperFix(dto);
    }
    public void  delNoperFix(NoperFixDTO dto) {
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.delNoperFix(dto);
    }
    public void  updateNoperFix(NoperFixDTO dto) {
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.updateNoperFix(dto);
    }
    public String noperChkino(String gmskk, String gmdjgb, String gmseqco){return UnlicensedBuildingManagementRepository.noperChkino(gmskk, gmdjgb, gmseqco);}
    public String nowNoperChkino(String gmskk, String gmdjgb, String gmseqco){return UnlicensedBuildingManagementRepository.nowNoperChkino(gmskk, gmdjgb, gmseqco);}

    public void addNoperSiteChk(NoperSiteChkDTO dto){
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.addNoperSiteChk(dto);
    }
    public LedgerDTO cmpBjdongMgm(String gmskk){return UnlicensedBuildingManagementRepository.cmpBjdongMgm(gmskk);}

    public void updateNoperSiteChk(NoperSiteChkDTO dto){
        UnlicensedBuildingManagementRepository.updateNoperSiteChk(dto);
    }
    public void updateActionNoperSiteChk(NoperSiteChkDTO dto){
        UnlicensedBuildingManagementRepository.updateActionNoperSiteChk(dto);
    }


    public List<ConfirmationDTO> findConfirmation(ConfirmationDTO dto) {
        return UnlicensedBuildingManagementRepository.findConfirmation(dto);
    }
    // 무허가 확인원관리
    public LedgerDTO confirmationDetail(LedgerDTO dto) {
        return UnlicensedBuildingManagementRepository.confirmationDetail(dto);
    }
    public NoperConfirmDocDTO confirmationDetail2(NoperConfirmDocDTO dto) {
        return UnlicensedBuildingManagementRepository.confirmationDetail2(dto);
    }
    public List<NoperOwnerDTO> confirmationDetail3(NoperOwnerDTO dto) {
        return UnlicensedBuildingManagementRepository.confirmationDetail3(dto);
    }


    @Transactional
    public List<ConfirmationDTO> confirmationAdd(ConfirmationDTO dto, @AuthenticationPrincipal UserAuthentication auth) {
        int bgsu = Integer.parseInt(dto.getBgsu());
        List<ConfirmationDTO> confirmationDTOList = new ArrayList<>();
        List<String> conilnoList = new ArrayList<>();
        // 사용자 정보 기입과 HIS 테이블에 넣기위한 값 기입
        dto.setCrgb("i");
        dto.setUserId(auth.getUserId());
        dto.setDecision(auth.getDeptCd());
        dto.setDeptCd(auth.getDeptCd());
        dto.setWorkid("KGZ1400P");
        dto.setUserName(auth.getUserName());
        // 반복해서 쿼리 수행
        for (int i = 0; i < bgsu; i++) {
            String conilno = confirmationNo(auth.getSkkCd());
            dto.setConilno(conilno);
            UnlicensedBuildingManagementRepository.confirmationAdd(dto);
            UnlicensedBuildingManagementRepository.historyMain(dto);
            // this.confirmationHisAdd() 와 같은 기능
            UnlicensedBuildingManagementRepository.confirmationHisAdd(dto);
            conilnoList.add(conilno);
        }
        dto.setConilnoList(conilnoList);
        confirmationDTOList.add(dto);
        return confirmationDTOList;
    }

    public String confirmationNo(String gmskk){return UnlicensedBuildingManagementRepository.confirmationNo(gmskk);}
    public List<RenovationDTO> confirmationIssuerKey(String gmskk) { return UnlicensedBuildingManagementRepository.confirmationIssuerKey(gmskk);}

    @Transactional
    public void  confirmationUpdate(ConfirmationDTO dto) {
        UnlicensedBuildingManagementRepository.confirmationUpdate(dto);
        UnlicensedBuildingManagementRepository.historyMain(dto);    }
    @Transactional
    public void  confirmationHisAdd(ConfirmationDTO dto) {
        UnlicensedBuildingManagementRepository.confirmationHisAdd(dto);
    }

    public ConfirmationDTO getConfirmationDOC(ConfirmationDTO dto) {
        return UnlicensedBuildingManagementRepository.findConfirmationDOC(dto);
    }

    public void confirmationDel(ConfirmationDTO dto) {
        UnlicensedBuildingManagementRepository.confirmationDel(dto);
    }

    public List<OwnerChangeDTO> findOwnerChange(OwnerChangeDTO dto) {
        return UnlicensedBuildingManagementRepository.findOwnerChange(dto);
    }
    public LedgerDTO findOwnerChangeGmj(String gmskk, String gmdjgb, String gmseqco){
        return UnlicensedBuildingManagementRepository.findOwnerChangeGmj(gmskk, gmdjgb, gmseqco);
    }
    public String OwnerChangeGmilNo(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.ownerChangeGmilNo(gmskk, gmdjgb, gmseqco);
    }
    public List<OwnerChangeDTO> OwnerChangeDetailList(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.ownerChangeDetailList(gmskk, gmdjgb, gmseqco);
    }
    public OwnerChangeDTO OwnerChangeDetail(String gmskk, String gmdjgb, String gmseqco, String suilno, String gmjilno) {
        OwnerChangeDTO ownerChangeDTO = UnlicensedBuildingManagementRepository.ownerChangeDetail(gmskk, gmdjgb, gmseqco, suilno, gmjilno);
        return ownerChangeDTO;
    }
    public List<OwnerChangeDTO> OwnerChangeFileList(String gmskk, String gmdjgb, String gmseqco, String gmjilno) {
        return UnlicensedBuildingManagementRepository.ownerChangeFileList(gmskk, gmdjgb, gmseqco, gmjilno);
    }
    @Transactional
    public void OwnerChangeUpdte(OwnerChangeDTO dto){
        String suilno =  UnlicensedBuildingManagementRepository.noperSuilNo(dto.getGmskk(), dto.getGmdjgb(), dto.getGmseqco(), dto.getGmjilno());
        dto.setSuilno(suilno);
        if(dto.getChangDivision().equals("absorption")){
            UnlicensedBuildingManagementRepository.ownerDell(dto);
            UnlicensedBuildingManagementRepository.ownerInsert(dto);
        }else{
            UnlicensedBuildingManagementRepository.ownerChangeUpdate(dto);
        }
        String fileSeqNo = UnlicensedBuildingManagementRepository.fileSeqNo();
        dto.setFileSeq(fileSeqNo);
        dto.setIsLocal("false");
        if(dto.getJnewAddr1() == null){dto.setJnewAddr1("");}
        if(dto.getJnewAddr2() == null){dto.setJnewAddr2("");}
        if(dto.getJgmjjumin() == null){dto.setJgmjjumin("");}
        if(dto.getJgmjsna() == null){dto.setJgmjsna("");}
        UnlicensedBuildingManagementRepository.saveOwnerChange(dto);
        UnlicensedBuildingManagementRepository.hisOwnerChangeAdd(dto);
    }
    @Transactional
    public void OwnerChangeUpdts(OwnerChangeDTO dto){
        dto.setIsLocal("false");
        dto.setUpdateId(dto.getUserId());
        UnlicensedBuildingManagementRepository.modyOwnerChange(dto);
        UnlicensedBuildingManagementRepository.modyOwner(dto);
    }
    @Transactional
    public void hisOwnerChangeAdd(OwnerChangeDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        dto.setRegistId(auth.getUserId());
        UnlicensedBuildingManagementRepository.hisOwnerChangeAdd(dto);
    }

    public void OwnerChangeDell(OwnerChangeDTO dto) {
        UnlicensedBuildingManagementRepository.ledgerDellOwnerChangeDell(dto);
    }

    @Transactional
    public void historyMain(OwnerChangeDTO dto, @AuthenticationPrincipal UserAuthentication auth){
        String deptCd = auth.getDeptCd();
        String workid = "KGZ1000P";
        String crgb = dto.getCrgb();
        String userId = auth.getUserId();
        String userName = auth.getUserName();
        dto.setRegistId(userId);
        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setDeptCd(deptCd);
        user.setWorkid(workid);
        user.setCrgb(crgb);
        user.setUserName(userName);
        user.setNewyn("UPDATEC");
        user.setGmskk(dto.getGmskk());
        user.setGmdjgb(dto.getGmdjgb());
        user.setGmseqco(dto.getGmseqco());
        UnlicensedBuildingManagementRepository.historyMain(dto);
    }

    public String ownerCount(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.ownerCount(gmskk, gmdjgb, gmseqco);
    }
    public LedgerDTO absorptionNoperMaster(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.absorptionNoperMaster(gmskk, gmdjgb, gmseqco);
    }
    public List<NoperOwnerDTO> absorptionNoperOwner(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.absorptionNoperOwner(gmskk, gmdjgb, gmseqco);
    }


    public List<RenovationDTO> findRenovationList(RenovationDTO dto) {
        return UnlicensedBuildingManagementRepository.findRenovationList(dto);
    }
    public List<RenovationDTO> findRenovationAddSearch(RenovationDTO dto) {
        return UnlicensedBuildingManagementRepository.findRenovationAddSearch(dto);
    }
    public List<RenovationDTO> findRenovationIssuerKey(RenovationDTO dto) {
        return UnlicensedBuildingManagementRepository.findRenovationIssuerKey(dto);
    }

    @Transactional
    public RenovationDTO saveRenovation(RenovationDTO dto) {
        // 개보수신고 - 신고사항 등록
        UnlicensedBuildingManagementRepository.saveRenovation(dto);

        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisNoperFixI1(dto);
        return UnlicensedBuildingManagementRepository.findRenovationAddDetail(dto);
    }

    public RenovationDTO findRenovationDetailSearch(RenovationDTO dto) {
        return UnlicensedBuildingManagementRepository.findRenovationDetailSearch(dto);
    }

    public RenovationDTO findRenovationDetail(RenovationDTO dto) {
        return UnlicensedBuildingManagementRepository.findRenovationDetail(dto);
    }
    @Transactional
    public void modifyRenovation(RenovationDTO dto) {
        // 개보수신고 - 신고사항 수정
        UnlicensedBuildingManagementRepository.modifyRenovation(dto);

        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisNoperFixU1(dto);
    }

    public List<DemolitionDTO> findDemolitionList(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionList(dto);
    }

    public List<DemolitionDTO> findDemolitionIssuerKey(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionIssuerKey(dto);
    }

    public List<DemolitionDTO> findDemolitionNoperInfo(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionNoperInfo(dto);
    }

    public String findDemolitionNoperInfoGmhjdnm(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionNoperInfoGmhjdnm(dto);
    }

    public List<DemolitionDTO> findDemolitionCompensationList(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionCompensationList(dto);
    }

    public List<DemolitionDTO> findDemolitionTenantList(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionTenantList(dto);
    }

    public int findDemolitionCountNoperOwner(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionCountNoperOwner(dto);
    }

    public List<DemolitionDTO> findDemolitionOwnerChangeInfoByGmjilno(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionOwnerChangeInfoByGmjilno(dto);
    }

    public List<DemolitionDTO> findDemolitionOwnerChangeList(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionOwnerChangeList(dto);
    }

    @Transactional
    public void modifyDemolitionDetailReport(DemolitionDTO dto) {
        // 철거신고 신고사항 변경
        UnlicensedBuildingManagementRepository.modifyDemolitionDetailReport(dto);

        // UnlicensedBuildingManagementRepository.historyMain(dto, sesseion);
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveMasterU1(dto);
    }

    @Transactional
    public int saveDemolitionDetailCompensation(DemolitionDTO dto) {
        // 철거신고 건물주 보상 현황 추가
        int result = UnlicensedBuildingManagementRepository.saveDemolitionDetailCompensation(dto);

        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveOwnerI2(dto);
        return result;
    }

    @Transactional
    public int saveDemolitionDetailTenant(DemolitionDTO dto) {
        // 철거신고 세입자 현황 추가
        int result = UnlicensedBuildingManagementRepository.saveDemolitionDetailTenant(dto);

        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveRentI3(dto);
        return result;
    }

    @Transactional
    public int saveDemolitionRemoveMaster(DemolitionDTO dto) {
        int cnt = UnlicensedBuildingManagementRepository.saveDemolitionRemoveMaster(dto);

        if (cnt >= 1){
            int result = UnlicensedBuildingManagementRepository.updateNoperMaster(dto);

            int cnt2 = UnlicensedBuildingManagementRepository.findRemoveOwnerRecom(dto);
            if(cnt2 >= 1){
                UnlicensedBuildingManagementRepository.updateRemoveOwnerRecom(dto);
            }
        }

        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveMasterI1(dto);
        return cnt;
    }

    @Transactional
    public int deleteDemolitionDetailCompensation(DemolitionDTO dto) {
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveOwner(dto);
        return UnlicensedBuildingManagementRepository.deleteDemolitionDetailCompensation(dto);
    }

    @Transactional
    public int deleteDemolitionDetailTenant(DemolitionDTO dto) {
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveRent(dto);

        return UnlicensedBuildingManagementRepository.deleteDemolitionDetailTenant(dto);
    }

    public List<DemolitionDTO> findPartDemolitionList(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findPartDemolitionList(dto);
    }

    public List<DemolitionDTO> findPartDemolitionNoperInfo(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findPartDemolitionNoperInfo(dto);
    }

    public List<DemolitionDTO> findPartDemolitionCompensationList(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findPartDemolitionCompensationList(dto);
    }

    public List<DemolitionDTO> findPartDemolitionTenantList(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findPartDemolitionTenantList(dto);
    }

    @Transactional
    public int savePartDemolitionDetailCompensation(DemolitionDTO dto) {
        // 부분철거신고 건물주 보상 현황 추가
        int result = UnlicensedBuildingManagementRepository.savePartDemolitionDetailCompensation(dto);
//        if (result > 0){
            UnlicensedBuildingManagementRepository.updateNoperMasterC(dto);
//        }
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveOwnerI2Part(dto);
        return result;
    }

    @Transactional
    public int savePartDemolitionDetailTenant(DemolitionDTO dto) {
        // 부분철거신고 세입자 현황 추가
        int result = UnlicensedBuildingManagementRepository.savePartDemolitionDetailTenant(dto);

        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveRentI3Part(dto);
        return result;
    }

    @Transactional
    public int deletePartDemolitionDetailCompensation(DemolitionDTO dto) {
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveOwnerD2Part(dto);

        // 부분철거신고 건물주 보상 현황 삭제
        return UnlicensedBuildingManagementRepository.deletePartDemolitionDetailCompensation(dto);
    }

    @Transactional
    public int deletePartDemolitionDetailTenant(DemolitionDTO dto) {
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveRentD3Part(dto);

        // 부분철거신고 세입자 현황 삭제
        return UnlicensedBuildingManagementRepository.deletePartDemolitionDetailTenant(dto);
    }

    @Transactional
    public void demolitionDelete(DemolitionDTO dto) {
//        UnlicensedBuildingManagementRepository.historyMain(dto, sesseion);
        UnlicensedBuildingManagementRepository.historyMain(dto);
        UnlicensedBuildingManagementRepository.insertHisRemoveMaster(dto);

        int gmjilno = UnlicensedBuildingManagementRepository.deleteRemoveAllOwner(dto);       // 건물주 보상 현황 삭제
        int seipilno = UnlicensedBuildingManagementRepository.deleteRemoveAllRent(dto);       // 세입자 현황 삭제
        int removeMaster = UnlicensedBuildingManagementRepository.deleteRemoveMaster(dto);    // 철거대장 현황 삭제
        int noperMaster = UnlicensedBuildingManagementRepository.updateNoperMasterN(dto);     // 무허가 대장 수정
    }

    public List<DemolitionDTO> findPartDemolitionAddNoperInfo(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findPartDemolitionAddNoperInfo(dto);
    }

    public List<Object> findDemolitionExcelList(DemolitionExcelDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionExcelList(dto);
    }

    public List<Object> findPartDemolitionExcelList(PartDemolitionExcelDTO dto) {
        return UnlicensedBuildingManagementRepository.findPartDemolitionExcelList(dto);
    }

    public List<Object> findNoperSiteChkExcel(NoperSiteChkExcelDTO dto) {
        return UnlicensedBuildingManagementRepository.findNoperSiteChkExcel(dto);
    }

    public List<Object> findRenovationListExcel(RenovationDTO dto) {
        return UnlicensedBuildingManagementRepository.findRenovationListExcel(dto);
    }

    public List<NoperStatusDTO> noperStatus(SearchParamDTO param) {
        param.setSkkCd(param.getSkkCd() == null ? "" : param.getSkkCd());
        param.setBjdCd(param.getBjdCd() == null ? "" : param.getBjdCd());
        return UnlicensedBuildingManagementRepository.noperStatus(param);
    }

    public List<NoperStatusByPeriodDTO> noperStatusByPeriod(SearchParamDTO param) {
        param.setSkkCd(param.getSkkCd() == null ? "" : param.getSkkCd());
        param.setUseType(param.getUseType() == null ? "" : param.getUseType());
        param.setStartDate(param.getStartDate() == null || param.getStartDate().isEmpty() ? "2000-01-01" : param.getStartDate());
        param.setEndDate(param.getEndDate() == null || param.getEndDate().isEmpty() ? "2099-12-31" : param.getEndDate());
        return UnlicensedBuildingManagementRepository.noperStatusByPeriod(param);
    }

    public List<DemolitionDTO> findDemolitionAddNoperInfo(DemolitionDTO dto) {
        return UnlicensedBuildingManagementRepository.findDemolitionAddNoperInfo(dto);
    }


    public List<OwnerChangeDTO> OwnerInfoDetailList(String gmskk, String gmdjgb, String gmseqco) {
        return UnlicensedBuildingManagementRepository.OwnerInfoDetailList(gmskk, gmdjgb, gmseqco);
    }
}
