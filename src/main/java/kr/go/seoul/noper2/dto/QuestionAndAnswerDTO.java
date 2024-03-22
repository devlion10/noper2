package kr.go.seoul.noper2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class QuestionAndAnswerDTO extends UserDTO {

    private int cnt;
    private String userId;
    private boolean isAdmin;
    private String userNm;
    private String stDate;
    private String endDate;
    private String answerState;
    private String searchState;
    private String searchTxt;
    private String allMyChk;
    private String qaType;
    private String qaFlag;
    private String fixFlag;
    private String secYn;
    private String qaSubject;
    private String qaContents;
    private String registId;
    private String registTs;
    private String updateId;
    private String updateTs;
    private Long qaSeq;
    private Long readCnt;
}


