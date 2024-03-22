package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisNoperOwnerDamo {
    private String crdate;
    private BigDecimal gmjilno;
    private String gmskk;
    private String gmdjgb;
    private BigDecimal gmseqco;
    private String gmjname;
    private String gmjjgn;
    private String secGmjjumin;
    private String gmzip1;
    private String gmzip2;
    private String gmjjname;
    private String gmjsna;
    private String bksu;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private String deleteAt;
    private String deleteId;
    private Date deleteTs;
    private String gmjsajuso;
    private String naBjdongNo;
    private String naRoadCd;
    private String naRoadNm;
    private String naMainBun;
    private String naSubBun;
    private String naEtc;
    private String naSigunguCd;
    private String newAddr1;
    private String newAddr2;

    @Builder
    public HisNoperOwnerDamo(String crdate, BigDecimal gmjilno, String gmskk, String gmdjgb, BigDecimal gmseqco, String gmjname, String gmjjgn, String secGmjjumin, String gmzip1, String gmzip2, String gmjjname, String gmjsna, String bksu, String registId, Date registTs, String updateId, Date updateTs, String deleteAt, String deleteId, Date deleteTs, String gmjsajuso, String naBjdongNo, String naRoadCd, String naRoadNm, String naMainBun, String naSubBun, String naEtc, String naSigunguCd, String newAddr1, String newAddr2) {
        this.crdate = crdate;
        this.gmjilno = gmjilno;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.gmjname = gmjname;
        this.gmjjgn = gmjjgn;
        this.secGmjjumin = secGmjjumin;
        this.gmzip1 = gmzip1;
        this.gmzip2 = gmzip2;
        this.gmjjname = gmjjname;
        this.gmjsna = gmjsna;
        this.bksu = bksu;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.deleteAt = deleteAt;
        this.deleteId = deleteId;
        this.deleteTs = deleteTs;
        this.gmjsajuso = gmjsajuso;
        this.naBjdongNo = naBjdongNo;
        this.naRoadCd = naRoadCd;
        this.naRoadNm = naRoadNm;
        this.naMainBun = naMainBun;
        this.naSubBun = naSubBun;
        this.naEtc = naEtc;
        this.naSigunguCd = naSigunguCd;
        this.newAddr1 = newAddr1;
        this.newAddr2 = newAddr2;
    }
}
