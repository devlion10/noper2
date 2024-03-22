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
public class LedgerDTO extends UserDTO{

    /* 기개발 */
    private LegerLimited legerLimited;
    private LegerOwner legerOwner;
    private LegerDong legerDong;
    private LegerStructures legerStructures;
    private LegerGround legerGround;
    private LegerPermit legerPermit;
    private SiteChk siteChk;
    //private Bldmngingb bldmngingb;

    // 업무구분 - 대장번호 조회시 업무구분 값에 따라 쿼리 분기
//    private String chargeWork;
    private boolean role;

    // 대장구분
    private String legerFlag;
    // popup구분
    private String popFlag;
    // 신규 여부
    private String newyn;
    @ExcelOrder(order = 3, headerName = "도로명주소")
    private String newaddr1;
    @ExcelOrder(order = 4, headerName = "지번주소")
    private String newaddr2;
    private String roadaddr;
    private String jibunaddr;
    private String listCnt;
    private String searchJuso;
    // 기관 코드
    private String gmskk;
    private String jgmjname;
    // 등재 코드
    private String gmdjgb;
    // 도엽번호
    private String dhbh;
    // 도엽건물관리번호
    private String dhgbh;
    // 동번호
    private String dongbh;
    // 동일련번호
    private String donggbh;
    // 대장등재
    private String daejang;
    // 부속건축물
    private String bsdong;
    private String gmhjdname;
    // 토지면적 ㎡
    @ExcelOrder(order = 10, headerName = "토지면적")
    private String tojimunjuk;
    // 건축일자
    @ExcelOrder(order = 11, headerName = "건축일자")
    private String gmgunil;
    // 건축일자1
    private String gmgunil1;
    // 건축일자2
    private String gmgunil2;
    // 지목
    private String tojijmg;
    // 도시계획
    private String tojidp;
    // 재개발지구여부
    private String jigujdg;
    // 지구구분
    private String jigugb;
    // 지구여건
    private String jiguug;
    // 도로저촉
    private String jigudz;
    // 위반사유
    private String gmweban;
    // 위반건수
    private String gmwebanno;
    // 확정일자
    private String decision;
    // 등록사유
    private String reason;
    // 발급정지사유
    private String bgstop;
    // 시군구코드
    private String gmskkcd;
    // 법정동코드
    private String gmbjdcd;
    // 소유구분
    private String tojisg;
    // 용도
    @ExcelOrder(order = 8, headerName = "용도")
    private String gmyd;
    // 대장번호
    @ExcelOrder(order = 2, headerName = "대장번호")
    private String gmbunho;
    // 순번
    @ExcelOrder(order = 1, headerName = "No.")
    private String rn;
    // 건물기관이름
    private String gmskkname;
    // 행정동 명
    private String hjdongnm;
    // 건물주이름
    @ExcelOrder(order = 6, headerName = "건물주")
    private String gmjname;
    // 건물주주민
    private String gmjjumin;
    // 건물주 주소(123-123)
    private String gmjjname;
    // 건물주 주소
    private String gmjsna;
    // 건물주 주소 - 신규확인원발급에 사용
    private String gmjsna2;
    // 도로명 건물주 주소 - 신규확인원발급에 사용
    private String roadgmjsna;
    // 무허가관리번호
    private String gmseqco;
    // 무허가관리번호
    private String gmseqco2;
    // 관리행정동 코드
    private String gmhjdcd;
    // 관리행정동 이름
    private String gmhjdnm;
    // 건물구조
    @ExcelOrder(order = 7, headerName = "건물구조")
    private String gmgzcd;
    // 건물건평
    private String gmgunp;
    // 건물층수
    private String gmfoors;
    // 토지건평
    private String tojimj;
    // 건물면적
    @ExcelOrder(order = 9, headerName = "건물면적")
    private String gmmunjuk;
    // 행정동코드
    private String hjdcd;
    // 행정동이름
    private String hnam;
    // 대지,산
    private String gmcsah;
    // 대지,산 - 대장현황 표출용
    @ExcelOrder(order = 5, headerName = "대지/산")
    private String gmcsahGb;
    // 부속건축물동수
    private String gmbsdong;
    // 우편번호 앞
    private String gmcbuh;
    // 우편번호 뒤
    private String gmcji;
    // 새주소관련
    private String naBjdongNo;
    // 상세주소
    private String gmcoh;
    // 시군구 코드
    private String naSigunguCd;
    // 새주소관련
    private String naRoadCd;
    // 새주소관련
    private String naRoadNm;
    // 새주소본번
    private String naMainBun;
    // 새주소부번
    private String naSubBun;
    // 새주소그외
    private String naEtc;

    // 등록ID
    private String registId;
    // 등록부서
    private String deptCd;
    // 변경업무구분
    private String workid;
    // 변경구분
    private String crgb;
    // 유저ID
    private String userId;
    // 유저ID
    private String userName;
    // 대장정정사유
    private String updatec;
    // 건물주명
    private String gmjnameTemp;
    // 주민 구분
    private String gmjjgnTemp;
    // 주민 번호
    private String gmjjuminTemp1;
    // 주민 번호
    private String gmjjuminTemp2;
    // 수정자
    private String updateId;
    private String bjdcd;
    //시도, 시군구, 법정동 이름
    private String bnam;
    // 대장번호
    private String num;

    private String gmwebnnoyn;
    private String gmdjgbName;
    private String gmbgunp;
    private String gmbtojimj;
    private String gmbmjuk;
    private String gmbtojimjuk;
    private String degb;    //폐쇄
    private String gmwebanyn;
    private String bgstopyn;
    private String gmroad; // 건물도로명
    private String gmcsahSearch; // 대지산검색
    private String jusoSearch; // 건물주소검색

    //개발대장관리
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
    private String preOwnerName; //전체소유자
    private String preOwnerNameSearch; //전체소유자 검색어
    private String buildAddr1;    // 건축물주소 도로명
    private String buildAddr2;    // 건축물주소 지번


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
        private String userName;    // registId의 userName

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
        private String updateId;

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
        private String deptCd;  //부서
        private String registId;
        private String updateId;

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
        private String dongJydListName;
        private String dongStListName;
        private String deptCd;  //부서
        private String registId;
        private String updateId;

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
        private String stJydListName;
        private String stListName;
        private String deptCd;  //부서
        private String registId;
        private String updateId;

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
        private String deptCd;  //부서
        private String registId;
        private String updateId;

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
        private String permitJydListName;
        private String permitStListName;
        private String deptCd;  //부서
        private String registId;
        private String updateId;

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
