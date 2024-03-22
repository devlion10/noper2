package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Data
@NoArgsConstructor
public class GreenBeltLedgerExcelDTO extends UserDTO{

    /* 기개발 */
    private LegerLimited legerLimited;
    private LegerOwner legerOwner;
    private LegerDong legerDong;
    private LegerStructures legerStructures;
    private LegerGround legerGround;
    private LegerPermit legerPermit;
    private SiteChk siteChk;
    //private Bldmngingb bldmngingb;


    //기개발대장관리
    private String admSeqSearch;    // 관리번호 검색어
    private String ownerAllNameSearch;  // 전체소유자 검색어
    private String owner1NameSearch;    // 현소유자 검색어
    private String roadaddrSearch;  // 도로명주소 검색어
    private String jibunaddrSearch; // 지번주소 검색어
    @ExcelOrder(order = 1, headerName = "No.")
    private String rn; // 연번
    @ExcelOrder(order = 2, headerName = "관리번호")
    private String admSeq;  //관리번호
    @ExcelOrder(order = 3, headerName = "도로명주소")
    private String buildAddr1;    // 주소
    @ExcelOrder(order = 4, headerName = "지번주소")
    private String buildAddr2;    // 주소
    @ExcelOrder(order = 5, headerName = "대지/산")
    //private String skkSah; //건물주소
    private String cSah;    // 대지산
    @ExcelOrder(order = 6, headerName = "건물주")
    private String owner1Name;  //건물주
    @ExcelOrder(order = 7, headerName = "구조")
    private String stCd;    //구조
    @ExcelOrder(order = 8, headerName = "용도")
    private String jydCd;   //용도
    @ExcelOrder(order = 9, headerName = "건평")
    private String buildMj; //건평
    @ExcelOrder(order = 10, headerName = "면적(m2)")
    private String groundArea;  //면적
    private String ownerAllName;  //전체소유자
    private String skkCd;   //위치시군구코드
    private String bjdCd;   //위치법정동코드
    private String hjdCd;   //위치행정동코드
    private String cBuh;   //위치본번
    private String cJi;   //위치부번
    private String gmcSah;    // 대지산
    @ExcelOrder(order = 11, headerName = "폐쇄")
    private String degb;    // 폐쇄
    private String jusoSearch; //건물주소
    private String skkName; //건물주소
    private String gmcsahSearch; //건물주소
    private String preOwnerName; //전체소유자
    private String preOwnerNameSearch; //전체소유자 검색어
    @Data
    public static class LegerLimited {
        private String searchState;
        private String admSeqSearch;    // 관리번호 검색어
        private String ownerAllNameSearch;  // 전체소유자 검색어
        private String owner1NameSearch;    // 현소유자 검색어
        private String roadaddrSearch;  // 도로명주소 검색어
        private String jibunaddrSearch; // 지번주소 검색어
        private String stDate;
        private String endDate;
        private String admSeq;  //관리번호
        private String skkName; //건물주소
        private String owner1Name;  //건물주
        private String stCd;    //구조
        private String jydCd;   //용도
        private String buildMj; //건평
        private String groundArea;  //면적
        private String ownerAllName;  //전체소유자
        private String skkCd;   //위치시군구코드
        private String bjdCd;   //위치법정동코드
        private String hjdCd;   //위치행정동코드
        private String cSah;   //위치대지구분
        private String cBuh;   //위치본번
        private String cJi;   //위치부번
        private String gmcSah; //대지,산

        private String crgb;    //이력
        private String limitedParam;
        private String deptCd;  //부서
        private String degb;    // 폐쇄
        private String buildAddr1;    // 건축물주소 도로명
        private String buildAddr2;    // 건축물주소 지번

