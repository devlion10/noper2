package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
public class ViomaStatusDTO {
    @ExcelOrder(order = 1, headerName = "위반내역")
    private String gmtype;
    @ExcelOrder(order = 2, headerName = "위반용도")
    private String gmsub;
    @ExcelOrder(order = 3, headerName = "신규적발")
    private String gmNCnt;
    @ExcelOrder(order = 4, headerName = "조치완료")
    private String gmECnt;
    @ExcelOrder(order = 5, headerName = "부과건수")
    private String impoCnt;
    @ExcelOrder(order = 6, headerName = "부과금액(천원)")
    private String impoAmt;
    @ExcelOrder(order = 7, headerName = "징수건수")
    private String collCnt;
    @ExcelOrder(order = 8, headerName = "징수금액(천원)")
    private String collAmt;
    @ExcelOrder(order = 9, headerName = "미납금액(천원)")
    private String nImpoAmt;
    @ExcelOrder(order = 10, headerName = "고발건수")
    private String gmgNCnt;
    @ExcelOrder(order = 11, headerName = "고발에 의한 조치완")
    private String gmgECnt;
    @ExcelOrder(order = 12, headerName = "행정 대집행 건수")
    private String gmhCnt;
}
