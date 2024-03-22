package kr.go.seoul.noper2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchParamDTO {
    private boolean statusToList;
    private String gmskk;
    private String violationType;
    private String userName;
    private String userTelNo;
    private String useType;
    private String address;
    private String newAddress;
    private String gmcsah;
    private String calendarType;
    private String calendarType2;
    private String calendarType3;
    private String calendarBtn;
    private String calendarBtn2;
    private String calendarBtn3;
    private String startDate;
    private String endDate;
    private String skkCd;
    private String bjdCd;
    private String insttList;
    private String deptCd;
    private String perId;
    private String joinType;
    private String compType;
    private String searchType;
    private String searchText;
}
