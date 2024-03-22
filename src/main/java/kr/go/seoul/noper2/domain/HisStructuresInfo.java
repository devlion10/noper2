package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisStructuresInfo {
    private String crdate;
    private Long stSeq;
    private Long admSeq;
    private String stDongNo;
    private String stDongJydCd;
    private String stJydName;
    private String stCd;
    private String stName;
    private BigDecimal stArea;
    private BigDecimal stHeight;
    private String stPermitDate;
    private String stPermitNo;
    private String stCompDate;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public HisStructuresInfo(String crdate, Long stSeq, Long admSeq, String stDongNo, String stDongJydCd, String stJydName, String stCd, String stName, BigDecimal stArea, BigDecimal stHeight, String stPermitDate, String stPermitNo, String stCompDate, String registId, Date registTs, String updateId, Date updateTs) {
        this.crdate = crdate;
        this.stSeq = stSeq;
        this.admSeq = admSeq;
        this.stDongNo = stDongNo;
        this.stDongJydCd = stDongJydCd;
        this.stJydName = stJydName;
        this.stCd = stCd;
        this.stName = stName;
        this.stArea = stArea;
        this.stHeight = stHeight;
        this.stPermitDate = stPermitDate;
        this.stPermitNo = stPermitNo;
        this.stCompDate = stCompDate;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
