package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//현장점검 DTO
@Data
@NoArgsConstructor
@Getter
@Setter
public class NoperSiteChkExcelDTO extends UserDTO{
    /* Excel */
    //순번
    @ExcelOrder(order = 1, headerName = "No.")
    private String rn;

    // 점검일
    @ExcelOrder(order = 2, headerName = "점검일")
    private String chkDate;

    // 직급
    @ExcelOrder(order = 3, headerName = "직급")
    private String chkChargeClspos;

    // 점검자 이름
    @ExcelOrder(order = 4, headerName = "성명")
    private String chkChargeNm;

    // 건물등재번호
    @ExcelOrder(order = 5, headerName = "건물등재번호")
    private String gmbunho;

    // 건축물 소재지(건물 소재지)
    @ExcelOrder(order = 6, headerName = "건물 소재지")
    private String gmskkname;

    // 건물주
    @ExcelOrder(order = 7, headerName = "건물주")
    private String gmjname;

    // 건물면적
    @ExcelOrder(order = 8, headerName = "면적(㎡)")
    private String gmmunjuk;

    // 층수(건물층수)
    @ExcelOrder(order = 9, headerName = "층수")
    private String gmFloors;

    // 구조(건물구조)
    @ExcelOrder(order = 10, headerName = "구조")
    private String gmgzcd;

    // 용도(건물용도)
    @ExcelOrder(order = 11, headerName = "용도")
    private String gmyd;

    // 위반여부
    @ExcelOrder(order = 12, headerName = "위반여부")
    private String violYn;

    // 조치여부
    @ExcelOrder(order = 13, headerName = "조치여부")
    private String trsctCnttYn;

    @ExcelOrder(order = 14, headerName = "확인")
    // 확인(승인/미승인)
    private String confirm1;


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

    // 현장점검 채번
    private String chkilno;
    // 위반여부
    private String violYnTxt;
    // 위반내용
    private String violCntt;
    // 조치내용
    private String trsctCntt;

    // 비고/기타
    private String rem;
    //
    private String confirm2;
    // 점검일(yyyy0101)
    private String chkStDate;
    // 점검일(yyyy1231)
    private String chkEndDate;
    //
    private String chkChargeId;
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

    private String userId;
    private String userName;

    
}
