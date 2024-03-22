package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@NoArgsConstructor
public class PartDemolitionExcelDTO extends UserDTO {

    /* Excel */
    // 순번
    @ExcelOrder(order = 1, headerName = "No.")
    private int rn;

    // 대장번호
    @ExcelOrder(order = 2, headerName = "대장번호")
    private String skkseq;

    // 도로명주소
    @ExcelOrder(order = 3, headerName = "도로명주소")
    private String newAddr1;

    // 지번주소
    @ExcelOrder(order = 4, headerName = "지번주소")
    private String newAddr2;

    // 소유자주민번호
    @ExcelOrder(order = 7, headerName = "소유자주민번호")
    private String gmjjumin;

    // 부분철거사유
    @ExcelOrder(order = 8, headerName = "철거사유")
    private String gmbsau;

    // 부분철거일자
    private String gmbilja_v;


    private String searchJuso;


    // 소유자
    private String gmju;
    // 철거사유-철거신고
    private String desau;
    // 철거일자-철거신고
    private String deilja;

    // U01D01F1VO
    // 철거 구분
    private String degb;
    // 대장번호1(기관 코드)
    private String gmskk;
    // 대장번호2[등재 코드(등재=0 / 미등재=1 / 항측판독=2)]
    private String gmdjgb;
    // 대장번호2[등재 구분명(0=등재 / 1=미등재 / 2=항측판독)]
    private String gmdjgbName;
    // 대장번호3(무허가 관리코드)
    private String gmseqco;
    // 무허가 관리 번호
    private String gmseqco2;
    // 대장번호(대장번호1+대장번호2+대장번호3)
    private String daejangBunho;
    // 대장번호(대장번호1+대장번호2+대장번호3)
    private String num;
    // 소유자 이름-부분철거신고
    @ExcelOrder(order = 6, headerName = "소유자")
    private String gmjname;
    // 건물주 주민번호 앞자리
    private String gmjjumin1;
    // 건물주 주민번호 뒷자리
    private String gmjjumin2;
    // 건물주 일련번호?
    private int gmjilno;
    // 우편번호 앞자리
    private String gmzip1;
    // 우편번호 뒷자리
    private String gmzip2;
    // 상세주소
    private String gmjjname;
    // 주소
    private String gmjsna;
    // 건물 주소
    private String bjuso;
    // 건물주 주소
    private String gmjuso;
    // 도엽번호
    private String dhbh;
    // 도엽건물번호
    private String dhgbh;
    // 동번호
    private String dongbh;
    // 동일련번호
    private String donggbh;
    // 대장등재번호
    private String daejang;
    // 주소 세부 구분 산/대지
    @ExcelOrder(order = 5, headerName = "대지/산")
    private String gmcsah;
    // 지번 앞자리
    private String gmcbuh;
    // 지번 뒷자리
    private String gmcji;
    // 건물 구조
    private String gmgzcd;
    // 건물 용도 코드
    private String gmyd;
    // 건물층수
    private String gmfoors;
    // 건물 주소?
    private String gmcoh;

    /* 철거 */
    // 건물면적(당초) m2
    private BigDecimal gmmjuk;
    // 토지면적(당초) m2
    private BigDecimal tojimjuk;
    // 건물면적(당초) 평
    private BigDecimal gmgunp;
    // 토지면적(당초) 평
    private BigDecimal tojimj;
    // 건물면적(현재) m2
    private BigDecimal currentGmbmjuk;
    // 토지면적(현재) m2
    private BigDecimal currentGmbtojimjuk;
    // 건물면적(현재) 평
    private BigDecimal currentGmbgunp;
    // 토지면적(현재) 평
    private BigDecimal currentGmbtojimj;


    // 건축일자
    private String gmgunil;
    // 지목 코드?
    private String tojijmg;
    // 지목명
    private String tojijmgnm;

    // 도시계획
    private String tojidp;
    // 재개발지구여부
    private String jigujdg;
    // 지구구분
    private String jigugb;
    // 지구여건
    private String jiguug;