        private String bigo;
        private String bjdCdOwner;
        private String buildFlag;
        private String buildingJibunaddr;
        private String buildingRoadaddr;
        private String confirmName;
        private String gmcSahOwner;
        private String gmcbuh;
        private String gmcbuhOwner;
        private String gmcji;
        private String gmcjiOwner;
        private String gmcoh;
        private String gmcohOwner;
        private String gmgzcd;
        private String gmhjdcd;
        private String gmyd;
        private String illegal;
        private String owner1Zip1;
        private String owner1Zip2;
        private String ownerJibunaddr;
        private String ownerRoadaddr;
        private String writeReason;
        private String owner1Addr1;
        private String owner1Addr2;
        private String registId;

    }
    @Data
    public static class LegerOwner {
        private String admSeqSearch;    // 관리번호 검색어
        private String ownerAllNameSearch;  // 전체소유자 검색어
        private String owner1NameSearch;    // 현소유자 검색어
        private String roadaddrSearch;  // 도로명주소 검색어
        private String jibunaddrSearch; // 지번주소 검색어
        private String admSeq;  //관리번호
        private String skkName; //건물주소
        private String owner1Name;  //건물주
        private String stCd;    //구조
        private String jydCd;   //용도
        private String buildMj; //건평
        private String groundArea;  //면적
        private String ownerAllName;  //전체소유자
        private String skkCd;   //위치시군구코드
        private String bjdCd;   //위치법정동코드
        private String hjdCd;   //위치행정동코드
        private String cSah;   //위치대지구분
        private String cBuh;   //위치본번
        private String cJi;   //위치부번
        private String gmcSah; //대지,산

        private String ownerRn; //no.
        private String ownerParam;  //분기

        private String crgb;
        private String ownerSeq;
        private String chDate;  //양도양수일자
        private String confirmName; //확인자
        private String ownerAddr1;
        private String ownerAddr2;
        private String ownerZip1;
        private String ownerZip2;
        private String preOwnerName;    //양수자이름

    }
    @Data
    public static class LegerDong {
        private String admSeqSearch;    // 관리번호 검색어
        private String ownerAllNameSearch;  // 전체소유자 검색어
        private String owner1NameSearch;    // 현소유자 검색어
        private String roadaddrSearch;  // 도로명주소 검색어
        private String jibunaddrSearch; // 지번주소 검색어
        private String admSeq;  //관리번호
        private String skkName; //건물주소
        private String owner1Name;  //건물주
        private String stCd;    //구조
        private String jydCd;   //용도
        private String buildMj; //건평
        private String groundArea;  //면적
        private String ownerAllName;  //전체소유자
        private String skkCd;   //위치시군구코드
        private String bjdCd;   //위치법정동코드
        private String hjdCd;   //위치행정동코드
        private String cSah;   //위치대지구분
        private String cBuh;   //위치본번
        private String cJi;   //위치부번
        private String gmcSah; //대지,산

        private String dongParam;
        private String dongRn;
        private String crgb;

        private String dongSeq;
        private String dongCompDate;
        private String dongJydCd;
        private String dongJydName;
        private String dongNo;
        private String dongPermitDate;
        private String dongPermitNo;
        private String dongStCd;
        private String dongStName;
        private String floor1Scale;
        private String floor2Scale;
        private String floor3Scale;
        private String floor4Scale;
        private String floorsSumScale;
        private String underScale;

    }
    @Data
    public static class LegerStructures {
        private String admSeqSearch;    // 관리번호 검색어
        private String ownerAllNameSearch;  // 전체소유자 검색어
        private String owner1NameSearch;    // 현소유자 검색어
        private String roadaddrSearch;  // 도로명주소 검색어
        private String jibunaddrSearch; // 지번주소 검색어
        private String admSeq;  //관리번호
        private String skkName; //건물주소
        private String owner1Name;  //건물주
        private String jydCd;   //용도
        private String buildMj; //건평
        private String groundArea;  //면적
        private String ownerAllName;  //전체소유자
        private String skkCd;   //위치시군구코드
        private String bjdCd;   //위치법정동코드
        private String hjdCd;   //위치행정동코드
        private String cSah;   //위치대지구분
        private String cBuh;   //위치본번
        private String cJi;   //위치부번
        private String gmcSah; //대지,산

