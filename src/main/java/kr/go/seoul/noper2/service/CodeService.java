package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.Code;
import kr.go.seoul.noper2.dto.BjdongDTO;
import kr.go.seoul.noper2.dto.CodeDTO;
import kr.go.seoul.noper2.dto.DemolitionDTO;
import kr.go.seoul.noper2.repository.CodeRepository;
import kr.go.seoul.noper2.util.TypeCasting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodeService {
    private final CodeRepository repository;

    //대장리스트
    public List<Code> findLedget() {
        return repository.findLedget();
    }
    // 용도리스트
    public List<Code> findUsage() {
        return repository.findUsage();
    }
    //소유구분리스트
    public List<Code> findPossession() {return repository.findPossession();}
    //재개발지구여부
    public List<Code> findRedevelopmentDistrict() { return repository.findRedevelopmentDistrict();}
    //지목
    public List<Code> findPdointOut() { return repository.findPdointOut();}
    //지구구분
    public List<Code> findDistrict() { return repository.findDistrict();}
    //도시계획
    public List<Code> findUrbanPlanning() { return repository.findUrbanPlanning();}
    //지구여건
    public List<Code> findConditions() { return repository.findConditions();}
    //구조
    public List<Code> findStructure() { return repository.findStructure();}
    // 주민등록번호 (내국인/법인/외국인)
    public List<Code> findGmjjgnTemp() { return repository.findGmjjgnTemp();}

    @Transactional
    public List<CodeDTO> findDeptList() {
        return TypeCasting.changeTypeList(repository.findDeptList(), CodeDTO.class);
    }
    @Transactional
    public List<CodeDTO> findDptList(String skkCd) {
        return TypeCasting.changeTypeList(repository.findDptList(skkCd), CodeDTO.class);
    }

    @Transactional
    public List<CodeDTO> findSkkList() {
        return TypeCasting.changeTypeList(repository.findSkkList(), CodeDTO.class);
    }

    @Transactional
    public List<BjdongDTO> findBjdNameListBySkkCd(String skkCd) {
        return TypeCasting.changeTypeList(repository.findBjdNameListBySkkCd(skkCd), BjdongDTO.class);
    }
    @Transactional
    public List<CodeDTO> findEmailListBySkkCd(String skkCd){
        return TypeCasting.changeTypeList(repository.findEmailListBySkkCd(skkCd), CodeDTO.class);
    }

    public List<BjdongDTO> findBjdNameListBySkkNm(String skkNm) {
        CodeDTO code = TypeCasting.changeType(repository.findSkkCdBySkkNm(skkNm), CodeDTO.class);
        return TypeCasting.changeTypeList(repository.findBjdNameListBySkkCd(code.getCDKEY()), BjdongDTO.class);
    }

    public List<Code> findGmcsah() {
        List<Code> gmcsahList = new ArrayList<>();
        gmcsahList = repository.findGmcsah();
        return gmcsahList;
    }

    public List<DemolitionDTO> findComCode(DemolitionDTO dto) {
        return repository.findComCode(dto);
    }


    /*개발제한 대장관리*/

    //구조
    public List<Code> findStCd() {
        return repository.findStCd();
    }

    public List<Code> findJydCd() {
        return repository.findJydCd();
    }

    public List<Code> findWorkId() { return repository.findWorkId();
    }

    public List<Code> findGmskk() { return repository.findGmskk();
    }

    public List<Code> findGreenBeltWorkId() { return repository.findGreenBeltWorkId();
    }

    public List<Code> findDemolitionDeuh()  { return repository.findDemolitionDeuh(); }
}
