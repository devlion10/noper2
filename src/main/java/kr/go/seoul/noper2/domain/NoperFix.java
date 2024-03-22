package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoperFix {
    private BigDecimal gmilno;
    private BigDecimal gmbalno;
    private String gmskk;
    private String gmdjgb;
    private BigDecimal gmseqco;
    private String gbsgoil;
    private String gbscotent;
    private String wbgb;
    private String chcontent;
    private String jgong;
    private String hhgname;
    private String hhname;
    private String sinda;
    private String snida1;
    private String jungon;
    private String jungon1;
    private String bigo;
    private String issueorgkey;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public NoperFix(BigDecimal gmilno, BigDecimal gmbalno, String gmskk, String gmdjgb, BigDecimal gmseqco, String gbsgoil, String gbscotent, String wbgb, String chcontent, String jgong, String hhgname, String hhname, String sinda, String snida1, String jungon, String jungon1, String bigo, String issueorgkey, String registId, Date registTs, String updateId, Date updateTs) {
        this.gmilno = gmilno;
        this.gmbalno = gmbalno;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.gbsgoil = gbsgoil;
        this.gbscotent = gbscotent;
        this.wbgb = wbgb;
        this.chcontent = chcontent;
        this.jgong = jgong;
        this.hhgname = hhgname;
        this.hhname = hhname;
        this.sinda = sinda;
        this.snida1 = snida1;
        this.jungon = jungon;
        this.jungon1 = jungon1;
        this.bigo = bigo;
        this.issueorgkey = issueorgkey;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