    // 철거일자 (~ 기간)
    private String deilja1;
    // 발급기관
    private String issueorgkey;

    // 시군구코드?
    private String gmskkcd;
    // 법정동코드
    private String gmbjdcd;
    // 행정동코드
    private String gmhjdcd;
    // 행정동명
    private String gmhjdnm;
    // 주민구분코드
    private String gmjjgn;
    private String gmjjgnTemp;

    // 소유권
    private String tojisg;
    // 부속건축물동수
    private int gmbsdong;

    // 도로저촉
    private int jigudz;


    /* 건물주 보상 현황 */
    // 건물주 보상금 지급일자(YYYYMMDD)
    private String gmjbsilja;
    // 건물주 보상금
    private String gmjbsgum;
    // 아파트 입주권 지급일자(YYYYMMDD)
    private String gmjjbilja;
    // 아파트 지구
    private String gmjigu;
    // 평형(아파트)
    private String gmjjbph;
    // 비고(건물주보상현황)
    private String bidak;
    // 비고
    private String gmbidak;


    /* 소유자 정보 */
    // 소유자 일련번호?
    private int suilno;
    // 소유자 변경 접수일자
    private int sujilja;
    // 소유자 변경 처리일자
    private String crilja;
    // 소유자 변경 사유
    private String bksu;
    // 소유자 카운트
    private int cntgmj;

    // 우편번호 앞자리
    private String gmjzip1;
    // 우편번호 뒷자리
    private String gmjzip2;

    // 전소유자명
    private String jgmjname;
    // 전소유자구분(1내국인/2법인/3외국인)
    private String jgmjjgn;
    // 전소유자 주민번호
    private String jgmjjumin;
    // 전소유자 주민번호 앞자리
    private String jgmjjumin1;
    // 전소유자 주민번호 뒷자리
    private String jgmjjumin2;
    // 전소유자 우편번호 앞자리
    private String jgmjzip1;
    // 전소유자 우편번호 뒷자리
    private String jgmjzip2;
    // 전소유자 주소
    private String jgmjsna;
    // 전소유자 주소 - 상세
    private String jgmjjname;

    /* 소유자변경이력 현황 그리드 */
    // 보고일자
    private String gubogoilja;


    /* 세입자 정보 */
    // 세입자 일련번호?
    private int seipilno;
    // 세입자명
    private String sename;
    // 세입자 구분(내국인/외국인)
    private String sejjgn;
    // 세입자 주민번호
    private String sejjumin;
    // 세입자 주민번호 앞자리
    private String sejjumin1;
    // 세입자 주민번호 뒷자리
    private String sejjumin2;

    /* 세입자 보상금 정보 */
    // 세입자 보상금 지급일자(보상금)
    private String sebsilja;
    // 세입자 주거대책비(보상금)
    private String sebsgum;
    // 세입자 이사비(보상금)
    private String seisgum;

    /* 세입자 임대 아파트 입주권 정보 */
    // 세입자 입주권 지급일자(임대 아파트 입주권)
    private String sejaptilja;
    // 세입자 아파트지구(임대 아파트 입주권)
    private String sejigu;
    // 세입자 아파트명(임대 아파트 입주권)
    private String sejaptname;
    // 세입자 동호수(임대 아파트 입주권)
    private String sejdongho;
    // 세입자 비고
    private String sebidak;



    /* 공통코드 조회 */
    private String cdid;
    private String cdkey;
    private String cdvalue;
    private String cddesc;



    /* 부분철거 */
    // 부분철거일자(YYYYMMDD)
    @ExcelOrder(order = 9, headerName = "철거일자")
    private String gmbilja;
    // 부분철거일자
    private String gmbilja1;
    // 부분철거일자 (~ 기간)
    private String gmbilja2;


    // 건물층수
    private String gmbfoors;
    // 건물면적(㎡)
    private BigDecimal gmbmjuk;
    // 건물면적(평수)
    private BigDecimal gmbgunp;
    // 토지면적(㎡)
    private BigDecimal gmbtojimjuk;
    // 토지면적(평수)
    private String gmbtojimj;

