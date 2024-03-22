package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisGroundInfo {
    private String crdate;
    private Long groundSeq;
    private Long admSeq;
    private BigDecimal groundArea;
    private BigDecimal groundContents;
    private BigDecimal chaArea;
    private BigDecimal farmArea;
    private String workid;
    private String crgb;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private String bscd;

    @Builder
    public HisGroundInfo(String crdate, Long groundSeq, Long admSeq, BigDecimal groundArea, BigDecimal groundContents, BigDecimal chaArea, BigDecimal farmArea, String workid, String crgb, String registId, Date registTs, String updateId, Date updateTs, String bscd) {
        this.crdate = crdate;
        this.groundSeq = groundSeq;
        this.admSeq = admSeq;
        this.groundArea = groundArea;
        this.groundContents = groundContents;
        this.chaArea = chaArea;
        this.farmArea = farmArea;
        this.workid = workid;
        this.crgb = crgb;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.bscd = bscd;
    }
}
