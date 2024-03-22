package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemoveRentInfoDamo {
    private BigDecimal seipilno;
    private String gmskk;
    private String gmdjgb;
    private BigDecimal gmseqco;
    private String sebilja;
    private String sebdegb;
    private String sebsilja;
    private String sejigu;
    private BigDecimal sebsgum;
    private BigDecimal seisgum;
    private String sejaptilja;
    private String sejaptname;
    private String sejdongho;
    private String sename;
    private String sejjgn;
    private String secSejjumin;
    private String bidak;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public RemoveRentInfoDamo(BigDecimal seipilno, String gmskk, String gmdjgb, BigDecimal gmseqco, String sebilja, String sebdegb, String sebsilja, String sejigu, BigDecimal sebsgum, BigDecimal seisgum, String sejaptilja, String sejaptname, String sejdongho, String sename, String sejjgn, String secSejjumin, String bidak, String registId, Date registTs, String updateId, Date updateTs) {
        this.seipilno = seipilno;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.sebilja = sebilja;
        this.sebdegb = sebdegb;
        this.sebsilja = sebsilja;
        this.sejigu = sejigu;
        this.sebsgum = sebsgum;
        this.seisgum = seisgum;
        this.sejaptilja = sejaptilja;
        this.sejaptname = sejaptname;
        this.sejdongho = sejdongho;
        this.sename = sename;
        this.sejjgn = sejjgn;
        this.secSejjumin = secSejjumin;
        this.bidak = bidak;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