    private String skkseq1;
    // 철거 구분?
    private String gmbdegb;
    // 전체 주소?(지번주소+상세)
    private String kjuso;
    // 주소
    private String bjus;







    private BigDecimal tojimunjuk; //면적2
    private String hjdcd;
    private String hnam;
    private String bjdcd;
    private String bnam;


    private String gmskkname;

    private BigDecimal gmmunjuk;  //면적2

    private String gmweban;
    private int gmwebanno;
    private String decision;
    private String reason;
    private String bgstop;
    private String updatec;

    private String sejjgnTemp; //�ֹα��� ��(������)


    private String zip1;
    private String zip2;
    private String zipcode;
    private String zipnm;




    private int gmilno;
    private String gbsgoil;
    private int gmbalno;
    private String jgong;
    private String wbgb;
    private String gbscotent;
    private String chcontent;
    private String hhgname;
    private String hhname;
    private String sinda;
    private String sinda1;
    private String jungon;
    private String jungon1;

    private String gmbunho;
    private String gunmulno;
    private String jsilja;
    private String trilja;
    private String balno;
    private String conilno;
    private String sinname;
    private String sinjugb;
    private String sinjumin;
    private String sinjumin1;
    private String sinjumin2;
    private String chargetel;
    private String sinzip1;
    private String sinzip2;
    private String sinsna;
    private String sinjname;
    private String bgsu;

    private String sjuso;
    private String jigb;

    private String bigo;

    private String gmwebanyn;
    private String gmwebannoyn;
    private String bgstopyn;

    private String gunno;
    private String gmbsdongG;
    private String conno;
    private String bbjuso;
    private String tuksuji;
    private String gmjjuso;
    private String jsilja1;
    private String trilja1;

    private String key;
    private String prntnm;
    private String prntnm2;


    private String issueorgkeyin;





    private String deiljaV;
    // 건물주 보상금 지급일자(YYYY-MM-DD)
    private String gmjbsiljaV;
    // 아파트 입주권 지급일자(YYYY-MM-DD)
    private String gmjjbiljaV;

    private String jimokValue;

    private String yyyy;
    private String mm;
    private String dd;

    /**
     * 2012-11-26 ���ּҰ��� form �� �߰�
     */
    private String naBjdongNo;
    private String naRoadCd;
    private String naRoadNm;
    private String naMainBun;
    private String naSubBun;
    private String naEtc;
    private String naSigunguCd;

    private String naBjdongNoOwner;
    private String naRoadCdOwner;
    private String naRoadNmOwner;
    private String naMainBunOwner;
    private String naSubBunOwner;
    private String naEtcOwner;
    private String naSigunguCdOwner;

    private String jnaBjdongNoOwner;
    private String jnaRoadCdOwner;
    private String jnaRoadNmOwner;
    private String jnaMainBunOwner;
    private String jnaSubBunOwner;
    private String jnaEtcOwner;
    private String jnaSigunguCdOwner;

    private String sigunguCd;
    private String sigunguNm;
    private String sidoNm;

    private String naBjdongNoRecom;
    private String naRoadCdRecom;
    private String naRoadNmRecom;
    private String naMainBunRecom;
    private String naSubBunRecom;
    private String naEtcRecom;
    private String naSigunguCdRecom;


    /* 히스토리 등록(이력 관리) */
    // 등록자ID
    private String registid;
    // 등록자ID
    private String userId;
    // 사용자 이름
    private String userName;
    // 등록 구분(i=insert, u=update, d=delete)
    private String crgb;
    //
    private String workid;
    //
    private String deptCd;


    /* 주소 찾기 2023-10-25 */
    // 도로명주소(전체)
    private String roadaddr;
    // 지번주소(전체)
    private String jibunaddr;
    private String gmRoadJuso;



    private String jnewAddr1;
    private String jnewAddr2;



}






















