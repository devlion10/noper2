package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.dto.LedgerDTO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface CommonRepository {

    String findUserChargeWorkById(String id);

    List<LedgerDTO> findBjdJusoList(LedgerDTO dto);

    List<LedgerDTO> findNoperNumListByGmskk(LedgerDTO dto);

    List<LedgerDTO> findNewNoperNumList(LedgerDTO dto);

    List<LedgerDTO> findconfirmationPopup(LedgerDTO dto);

    List<LedgerDTO> findAbsorptionPopup(LedgerDTO dto);

    List<LedgerDTO> findChangePopup(LedgerDTO dto);

    List<LedgerDTO> findNoperOwnerPopup(LedgerDTO dto);
}