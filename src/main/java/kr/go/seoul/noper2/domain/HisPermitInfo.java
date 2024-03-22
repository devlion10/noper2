package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisPermitInfo {
    private String crdate;
    private Long permitSeq;
    private Long admSeq;
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
    private String crggb;
    private String workid;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private String bsCd;

    @Builder
    public HisPermitInfo(String crdate, Long permitSeq, Long admSeq, String actFlag, String permitStCd, String permitStName, String permitJydCd, String permitJydName, String permitArea, String permitDate, String permitNo, String permitCompDate, String registInfo, String confirmInfo, String crggb, String workid, String registId, Date registTs, String updateId, Date updateTs, String bsCd) {
        this.crdate = crdate;
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
        this.crggb = crggb;
        this.workid = workid;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.bsCd = bsCd;
    }
}
