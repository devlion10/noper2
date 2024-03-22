package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.Bjdong;
import kr.go.seoul.noper2.domain.Code;
import kr.go.seoul.noper2.dto.DemolitionDTO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface CodeRepository {
    List<Code> findLedget(); //대장리스트
    List<Code> findPossession(); //소유구분리스트
    List<Code> findUsage();  // 용도리스트
    List<Code> findRedevelopmentDistrict();//재개발지구여부
    List<Code> findPdointOut(); //지목
    List<Code> findDistrict();//지구구분
    List<Code> findUrbanPlanning(); //도시계획
    List<Code> findConditions();//지구여건
    List<Code> findStructure();//구조
    List<Code> findDeptList();
    List<Code> findSkkList();
    List<Bjdong> findBjdNameListBySkkCd(String skkCd);
    Code findSkkCdBySkkNm(String skkNm);
    List<Code> findGmjjgnTemp(); // 주민등록번호 (내국인/법인/외국인)

    List<Code> findGmcsah();//대지,산,블록 구분 조회

    List<DemolitionDTO> findComCode(DemolitionDTO dto);

    List<Code> findStCd();  //개발제한 구조

    List<Code> findJydCd(); //개발제한 주용도

    List<Code> findDemolitionDeuh(); // 철거유형(4종)

    List<Code> findWorkId();    // 기존무허가 일일처리내역 업무구분

    List<Code> findGmskk();     // 기존무허가 일일처리내역 시군구

    List<Code> findGreenBeltWorkId();   // 개발제한 일일처리내역 업무구분

    List<Code> findDptList(String skkCd); // 자치구 코드를 받아 부서 전체를 조회

    List<Code> findEmailListBySkkCd(String skkCd); // 자치구 코드를 받아 자치구 이메일을 조회
}
