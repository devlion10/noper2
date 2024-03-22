package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EndPeriod {
    private Long seq;
    private String updateId;
    private String rptDate;
    private String startDate;
    private String endDate;
    private String chargeGbn;
    private String registId;
    private Date registTs;
    private Date updateTs;

    @Builder
    public EndPeriod(Long seq, String updateId, String rptDate, String startDate, String endDate, String chargeGbn, String registId, Date registTs, Date updateTs) {
        this.seq = seq;
        this.updateId = updateId;
        this.rptDate = rptDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.chargeGbn = chargeGbn;
        this.registId = registId;
        this.registTs = registTs;
        this.updateTs = updateTs;
    }
}
