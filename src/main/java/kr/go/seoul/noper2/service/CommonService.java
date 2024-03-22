package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.dto.LedgerDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.repository.CommonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonService {
    private final CommonRepository commonRepository;

    //주소 리스트 조회(selectBox 시도 시군구 법정동 list)
    public List<LedgerDTO> findBjdJusoList(LedgerDTO dto, UserAuthentication auth) {
        String id = dto.getUserId();
//        String chargeWork = commonRepository.findUserChargeWorkById(id);
//        dto.setChargeWork(chargeWork);
        boolean role = auth.getIsAdmin();
        dto.setRole(role);
        if(!role){
            dto.setGmskk(auth.getSkkCd());
        }
        List<LedgerDTO> bjdJusoList = commonRepository.findBjdJusoList(dto);
        return bjdJusoList;
    };

    public List<LedgerDTO> findNoperNumListByGmskk(LedgerDTO dto) {
        List<LedgerDTO> noperNumList = commonRepository.findNoperNumListByGmskk(dto);
        log.debug("대장번호 찾기 데이터 건수: {}", noperNumList.size());
        return noperNumList;
    }

    public List<LedgerDTO> findNoperOwnerPopup(LedgerDTO dto) {
        List<LedgerDTO> noperNumList = commonRepository.findNoperOwnerPopup(dto);
        log.debug("소유자 변경이력 찾기 데이터 건수: {}", noperNumList.size());
        return noperNumList;
    }

    public List<LedgerDTO> findNewNoperNumList(LedgerDTO dto) {
        List<LedgerDTO> noperNumList = commonRepository.findNewNoperNumList(dto);
        return noperNumList;
    }



    public List<LedgerDTO> findconfirmationPopup(LedgerDTO dto) {
        return commonRepository.findconfirmationPopup(dto);
    }

    public List<LedgerDTO> findAbsorptionPopup(LedgerDTO dto) {
        return commonRepository.findAbsorptionPopup(dto);
    }
    public List<LedgerDTO> findChangePopup(LedgerDTO dto) {
        return commonRepository.findChangePopup(dto);
    }

}
