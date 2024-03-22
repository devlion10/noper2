package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnlicensedBuildingManagement {
    private String gmskk;
    private String gmdjgb;
    private BigDecimal gmseqco;
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
    private String gmgzcd;
    private BigDecimal gmgunp;
    private BigDecimal gmfoors;
    private String gmyd;
    private String gmgunil;
    private BigDecimal tojimj;
    private String tojisg;
    private String tojijmg;
    private String tojidp;
    private String jigujdg;
    private String jigugb;
    private String jiguug;
    private BigDecimal jigudz;
    private String degb;
    private String bigo;
    private String gmweban;
    private BigDecimal gmwebanno;
    private BigDecimal gmmunjuk;
    private BigDecimal tojimunjuk;
    private BigDecimal gmsbdong;
    private String decision;
    private String reason;
    private String bgstop;
    private String updatec;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private String newyn;
    private String naBjdongNo;
    private String naRoadCd;
    private String naRoadNm;
    private String naMainBun;
    private String naSubBun;
    private String naEtc;
    private String naSigunguCd;
    private String newAddr1;
    private String newAddr2;
    private String rn;
    private String gmskkname;
    private String hjdongnm;
    private String gmjjumin;
    private String gmjname;
    private String gmbunho;



    @Builder
    public UnlicensedBuildingManagement(String gmjname, String gmbunho, String rn, String gmskkname, String hjdongnm, String gmjjumin, String gmskk, String gmdjgb, BigDecimal gmseqco, String dhbh, String dhgbh, String dongbh, String donggbh, String daejang, String gmskkcd, String gmbjdcd, String gmhjdcd, String gmcsah, String gmcbuh, String gmcji, String gmcoh, String gmgzcd, BigDecimal gmgunp, BigDecimal gmfoors, String gmyd, String gmgunil, BigDecimal tojimj, String tojisg, String tojijmg, String tojidp, String jigujdg, String jigugb, String jiguug, BigDecimal jigudz, String degb, String bigo, String gmweban, BigDecimal gmwebanno, BigDecimal gmmunjuk, BigDecimal tojimunjuk, BigDecimal gmsbdong, String decision, String reason, String bgstop, String updatec, String registId, Date registTs, String updateId, Date updateTs, String newyn, String naBjdongNo, String naRoadCd, String naRoadNm, String naMainBun, String naSubBun, String naEtc, String naSigunguCd, String newAddr1, String newAddr2) {
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.dhbh = dhbh;
        this.dhgbh = dhgbh;
        this.dongbh = dongbh;
        this.donggbh = donggbh;
        this.daejang = daejang;
        this.gmskkcd = gmskkcd;
        this.gmbjdcd = gmbjdcd;
        this.gmhjdcd = gmhjdcd;
        this.gmcsah = gmcsah;
        this.gmcbuh = gmcbuh;
        this.gmcji = gmcji;
        this.gmcoh = gmcoh;
        this.gmgzcd = gmgzcd;
        this.gmgunp = gmgunp;
        this.gmfoors = gmfoors;
        this.gmyd = gmyd;
        this.gmgunil = gmgunil;
        this.tojimj = tojimj;
        this.tojisg = tojisg;
        this.tojijmg = tojijmg;
        this.tojidp = tojidp;
        this.jigujdg = jigujdg;
        this.jigugb = jigugb;
        this.jiguug = jiguug;
        this.jigudz = jigudz;
        this.degb = degb;
        this.bigo = bigo;
        this.gmweban = gmweban;
        this.gmwebanno = gmwebanno;
        this.gmmunjuk = gmmunjuk;
        this.tojimunjuk = tojimunjuk;
        this.gmsbdong = gmsbdong;
        this.decision = decision;
        this.reason = reason;
        this.bgstop = bgstop;
        this.updatec = updatec;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.newyn = newyn;
        this.naBjdongNo = naBjdongNo;
        this.naRoadCd = naRoadCd;
        this.naRoadNm = naRoadNm;
        this.naMainBun = naMainBun;
        this.naSubBun = naSubBun;
        this.naEtc = naEtc;
        this.naSigunguCd = naSigunguCd;
        this.newAddr1 = newAddr1;
        this.newAddr2 = newAddr2;
        this.rn = rn;
        this.gmskkname = gmskkname;
        this.hjdongnm = hjdongnm;
        this.gmjjumin = gmjjumin;
        this.gmbunho = gmbunho;
        this.gmjname = gmjname;
    }
}
