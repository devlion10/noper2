package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class OwnerChangeDTO extends UserDTO{
    private String isLocal;
    // 분기
    private String ownerChangeFlag;
    // 순번
    @ExcelOrder(order = 1, headerName = "No.")
    private String rn;
    private String skkseqno;
    @ExcelOrder(order = 2, headerName = "대장번호")
    private String skkseqno1;
    private String skkseqno2;
    private String suilno;
    // 변경 병합구분
    private String changDivision;
    // 접수일자
    @ExcelOrder(order = 9, headerName = "접수일자")
    private String sujilja;
    private String sujilja1;
    private String issueorgkey;
    private String sujilja2;
    // 처리일자
    @ExcelOrder(order = 10, headerName = "처리일자")
    private String crilja;
    private String crilja1;
    private String crilja2;
    private String gubogoilja;
    // 변경사유
    @ExcelOrder(order = 8, headerName = "변경사유")
    private String bksu;
    // 건물주이름
    @ExcelOrder(order = 6, headerName = "현소유자")
    private String gmjname;
    // 건물주주민
    private String gmjjumin;
    @ExcelOrder(order = 7, headerName = "전소유자")
    private String jgmjname;
    // 전 건물주주민
    private String jgmjjumin;
    // 전 건물주주민
    private String jgmjjumin1;
    // 전 건물주주민
    private String jgmjjumin2;
    private String gmjuso;
    private String jgmjuso;
    // 기관 코드
    private String gmskk;
    // 등재 코드
    private String gmdjgb;
    // 무허가관리번호
    private String gmseqco;
    // 무허가관리번호
    private String gmseqco2;
    // 건물구조
    private String gmgzcd;
    // 건물건평
    private String gmgunp;
    // 건물층수
    private String gmfoors;
    // 건축일자
    private String gmgunil;
    // 건축일자1
    private String gmgunil1;
    // 건축일자2
    private String gmgunil2;
    // 토지건평
    private String tojimj;
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
    private String degb;
    private String degbTemp;
    private String bjuso;
    private String gmzip1;
    private String gmzip2;
    private String gmjzip1;
    private String gmjzip2;
    private String jgmjzip1;
    private String jgmjzip2;
    private String bigo;
    private String delc;
    private String gmjilno;
    private String gmjyn;
    // 신고필증번호
    private String sgpjno;
    // 신고필증신고일자
    private String sgpjdate;
    private String issue;
    private String issuergkey;
    // 매매가격
    private String salesprice;
    private String cntgmj;

    private String fileSeq;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private String fileSize;


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

    // 지목
    private String tojijmg;


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
    private String gmyd;
    // 대장번호
    private String gmbunho;

    // 건물기관이름
    private String gmskkname;
    // 행정동 명
    private String hjdongnm;

    // 건물주 주소(123-123)
    private String gmjjname;
    private String jgmjjname;
    // 건물주 주소
    private String gmjsna;

    // 관리행정동 코드
    private String gmhjdcd;
    // 관리행정동 이름
    private String gmhjdnm;
    private String naSigunguCdOwner;


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
    private String naSigunguCd;
    private String jNaBjdongNo;
    private String jNaRoadNm;
    private String jNaMainBun;
    private String jnaSubBun;
    private String jnaEtc;
    private String jnaRoadCd;
    private String jgmjsna;
    private String newAddr1;
    private String newAddr2;
    private String jnewAddr1;
    private String jnewAddr2;
    private String searchJuso;



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
    // 주민 구분
    private String gmjjgn;
    // 전건물주주민 구분
    private String jgmjjgn;
    // 주민 번호
    private String gmjjuminTemp1;
    // 주민 번호
    private String gmjjuminTemp2;
    // 주민 번호
    private String gmjjumin1;
    // 주민 번호
    private String gmjjumin2;
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

    private String gmwebanyn;
    private String bgstopyn;
    private String bscd;


}
