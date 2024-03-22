package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBbs {
    private Long noticeSeq;
    private String noticeSubject;
    private String noticeContents;
    private String registId;
    private String registTs;
    private String updateId;
    private String updateTs;
    private String fixFlag;
    private int readCnt;
    private int cnt;

    @Builder
    public NoticeBbs(Long noticeSeq, String noticeSubject, String noticeContents, String registId, String registTs, String updateId, String updateTs, String fixFlag, int readCnt, int cnt) {
        this.noticeSeq = noticeSeq;
        this.noticeSubject = noticeSubject;
        this.noticeContents = noticeContents;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.fixFlag = fixFlag;
        this.readCnt = readCnt;
        this.cnt = cnt;
    }
}
