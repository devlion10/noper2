package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ConfirmationDTO extends UserDTO{
    // 확인원구분
    private String confirmationFlag;
    // 신규 여부
    private String newyn;
    // 기관 코드
    private String gmskk;
    // 시군구코드
    private String gmskkcd;
    // 등재 코드
    private String gmdjgb;
    // 건축일자
    private String gmgunil;
    // 건축일자1
    private String gmgunil1;
    // 건축일자2
    private String gmgunil2;
    // 순번
    @ExcelOrder(order = 1, headerName = "No.")
    private String rn;
    // 도시계획
    private String tojidp;
    // 대장번호
    @ExcelOrder(order = 2, headerName = "대장번호")
    private String gmbunho;
    // 건물주주민
    private String gmjjumin;
    // 무허가관리번호
    private String gmseqco;
    // 건물구조
    private String gmgzcd;
    // 건물건평
    private String gmgunp;
    // 건물층수
    private String gmfoors;
    // 토지건평
    private String tojimj;
    // 건물넘버
    private String gunmulno;
    // 확인원발급번호
    private String conilno;
    // 확인원발급번호 목록 (신규확인원발급에서 사용중)
    private List<String> conilnoList;
    // 신청자
    @ExcelOrder(order = 7, headerName = "신청인")
    private String sinname;
    // 신청자구분
    private String sinjugb;
    // 신청인주민번호
    private String sinjumin;
    private String sinjumin1;
    private String sinjumin2;
    // 발급통수
    private String bgsu;
    // 비고사항
    @ExcelOrder(order = 9, headerName = "발급용도")
    private String bigo;
    // 확인원발급번호 "-"
    @ExcelOrder(order = 8, headerName = "발급번호")
    private String balNo;
    // SINSNA(신청인특수주소)+ SINJNAME(신청인외주소) = SJUSO
    private String sjuso;
    // 발급기관키
    private String issueorgkey;
    private String sinsna;
    //SUBSTR( F_BJD_NAME( GMSKKCD , GMBJDCD ) , 1 , 40 ) ||' ' ||COM_CODE.CDVALUE||'' || NOPER_MASTER.GMCBUH||'-' ||NOPER_MASTER.GMCJI||' ' ||NOPER_MASTER.GMCOH BJUSO,
    //서울특별시 동대문구 이문동 대지257-500
    private String bjuso;
    // 지목????
    private String jigb;
    // 철거구분
    private String degb;
    // 건물주
    @ExcelOrder(order = 6, headerName = "건물주")
    private String gmju;
    // 건물주 주소
    private String kjuso;
    // 접수일자1
    @ExcelOrder(order = 10, headerName = "접수일자")
    private String jsilja;
    // 접수일자1
    private String jsilja1;
    // 접수일자2
    private String jsilja2;
    // 처리일자
    @ExcelOrder(order = 11, headerName = "처리일자")
    private String trilja;

    // 처리일자
    private String naSigunguCdOwner;

    // 주소찾기
    private String searchJuso;

    // 처리일자1
    private String trilja1;
    // 처리일자2
    private String trilja2;
    private String sinzip1;
    private String sinzip2;
    private String sinjname;
    private String chargetel;
    // 등록ID
    private String registId;
    @ExcelOrder(order = 3, headerName = "도로명주소")
    private String newaddr1;
    @ExcelOrder(order = 4, headerName = "지번주소")
    private String newaddr2;









    private String roadaddr;
    private String jibunaddr;
    private String listCnt;
    // 건축면적
    private String tojmunjuk;
    // 건물면적
    private String gmmunjuk;
    // 행정동코드
    private String hjdcd;
    // 행정동이름
    private String hnam;
    // 대지,산
    @ExcelOrder(order = 5, headerName = "대지/산")
    private String gmcsah;
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
    //시도, 시군구, 법정동 이름
    private String bnam;

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

    // 법정동코드
    private String gmbjdcd;
    // 소유구분
    private String tojisg;
    // 용도
    private String gmyd;
    // 무허가관리번호
    private String gmseqco2;
    // 관리행정동 코드
    private String gmhjdcd;
    // 관리행정동 이름
    private String gmhjdnm;
    // 건물기관이름
    private String gmskkname;
    // 행정동 명
    private String hjdongnm;
    // 건물주이름
    private String gmjname;
    // 지목
    private String tojijmg;
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
    private String tojimunjuk;
    //삭제사유
    private String delc;
}
