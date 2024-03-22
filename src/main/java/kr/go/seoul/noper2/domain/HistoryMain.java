package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryMain {
    private String crdate;
    private String gmskk;
    private String gmdjgb;
    private BigDecimal gmseqco;
    private String bscd;
    private String workid;
    private String crgb;
    private String userName;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private int historySeq;
    private String workName;
    private String etc;
    private String bscdUsername;


    @Builder
    public HistoryMain(String crdate, String gmskk, String gmdjgb, BigDecimal gmseqco, String bscd, String workid, String crgb, String userName, String registId, Date registTs, String updateId, Date updateTs, int historySeq, String workName, String etc, String bscdUsername) {
        this.crdate = crdate;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.bscd = bscd;
        this.workid = workid;
        this.crgb = crgb;
        this.userName = userName;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.historySeq = historySeq;
        this.workName = workName;
        this.etc = etc;
        this.bscdUsername = bscdUsername;
    }
}