        private String stRn;
        private String stParam;
        private String crgb;

        private String stArea;
        private String stCompDate;
        private String stDongNo;
        private String stHeight;
        private String stJydCd;
        private String stJydName;
        private String stCd; //구조
        private String stName;
        private String stPermitDate;
        private String stPermitNo;
        private String stSeq;

    }
    @Data
    public static class LegerGround {
        private String admSeqSearch;    // 관리번호 검색어
        private String ownerAllNameSearch;  // 전체소유자 검색어
        private String owner1NameSearch;    // 현소유자 검색어
        private String roadaddrSearch;  // 도로명주소 검색어
        private String jibunaddrSearch; // 지번주소 검색어
        private String admSeq;  //관리번호
        private String skkName; //건물주소
        private String owner1Name;  //건물주
        private String stCd;    //구조
        private String jydCd;   //용도
        private String buildMj; //건평
        private String ownerAllName;  //전체소유자
        private String skkCd;   //위치시군구코드
        private String bjdCd;   //위치법정동코드
        private String hjdCd;   //위치행정동코드
        private String cSah;   //위치대지구분
        private String cBuh;   //위치본번
        private String cJi;   //위치부번
        private String gmcSah; //대지,산

        private String groundRn;
        private String groundParam;
        private String crgb;

        private String groundSeq;
        private String groundArea;  //면적
        private String chaArea;
        private String farmArea;
        private String groundContents;
        private String groundEtc;

    }
    @Data
    public static class LegerPermit {
        private String admSeqSearch;    // 관리번호 검색어
        private String ownerAllNameSearch;  // 전체소유자 검색어
        private String owner1NameSearch;    // 현소유자 검색어
        private String roadaddrSearch;  // 도로명주소 검색어
        private String jibunaddrSearch; // 지번주소 검색어
        private String admSeq;  //관리번호
        private String skkName; //건물주소
        private String owner1Name;  //건물주
        private String stCd;    //구조
        private String jydCd;   //용도
        private String buildMj; //건평
        private String groundArea;  //면적
        private String ownerAllName;  //전체소유자
        private String skkCd;   //위치시군구코드
        private String bjdCd;   //위치법정동코드
        private String hjdCd;   //위치행정동코드
        private String cSah;   //위치대지구분
        private String cBuh;   //위치본번
        private String cJi;   //위치부번
        private String gmcSah; //대지,산

        private String permitRn;
        private String permitParam;
        private String crgb;

        private String permitSeq;
        private String actFlag;
        private String confirmInfo;
        private String permitArea;
        private String permitCompDate;
        private String permitJydCd;
        private String permitJydName;
        private String permitNo;
        private String permitDate;
        private String permitStCd;
        private String permitStName;
        private String registInfo;
    }
    @Data
    public static class SiteChk {
        private String admSeqSearch;    // 관리번호 검색어
        private String ownerAllNameSearch;  // 전체소유자 검색어
        private String owner1NameSearch;    // 현소유자 검색어
        private String roadaddrSearch;  // 도로명주소 검색어
        private String jibunaddrSearch; // 지번주소 검색어
        private String admSeq;  //관리번호
        private String skkName; //건물주소
        private String owner1Name;  //건물주
        private String stCd;    //구조
        private String jydCd;   //용도
        private String buildMj; //건평
        private String groundArea;  //면적
        private String ownerAllName;  //전체소유자
        private String skkCd;   //위치시군구코드
        private String bjdCd;   //위치법정동코드
        private String hjdCd;   //위치행정동코드
        private String cSah;   //위치대지구분
        private String cBuh;   //위치본번
        private String cJi;   //위치부번
        private String gmcSah; //대지,산

        private String chaAreaTemp;
        private String chkDate;
        private String chkilno;
        private String farmAreaTemp;
        private String groundAreaTemp;
        private String groundContentsTemp;
        private String groundEtcTemp;
        private String sitechkParam;
    }


}
