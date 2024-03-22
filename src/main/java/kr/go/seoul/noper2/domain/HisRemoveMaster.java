package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisRemoveMaster {
    private String crdate;
    private String gmskk;
    private String gmdjgb;
    private BigDecimal gmseqco;
    private String dhbh;
    private String dhgbh;
    private String dongbh;
    private String donggbh;
    private String deajang;
    private String gmskkcd;
    private String gmbjcd;
    private String gmhjdcd;
    private String gmcsah;
    private String gmcbuh;
    private String gmji;
    private String gmcoh;
    private String gmgzcd;
    private BigDecimal gmgunp;
    private BigDecimal gmfoors;
    private String gmyd;
    private String gmgunil;
    private BigDecimal tojimj;
    private String tojisg;
    private String togijmg;
    private String tojidp;
    private String jigujdg;
    private String jigugb;
    private String jiguug;
    private BigDecimal jigudz;
    private String degb;
    private String desau;
    private String deilja;
    private String bigo;
    private BigDecimal tojimjuk;
    private BigDecimal gmbsdong;
    private BigDecimal gmmjuk;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private String issueorgkey;
    private String naBjdongNo;
    private String naRoadCd;
    private String naRoadNm;
    private String naMainBun;
    private String naSubBun;
    private String naEtc;
    private String naSigunguCd;

    @Builder
    public HisRemoveMaster(String crdate, String gmskk, String gmdjgb, BigDecimal gmseqco, String dhbh, String dhgbh, String dongbh, String donggbh, String deajang, String gmskkcd, String gmbjcd, String gmhjdcd, String gmcsah, String gmcbuh, String gmji, String gmcoh, String gmgzcd, BigDecimal gmgunp, BigDecimal gmfoors, String gmyd, String gmgunil, BigDecimal tojimj, String tojisg, String togijmg, String tojidp, String jigujdg, String jigugb, String jiguug, BigDecimal jigudz, String degb, String desau, String deilja, String bigo, BigDecimal tojimjuk, BigDecimal gmbsdong, BigDecimal gmmjuk, String registId, Date registTs, String updateId, Date updateTs, String issueorgkey, String naBjdongNo, String naRoadCd, String naRoadNm, String naMainBun, String naSubBun, String naEtc, String naSigunguCd) {
        this.crdate = crdate;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.dhbh = dhbh;
        this.dhgbh = dhgbh;
        this.dongbh = dongbh;
        this.donggbh = donggbh;
        this.deajang = deajang;
        this.gmskkcd = gmskkcd;
        this.gmbjcd = gmbjcd;
        this.gmhjdcd = gmhjdcd;
        this.gmcsah = gmcsah;
        this.gmcbuh = gmcbuh;
        this.gmji = gmji;
        this.gmcoh = gmcoh;
        this.gmgzcd = gmgzcd;
        this.gmgunp = gmgunp;
        this.gmfoors = gmfoors;
        this.gmyd = gmyd;
        this.gmgunil = gmgunil;
        this.tojimj = tojimj;
        this.tojisg = tojisg;
        this.togijmg = togijmg;
        this.tojidp = tojidp;
        this.jigujdg = jigujdg;
        this.jigugb = jigugb;
        this.jiguug = jiguug;
        this.jigudz = jigudz;
        this.degb = degb;
        this.desau = desau;
        this.deilja = deilja;
        this.bigo = bigo;
        this.tojimjuk = tojimjuk;
        this.gmbsdong = gmbsdong;
        this.gmmjuk = gmmjuk;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.issueorgkey = issueorgkey;
        this.naBjdongNo = naBjdongNo;
        this.naRoadCd = naRoadCd;
        this.naRoadNm = naRoadNm;
        this.naMainBun = naMainBun;
        this.naSubBun = naSubBun;
        this.naEtc = naEtc;
        this.naSigunguCd = naSigunguCd;
    }
}
