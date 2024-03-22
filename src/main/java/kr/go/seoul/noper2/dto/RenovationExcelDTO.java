package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Getter
@Setter
public class RenovationExcelDTO extends UserDTO {

    /* Excel */
    // 순번
    @ExcelOrder(order = 1, headerName = "No.")
    private int rn;

    // 대장번호
    @ExcelOrder(order = 2, headerName = "대장번호")
    private String skkseqno;

    // 접수번호
    @ExcelOrder(order = 3, headerName = "접수번호")
    private String balno;

    // 접수일자
    @ExcelOrder(order = 4, headerName = "접수일자")
    private String gbsgoil;

    // 신고내용
    @ExcelOrder(order = 5, headerName = "신고내용")
    private String gbscotent;

    // 처리내용
    @ExcelOrder(order = 6, headerName = "처리내용")
    private String chcontent;

    // 준공일
    @ExcelOrder(order = 7, headerName = "준공일")
    private String jgong;

    // 건물주
    @ExcelOrder(order = 8, headerName = "건물주")
    private String gmjname;

    private String searchJuso;

    // U01F01L1Form
    // 건축일자
    private String gmgunil1;
    private String gmgunil2;
    // 접수일자
    private String gbsgoil1;
    private String gbsgoil2;
    // 준공일자
    private String jgong1;
    private String jgong2;
    
    //등록자 아이디
    private String registId;

    // U01F01F1VO
    // 건축일자
    private String gmgunil;

    private String gmskk;
    private String gmjjumin;
    private String gmgzcd;
    private String gmyd;
    private BigDecimal gmgunp;
    private String gmfoors;
    private BigDecimal tojimunjuk;

    private String hjdcd;
    private String hnam;
    private String bjdcd;
    private String bnam;
    private String gmdjgb;
    private String gmdjgbName;
    private String gmseqco;
    // 대장번호
    private String num;
    private String degb;
    // 건축물 지번주소
    private String gmskkname;
    // 건축물 도로명주소
    private String gmroadjuso;
    // 건물주 지번주소
    private String gmjskkname;
    // 건물주 도로명주소
    private String gmjroadjuso;
    private BigDecimal tojimj;
    private String dhbh;
    private String dhgbh;
    private String dongbh;
    private String donggbh;
    private String daejang;
    private String gmskkcd;
    private String gmbjdcd;
    private String gmhjdcd;
    private String gmcsah;
    private String gmcbuh;
    private String gmcji;
    private String gmcoh;
    private int gmbsdong;

    private BigDecimal gmmunjuk;
    private String tojisg;
    private String tojijmg;
    private String tojidp;
    private String jigujdg;
    private String jigugb;
    private String jiguug;
    private int jigudz;
    private String gmweban;
    private int gmwebanno;
    private String decision;
    private String reason;
    private String bgstop;
    private String updatec;
    private String gmjjgnTemp;

    private int gmjilno;
    private String gmjjgn;
    private String gmjjumin1;
    private String gmjjumin2;
    private String gmzip1;
    private String gmzip2;
    private String gmjjname;
    private String gmjsna;

    private String zip1;
    private String zip2;
    private String zipcode;
    private String zipnm;

    private int suilno;
    private int sujilja;
    private String gmjzip1;
    private String gmjzip2;
    private String jgmjname;
    private String jgmjjgn;
    private String jgmjjumin;
    private String jgmjjumin1;
    private String jgmjjumin2;
    private String jgmjzip1;
    private String jgmjzip2;
    private String jgmjjname;
    private String jgmjsna;
    private String crilja;
    private String bksu;
    private String gubogoilja;

    private int gmilno;
    private int gmbalno;

    private String wbgb;

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
    private String bjuso;
    private String sjuso;
    private String jigb;

    private String kjuso;
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
    private String issueorgkey;
    private String issueorgkeyin;

    private String bjuso1;
    private String kjuso1;

    private String yyyy;
    private String mm;
    private String dd;

    /**
     * 2012-11-26
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

    //(2008.01.03)
    private String gmseqco2;
}


