package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.domain.NoperUserDamo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//현장점검 DTO
@Data
@NoArgsConstructor
@Getter
@Setter
public class NoperSiteChkDTO extends UserDTO{
    // 분기
    private String sitechkParam;
    // 기관 코드
    private String gmskk;
    // 등재 코드
    private String gmdjgb;
    // 무허가 관리코드
    private String gmseqco;
    //
    private String deleteAt;
    //순번
    private String rn;
    // 현장점검 채번
    private String chkilno;
    // 위반여부
    private String violYn;
    // 위반여부
    private String violYnTxt;
    // 위반내용
    private String violCntt;
    // 조치내용
    private String trsctCntt;
    // 조치내용여부
    private String trsctCnttYn;
    // 비고/기타
    private String rem;
    // 확인(승인/미승인)
    private String confirm1;
    //
    private String confirm2;
    // 점검일
    private String chkDate;
    // 점검일(yyyy0101)
    private String chkStDate;
    // 점검일(yyyy1231)
    private String chkEndDate;
    //
    private String chkChargeId;
    // 점검자 이름
    private String chkChargeNm;
    // 조치담당자 아이디
    private String trsctCnfmId;
    // 조치담당자 이름
    private String trsctCnfmNm;
    // 조치 일자
    private String trsctDate;
    //
    private String regDtime;
    // 수정일자
    private String changDtime;
    //직급
    private String chkChargeClspos;
    // 조치 직급
    private String trsctCnfmClspos;
    // 조치한 기관
    private String chkChargeGmskk;
    //
    private String trsctCnfmGmskk;
    //
    private String regtId;
    // 수정한 id
    private String changId;
    // 건물등재번호
    private String gmbunho;
    // 건축물 소재지[기존(미사용)]
    private String gmskkname;
    // 건축물 소재지(현재-도로명)
    private String newAddr1;
    // 건축물 소재지(현재-지번)
    private String newAddr2;
    // 건물주이름
    private String gmjname;
    // 건물면적
    private String gmmunjuk;
    // 층수(건물층수)
    private String gmFloors;
    // 구조(건물구조)
    private String gmgzcd;
    // 용도(건물용도)
    private String gmyd;

    private String userId;
    private String userName;

    
}
