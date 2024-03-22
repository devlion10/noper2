package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisImgFileinfo {
    private String crdate;
    private Long fileSeq;
    private Long admSeq;
    private String fileFlag;
    private String logicalFilename;
    private String physialFilename;
    private String filePath;
    private String fileEnd;
    private BigDecimal fileSize;
    private String crgb;
    private String workid;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private String bscd;

    @Builder
    public HisImgFileinfo(String crdate, Long fileSeq, Long admSeq, String fileFlag, String logicalFilename, String physialFilename, String filePath, String fileEnd, BigDecimal fileSize, String crgb, String workid, String registId, Date registTs, String updateId, Date updateTs, String bscd) {
        this.crdate = crdate;
        this.fileSeq = fileSeq;
        this.admSeq = admSeq;
        this.fileFlag = fileFlag;
        this.logicalFilename = logicalFilename;
        this.physialFilename = physialFilename;
        this.filePath = filePath;
        this.fileEnd = fileEnd;
        this.fileSize = fileSize;
        this.crgb = crgb;
        this.workid = workid;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.bscd = bscd;
    }
}
