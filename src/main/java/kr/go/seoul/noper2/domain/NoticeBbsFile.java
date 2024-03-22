package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBbsFile {
    private Long fileSeq;
    private String noticeSeq;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private BigDecimal fileSize;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public NoticeBbsFile(Long fileSeq, String noticeSeq, String logicalFilename, String physicalFilename, String filePath, String fileEnd, BigDecimal fileSize, String registId, Date registTs, String updateId, Date updateTs) {
        this.fileSeq = fileSeq;
        this.noticeSeq = noticeSeq;
        this.logicalFilename = logicalFilename;
        this.physicalFilename = physicalFilename;
        this.filePath = filePath;
        this.fileEnd = fileEnd;
        this.fileSize = fileSize;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
