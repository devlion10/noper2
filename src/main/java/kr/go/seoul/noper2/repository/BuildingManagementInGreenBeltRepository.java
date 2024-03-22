package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.dto.GreenBeltLedgerExcelDTO;
import kr.go.seoul.noper2.dto.HistoryMainDTO;
import kr.go.seoul.noper2.dto.LedgerDTO;
import kr.go.seoul.noper2.dto.NoperSiteChkDTO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface BuildingManagementInGreenBeltRepository {

    /* 일일처리내역 */
    List<HistoryMain> findHistoryList(HistoryMainDTO dto);

    /* 대장관리 */
    List<LedgerDTO> findLedgerList(LedgerDTO dto);

    //관리행정동
    List<LedgerDTO> mngAdminstrBld(String gmskk);

    //void legerAdd(LedgerDTO ledgerDTO);

    //void hisLegerAdd(LedgerDTO dto);

    //신규대장등록
    void legerLimitedAdd(LedgerDTO.LegerLimited legerLimited);

    void legerOwnerAdd(LedgerDTO.LegerOwner legerOwner);

    void legerDongAdd(LedgerDTO.LegerDong legerDong);

    void legerStructuresAdd(LedgerDTO.LegerStructures legerStructures);

    void legerGroundAdd(LedgerDTO.LegerGround legerGround);

    void legerPermitAdd(LedgerDTO.LegerPermit legerPermit);

    //히스토리등록
    void hisLegerLimitedAdd(LedgerDTO.LegerLimited legerLimited);

    void hisLegerOwnerAdd(LedgerDTO.LegerOwner legerOwner);

    void hisLegerDongAdd(LedgerDTO.LegerDong legerDong);

    void hisLegerStructuresAdd(LedgerDTO.LegerStructures legerStructures);

    void hisLegerGroundAdd(LedgerDTO.LegerGround legerGround);

    void hisLegerPermitAdd(LedgerDTO.LegerPermit legerPermit);

    String findAdmSeq(String skkCd);

    //상세
    LedgerDTO.LegerLimited legerLimited(LedgerDTO dto);

    List<LedgerDTO.LegerOwner> legerOwner(LedgerDTO.LegerOwner dto);

    List<LedgerDTO.LegerDong> legerDong(LedgerDTO.LegerDong dto);

    List<LedgerDTO.LegerStructures> legerStructures(LedgerDTO.LegerStructures dto);

    List<LedgerDTO.LegerGround> legerGround(LedgerDTO.LegerGround dto);

    List<LedgerDTO.LegerPermit> legerPermit(LedgerDTO.LegerPermit dto);


    void delLegerOwner(LedgerDTO.LegerOwner dto);

    void legerOwnerUpdate(LedgerDTO.LegerOwner dto);
    
    void legerDongUpdate(LedgerDTO.LegerDong dto);

    void delLegerDong(LedgerDTO.LegerDong dto);

    void legerStructuresUpdate(LedgerDTO.LegerStructures dto);

    void delLegerStructures(LedgerDTO.LegerStructures dto);

    void legerGroundUpdate(LedgerDTO.LegerGround dto);

    void delLegerGround(LedgerDTO.LegerGround dto);

    void delLegerPermit(LedgerDTO.LegerPermit dto);

    void legerPermitUpdate(LedgerDTO.LegerPermit dto);

    void legerLimitedUpdate(LedgerDTO.LegerLimited dto);

    void deleteLimitedHead(LedgerDTO.LegerLimited dto);

    List<GreenBeltLedgerExcelDTO> findLedgerExcelList(GreenBeltLedgerExcelDTO dto);

    List<HistoryMainDTO> findDailyHistoryDataExcel(HistoryMainDTO dto);
}
