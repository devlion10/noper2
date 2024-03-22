package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.domain.NoticeBbs;
import kr.go.seoul.noper2.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class NoticeDTO extends NoticeBbs {

    private String noticeState;
    private String searchState;
    private String searchTxt;
    private String stDate;
    private String endDate;
    private String userId;
    private String userNm;
    private Long noticeSeq;
    private String pinYn;
    private int cnt;
}
