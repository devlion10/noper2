package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.dto.GreenBeltLedgerExcelDTO;
import kr.go.seoul.noper2.dto.HistoryMainDTO;
import kr.go.seoul.noper2.dto.LedgerDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.repository.BuildingManagementInGreenBeltRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BuildingManagementInGreenBeltService {
    private final BuildingManagementInGreenBeltRepository repository;

    /* 일일처리내역 */
    public List<HistoryMain> findHistoryList(HistoryMainDTO dto) {
        return repository.findHistoryList(dto);
    }

    /* 대장관리 */
    public List<LedgerDTO> findLedgerList(LedgerDTO dto) {
        return repository.findLedgerList(dto);
    }

    //관리행정동
    public List<LedgerDTO> mngAdminstrBld(String gmskk) { return repository.mngAdminstrBld(gmskk); }

    @Transactional
    public void legerAdd(LedgerDTO LedgerDTO, UserAuthentication auth) {
        String admSeq = repository.findAdmSeq(auth.getSkkCd()); // 기개발 대장번호 채번
        LedgerDTO.getLegerLimited().setAdmSeq(admSeq);
        /*LedgerDTO.getLegerOwner().setAdmSeq(admSeq);
        LedgerDTO.getLegerDong().setAdmSeq(admSeq);
        LedgerDTO.getLegerStructures().setAdmSeq(admSeq);
        LedgerDTO.getLegerGround().setAdmSeq(admSeq);
        LedgerDTO.getLegerPermit().setAdmSeq(admSeq);
        System.out.println("add 기개발 대장번호 채번 : "+LedgerDTO.getLegerOwner().getSkkCd());*/

        //registId, deptCd 컨트롤러말고 서비스에서넣기
        String registId = auth.getUserId();
        LedgerDTO.getLegerLimited().setRegistId(registId);
        /*LedgerDTO.getLegerOwner().setRegistId(registId);
        LedgerDTO.getLegerDong().setRegistId(registId);
        LedgerDTO.getLegerStructures().setRegistId(registId);
        LedgerDTO.getLegerGround().setRegistId(registId);
        LedgerDTO.getLegerPermit().setRegistId(registId);
        System.out.println("add 기개발 RegistId : "+LedgerDTO.getLegerPermit().getRegistId());*/

        String deptCd = auth.getDeptCd();
        LedgerDTO.getLegerLimited().setDeptCd(deptCd);
        /*LedgerDTO.getLegerOwner().setDeptCd(deptCd);
        LedgerDTO.getLegerDong().setDeptCd(deptCd);
        LedgerDTO.getLegerStructures().setDeptCd(deptCd);
        LedgerDTO.getLegerGround().setDeptCd(deptCd);
        LedgerDTO.getLegerPermit().setDeptCd(deptCd);
        System.out.println("add 기개발 deptCd : "+LedgerDTO.getLegerPermit().getRegistId());*/

        LedgerDTO.getLegerLimited().setCrgb("i");
        /*LedgerDTO.getLegerOwner().setCrgb("i");
        LedgerDTO.getLegerDong().setCrgb("i");
        LedgerDTO.getLegerStructures().setCrgb("i");
        LedgerDTO.getLegerGround().setCrgb("i");
        LedgerDTO.getLegerPermit().setCrgb("i");*/

        repository.legerLimitedAdd(LedgerDTO.getLegerLimited());
        repository.hisLegerLimitedAdd(LedgerDTO.getLegerLimited());
        /*repository.legerOwnerAdd(LedgerDTO.getLegerOwner());
        repository.legerDongAdd(LedgerDTO.getLegerDong());
        repository.legerStructuresAdd(LedgerDTO.getLegerStructures());
        repository.legerGroundAdd(LedgerDTO.getLegerGround());
        repository.legerPermitAdd(LedgerDTO.getLegerPermit());*/
    }

    /* 미사용 코드 주석처리 - 24.03.08
    @Transactional
    public void hisLegerAdd(LedgerDTO LedgerDTO, UserAuthentication auth) {

        repository.hisLegerLimitedAdd(LedgerDTO.getLegerLimited());
        repository.hisLegerOwnerAdd(LedgerDTO.getLegerOwner());
        repository.hisLegerDongAdd(LedgerDTO.getLegerDong());
        repository.hisLegerStructuresAdd(LedgerDTO.getLegerStructures());
        repository.hisLegerGroundAdd(LedgerDTO.getLegerGround());
        repository.hisLegerPermitAdd(LedgerDTO.getLegerPermit());
    }
    */

    public String findAdmSeq(String skkCd) {
        return repository.findAdmSeq(skkCd);
    }

    //상세
    public LedgerDTO.LegerLimited legerLimited(LedgerDTO dto) { return repository.legerLimited(dto); }

    public List<LedgerDTO.LegerOwner> legerOwner(LedgerDTO.LegerOwner dto) { return repository.legerOwner(dto); }

    public List<LedgerDTO.LegerDong> legerDong(LedgerDTO.LegerDong dto) { return repository.legerDong(dto); }

    public List<LedgerDTO.LegerStructures> legerStructures(LedgerDTO.LegerStructures dto) { return repository.legerStructures(dto); }

    public List<LedgerDTO.LegerGround> legerGround(LedgerDTO.LegerGround dto) { return repository.legerGround(dto); }

    public List<LedgerDTO.LegerPermit> legerPermit(LedgerDTO.LegerPermit dto) { return repository.legerPermit(dto); }


    //상세 Grid 수정/삭제/조회
    public void delLegerOwner(LedgerDTO.LegerOwner dto) {
        repository.delLegerOwner(dto);
    }

    public void legerOwnerAdd(LedgerDTO.LegerOwner dto) {
        repository.legerOwnerAdd(dto);
    }

    public void hisLegerOwnerAdd(LedgerDTO.LegerOwner dto) {
        repository.hisLegerOwnerAdd(dto);
    }

    public void legerOwnerUpdate(LedgerDTO.LegerOwner dto) {
        repository.legerOwnerUpdate(dto);
    }

    public void legerDongAdd(LedgerDTO.LegerDong dto) {
        repository.legerDongAdd(dto);
    }

    public void hisLegerDongAdd(LedgerDTO.LegerDong dto) {
        repository.hisLegerDongAdd(dto);
    }

    public void legerDongUpdate(LedgerDTO.LegerDong dto) {
        repository.legerDongUpdate(dto);
    }

    public void delLegerDong(LedgerDTO.LegerDong dto) {
        repository.delLegerDong(dto);
    }

    public void legerStructuresAdd(LedgerDTO.LegerStructures dto) {
        repository.legerStructuresAdd(dto);
    }

    public void hisLegerStructuresAdd(LedgerDTO.LegerStructures dto) {
        repository.hisLegerStructuresAdd(dto);
    }

    public void legerStructuresUpdate(LedgerDTO.LegerStructures dto) {
        repository.legerStructuresUpdate(dto);
    }

    public void delLegerStructures(LedgerDTO.LegerStructures dto) {
        repository.delLegerStructures(dto);
    }

    public void legerGroundAdd(LedgerDTO.LegerGround dto) {
        repository.legerGroundAdd(dto);
    }

    public void hisLegerGroundAdd(LedgerDTO.LegerGround dto) {
        repository.hisLegerGroundAdd(dto);
    }

    public void legerGroundUpdate(LedgerDTO.LegerGround dto) {
        repository.legerGroundUpdate(dto);
    }

    public void delLegerGround(LedgerDTO.LegerGround dto) {
        repository.delLegerGround(dto);
    }

    public void legerPermitAdd(LedgerDTO.LegerPermit dto) {
        repository.legerPermitAdd(dto);
    }

    public void hisLegerPermitAdd(LedgerDTO.LegerPermit dto) {
        repository.hisLegerPermitAdd(dto);
    }

    public void delLegerPermit(LedgerDTO.LegerPermit dto) {
        repository.delLegerPermit(dto);
    }

    public void legerPermitUpdate(LedgerDTO.LegerPermit dto) {
        repository.legerPermitUpdate(dto);
    }

    public void legerLimitedUpdate(LedgerDTO.LegerLimited dto) {
        //dto.setCrgb("u");
        repository.legerLimitedUpdate(dto);
        //repository.hisLegerLimitedAdd(dto);
    }

    public void hisLegerLimitedAdd(LedgerDTO.LegerLimited dto) {
        //dto.setCrgb("u");
        dto.setDeptCd("11130"); //  '1', f_cdname('R04', '11130'),
        repository.hisLegerLimitedAdd(dto);
    }

    public void deleteLimitedHead(LedgerDTO.LegerLimited dto) {
        repository.deleteLimitedHead(dto);
    }

    /*public List<GreenBeltLedgerExcelDTO> findLedgerDataExcel(GreenBeltLedgerExcelDTO dto) {
        return repository.findLedgerExcelList(dto);
    }*/

    public List<GreenBeltLedgerExcelDTO> findLedgerDataExcel(GreenBeltLedgerExcelDTO dto) {
        return repository.findLedgerExcelList(dto);
    }

    public List<HistoryMainDTO> findDailyHistoryDataExcel(HistoryMainDTO dto) {
        return repository.findDailyHistoryDataExcel(dto);
    }

    public void legerLimitedAdd(LedgerDTO dto) {
    }

   /* public List<GreenBeltLedgerExcelDTO> findLedgerExcelList(GreenBeltLedgerExcelDTO dto) {
        // 검색에 필요한 내용으로 수정
        return repository.findLedgerExcelList(dto);
    }*/
}
