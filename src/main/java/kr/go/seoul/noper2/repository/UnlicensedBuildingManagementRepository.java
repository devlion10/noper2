package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.dto.*;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface UnlicensedBuildingManagementRepository {
    List<LedgerDTO> findLedgrtList(LedgerDTO LedgerDTO);
    List<LedgerDTO> mngAdminstrBld(String gmskk);
    void legerAdd(LedgerDTO LedgerDTO);
    String findGesedqco();
    void hislegerAdd(LedgerDTO LedgerDTO);
    List<LedgerDTO> newFindLedgerList(LedgerDTO LedgerDTO);
    LedgerDTO newLedgerDtail(LedgerDTO LedgerDTO);
    LedgerDTO cmpBjdongMgm(String gmskk);
    void ledgerDell(LedgerDTO LedgerDTO);
    void historyMain(UserDTO UserDTO);
    void ledgerApproval(LedgerDTO LedgerDTO);

    List<NoperOwnerDTO> findNoperOwner(String gmskk, String gmdjgb, String gmseqco);
    List<NoperOwnerDTO> findNoperOwnerChange(String gmskk, String gmdjgb, String gmseqco);
    List<NoperFixDTO> findnoperFix(String gmskk, String gmdjgb, String gmseqco);
    List<NoperImgFileinfoDTO> fintNoperImgFileinfo(String gmskk, String gmdjgb, String gmseqco);

    // 대장 수정
    void updateNoperLeger(LedgerDTO LedgerDTO);
    // 건물주 일련번호채번
    String noperGmjilNo(String gmskk, String gmdjgb, String gmseqco);
    void noperHisNoperOwer(NoperOwnerDTO NoperOwnerDTO);
    void saveNoperOwner(NoperOwnerDTO NoperOwnerDTO);

    void hislegerupdate(LedgerDTO LedgerDTO);

    List<NoperSiteChkDTO> findNoperSiteChk(NoperSiteChkDTO dto);

    List<NoperSiteChkDTO> findNoperLedgerSiteChk(String gmskk, String gmdjgb, String gmseqco);

    void saveNoperOwnerChange(NoperOwnerDTO NoperOwnerDTO);
    void ledgerDellOwnerChangeDell(OwnerChangeDTO OwnerChangeDTO);
    // 소유주 일련번호 채번
    String noperSuilNo(String gmskk, String gmdjgb, String gmseqco, String gmjilno);
    // 현소유주 일련번호 채번
    String nowNoperSuilNo(String gmskk, String gmdjgb, String gmseqco);
    // 개보수 접수번호 채번
    String noperGmbalno(String gmskk, String gmdjgb, String gmseqco);

    // 개보수 일련번호 채번
    String noperGmilno(String gmskk, String gmdjgb, String gmseqco);

    void addNoperFix(NoperFixDTO NoperFixDTO);
    void noperHisNoperFix(NoperFixDTO NoperFixDTO);
    void delNoperFix(NoperFixDTO NoperFixDTO);
    void updateNoperFix(NoperFixDTO NoperFixDTO);
    String noperChkino(String gmskk, String gmdjgb, String gmseqco);
    String nowNoperChkino(String gmskk, String gmdjgb, String gmseqco);
    void addNoperSiteChk(NoperSiteChkDTO NoperSiteChkDTO);

    void updateNoperSiteChk(NoperSiteChkDTO NoperSiteChkDTO);

    void updateActionNoperSiteChk(NoperSiteChkDTO NoperSiteChkDTO);

    List<ConfirmationDTO> findConfirmation(ConfirmationDTO ConfirmationDTO);

    LedgerDTO confirmationDetail(LedgerDTO LedgerDTO);
    NoperConfirmDocDTO confirmationDetail2(NoperConfirmDocDTO NoperConfirmDocDTO);
    List<NoperOwnerDTO> confirmationDetail3(NoperOwnerDTO NoperOwnerDTO);

    List<RenovationDTO> findRenovationList(RenovationDTO dto);

    void confirmationAdd(ConfirmationDTO ConfirmationDTO);
    String confirmationNo(String gmskk);
    List<RenovationDTO> confirmationIssuerKey(String gmskk);

    void confirmationUpdate(ConfirmationDTO ConfirmationDTO);
    void confirmationHisAdd(ConfirmationDTO ConfirmationDTO);

    void confirmationDel(ConfirmationDTO ConfirmationDTO);

    ConfirmationDTO findConfirmationDOC(ConfirmationDTO dto);
    List<OwnerChangeDTO> findOwnerChange(OwnerChangeDTO OwnerChangeDTO);
    LedgerDTO findOwnerChangeGmj(String gmskk, String gmdjgb, String gmseqco);
    String ownerChangeGmilNo(String gmskk, String gmdjgb, String gmseqco);
    List<OwnerChangeDTO> ownerChangeFileList(String gmskk, String gmdjgb, String gmseqco, String gmjilno);
    List<OwnerChangeDTO> ownerChangeDetailList(String gmskk, String gmdjgb, String gmseqco);
    OwnerChangeDTO ownerChangeDetail(String gmskk, String gmdjgb, String gmseqco, String suilno, String gmjilno);
    void ownerChangeUpdate(OwnerChangeDTO OwnerChangeDTO);
    void ownerDell(OwnerChangeDTO OwnerChangeDTO);
    void ownerInsert(OwnerChangeDTO OwnerChangeDTO);
    String fileSeqNo();
    void saveOwnerChange(OwnerChangeDTO OwnerChangeDTO);
    void modyOwnerChange(OwnerChangeDTO OwnerChangeDTO);
    void hisOwnerChangeAdd(OwnerChangeDTO OwnerChangeDTO);

    String ownerCount(String gmskk, String gmdjgb, String gmseqco);
    LedgerDTO absorptionNoperMaster(String gmskk, String gmdjgb, String gmseqco);
    List<NoperOwnerDTO> absorptionNoperOwner(String gmskk, String gmdjgb, String gmseqco);


    List<RenovationDTO> findRenovationAddSearch(RenovationDTO dto);
    List<RenovationDTO> findRenovationIssuerKey(RenovationDTO dto);
    void saveRenovation(RenovationDTO dto);
    RenovationDTO findRenovationDetailSearch(RenovationDTO dto);
    RenovationDTO findRenovationDetail(RenovationDTO dto);
    void modifyRenovation(RenovationDTO dto);

    List<DemolitionDTO> findDemolitionList(DemolitionDTO dto);

    List<DemolitionDTO> findDemolitionIssuerKey(DemolitionDTO dto);

    List<DemolitionDTO> findDemolitionNoperInfo(DemolitionDTO dto);

    List<DemolitionDTO> findDemolitionCompensationList(DemolitionDTO dto);

    List<DemolitionDTO> findDemolitionTenantList(DemolitionDTO dto);

    int findDemolitionCountNoperOwner(DemolitionDTO dto);

    List<DemolitionDTO> findDemolitionOwnerChangeInfoByGmjilno(DemolitionDTO dto);

    List<DemolitionDTO> findDemolitionOwnerChangeList(DemolitionDTO dto);

    String findDemolitionNoperInfoGmhjdnm(DemolitionDTO dto);

    void modifyDemolitionDetailReport(DemolitionDTO dto);

    int saveDemolitionDetailCompensation(DemolitionDTO dto);

    int saveDemolitionDetailTenant(DemolitionDTO dto);

    int saveDemolitionRemoveMaster(DemolitionDTO dto);

    int deleteRemoveAllOwner(DemolitionDTO dto);

    int deleteRemoveAllRent(DemolitionDTO dto);

    int deleteRemoveMaster(DemolitionDTO dto);

    int updateNoperMasterN(DemolitionDTO dto);

    List<DemolitionDTO> findPartDemolitionList(DemolitionDTO dto);

    List<DemolitionDTO> findPartDemolitionNoperInfo(DemolitionDTO dto);

    List<DemolitionDTO> findPartDemolitionCompensationList(DemolitionDTO dto);

    List<DemolitionDTO> findPartDemolitionTenantList(DemolitionDTO dto);

    int savePartDemolitionDetailCompensation(DemolitionDTO dto);

    int savePartDemolitionDetailTenant(DemolitionDTO dto);

    int deletePartDemolitionDetailCompensation(DemolitionDTO dto);

    int deletePartDemolitionDetailTenant(DemolitionDTO dto);

    int deleteDemolitionDetailCompensation(DemolitionDTO dto);

    int deleteDemolitionDetailTenant(DemolitionDTO dto);

    List<DemolitionDTO> findPartDemolitionAddNoperInfo(DemolitionDTO dto);

    void insertHisRemoveMasterI1(DemolitionDTO dto);

    void insertHisRemoveOwnerI2(DemolitionDTO dto);

    void insertHisRemoveRentI3(DemolitionDTO dto);

    void insertHisRemoveMasterU1(DemolitionDTO dto);

    void insertHisRemoveMaster(DemolitionDTO dto);

    int updateNoperMaster(DemolitionDTO dto);

    int findRemoveOwnerRecom(DemolitionDTO dto);

    void updateRemoveOwnerRecom(DemolitionDTO dto);

    void insertHisRemoveOwner(DemolitionDTO dto);

    void insertHisRemoveRent(DemolitionDTO dto);

    void updateNoperMasterC(DemolitionDTO dto);

    void insertHisRemoveOwnerI2Part(DemolitionDTO dto);

    void insertHisRemoveRentI3Part(DemolitionDTO dto);

    void insertHisRemoveOwnerD2Part(DemolitionDTO dto);

    void insertHisRemoveRentD3Part(DemolitionDTO dto);

    void insertHisNoperFixI1(RenovationDTO dto);

    void insertHisNoperFixU1(RenovationDTO dto);

    List<Object> findDemolitionExcelList(DemolitionExcelDTO dto);

    List<Object> findPartDemolitionExcelList(PartDemolitionExcelDTO dto);

    List<Object> findNoperSiteChkExcel(NoperSiteChkExcelDTO dto);

    List<Object> findRenovationListExcel(RenovationDTO dto);

    List<NoperStatusDTO> noperStatus(SearchParamDTO param);
    List<NoperStatusByPeriodDTO> noperStatusByPeriod(SearchParamDTO param);
    List<DemolitionDTO> findDemolitionAddNoperInfo(DemolitionDTO dto);

    RenovationDTO findRenovationAddDetail(RenovationDTO dto);


    void modyOwner(OwnerChangeDTO dto);

    List<OwnerChangeDTO> OwnerInfoDetailList(String gmskk, String gmdjgb, String gmseqco);
}
