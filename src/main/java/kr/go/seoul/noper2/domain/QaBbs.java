package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QaBbs {
    private BigDecimal qaSeq;
    private String qaFlag;
    private String qaSubject;
    private String qaContents;
    private String registId;
    private String registTs;
    private String updateId;
    private String updateTs;
    private String fixFlag;
    private String secYn;
    private String qaType;


    @Builder
    public QaBbs(BigDecimal qaSeq, String qaFlag, String qaSubject, String qaContents, String registId, String registTs, String updateId, String updateTs, String fixFlag, String secYn, String qaType) {
        this.qaSeq = qaSeq;
        this.qaFlag = qaFlag;
        this.qaSubject = qaSubject;
        this.qaContents = qaContents;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.fixFlag = fixFlag;
        this.secYn = secYn;
        this.qaType = qaType;
    }
}
