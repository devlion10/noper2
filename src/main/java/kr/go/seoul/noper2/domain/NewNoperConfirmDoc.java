package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewNoperConfirmDoc {
    private BigDecimal conilno;
    private String gmskk;
    private String gmdjgb;
    private BigDecimal gmseqco;
    private String jsilja;
    private String trilja;
    private String sinname;
    private String sinjugb;
    private String sinjumin;
    private String sinzip1;
    private String sinzpi2;
    private String sinjname;
    private String sinsna;
    private String bgsu;
    private String bigo;
    private String delgb;
    private String delc;
    private String issueorgkey;
    private String chargetel;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private String gmjnameobj;
    private String gmjjuminobj;
    private String gmjsnaobj;
    private String registName;
    private String updateName;

    @Builder
    public NewNoperConfirmDoc(BigDecimal conilno, String gmskk, String gmdjgb, BigDecimal gmseqco, String jsilja, String trilja, String sinname, String sinjugb, String sinjumin, String sinzip1, String sinzpi2, String sinjname, String sinsna, String bgsu, String bigo, String delgb, String delc, String issueorgkey, String chargetel, String registId, Date registTs, String updateId, Date updateTs, String gmjnameobj, String gmjjuminobj, String gmjsnaobj, String registName, String updateName) {
        this.conilno = conilno;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.jsilja = jsilja;
        this.trilja = trilja;
        this.sinname = sinname;
        this.sinjugb = sinjugb;
        this.sinjumin = sinjumin;
        this.sinzip1 = sinzip1;
        this.sinzpi2 = sinzpi2;
        this.sinjname = sinjname;
        this.sinsna = sinsna;
        this.bgsu = bgsu;
        this.bigo = bigo;
        this.delgb = delgb;
        this.delc = delc;
        this.issueorgkey = issueorgkey;
        this.chargetel = chargetel;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.gmjnameobj = gmjnameobj;
        this.gmjjuminobj = gmjjuminobj;
        this.gmjsnaobj = gmjsnaobj;
        this.registName = registName;
        this.updateName = updateName;
    }
}
