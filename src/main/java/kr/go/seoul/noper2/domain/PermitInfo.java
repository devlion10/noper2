package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PermitInfo {
    private BigDecimal permitSeq;
    private BigDecimal admSeq;
    private String actFlag;
    private String permitStCd;
    private String permitStName;
    private String permitJydCd;
    private String permitJydName;
    private String permitArea;
    private String permitDate;
    private String permitNo;
    private String permitCompDate;
    private String registInfo;
    private String confirmInfo;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public PermitInfo(BigDecimal permitSeq, BigDecimal admSeq, String actFlag, String permitStCd, String permitStName, String permitJydCd, String permitJydName, String permitArea, String permitDate, String permitNo, String permitCompDate, String registInfo, String confirmInfo, String registId, Date registTs, String updateId, Date updateTs) {
        this.permitSeq = permitSeq;
        this.admSeq = admSeq;
        this.actFlag = actFlag;
        this.permitStCd = permitStCd;
        this.permitStName = permitStName;
        this.permitJydCd = permitJydCd;
        this.permitJydName = permitJydName;
        this.permitArea = permitArea;
        this.permitDate = permitDate;
        this.permitNo = permitNo;
        this.permitCompDate = permitCompDate;
        this.registInfo = registInfo;
        this.confirmInfo = confirmInfo;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
