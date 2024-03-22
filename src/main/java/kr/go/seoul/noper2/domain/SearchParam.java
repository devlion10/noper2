package kr.go.seoul.noper2.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchParam {
    private String gmskk;
    private String violationType;
    private String userName;
    private String userTelNo;
    private String useType;
    private String address;
    private String newAddress;
    private String gmcsah;
    private String calendarType;
    private String startDate;
    private String endDate;
    private String skkCd;
    private String bjdCd;
    private String deptCd;
    private String perId;
    private String joinType;
    private String compType;
    private String searchType;
    private String searchText;

    @Builder
    public SearchParam(String gmskk, String violationType, String userName, String userTelNo, String useType, String address, String newAddress, String gmcsah, String calendarType, String startDate, String endDate, String skkCd, String bjdCd, String deptCd, String perId, String joinType, String compType, String searchType, String searchText) {
        this.gmskk = gmskk;
        this.violationType = violationType;
        this.userName = userName;
        this.userTelNo = userTelNo;
        this.useType = useType;
        this.address = address;
        this.newAddress = newAddress;
        this.gmcsah = gmcsah;
        this.calendarType = calendarType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.skkCd = skkCd;
        this.bjdCd = bjdCd;
        this.deptCd = deptCd;
        this.perId = perId;
        this.joinType = joinType;
        this.compType = compType;
        this.searchType = searchType;
        this.searchText = searchText;
    }
}