package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroundInfo {
    private Long groundSeq;
    private Long admSeq;
    private BigDecimal groundArea;
    private BigDecimal groundContents;
    private BigDecimal chaArea;
    private BigDecimal farmArea;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public GroundInfo(Long groundSeq, Long admSeq, BigDecimal groundArea, BigDecimal groundContents, BigDecimal chaArea, BigDecimal farmArea, String registId, Date registTs, String updateId, Date updateTs) {
        this.groundSeq = groundSeq;
        this.admSeq = admSeq;
        this.groundArea = groundArea;
        this.groundContents = groundContents;
        this.chaArea = chaArea;
        this.farmArea = farmArea;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
