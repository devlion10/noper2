package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoperStatusByPeriodDTO {
    @ExcelOrder(order = 1, headerName = "년")
    private String year;
    @ExcelOrder(order = 2, headerName = "월")
    private String month;
    @ExcelOrder(order = 3, headerName = "자진철거")
    private String type1;
    @ExcelOrder(order = 4, headerName = "재개발")
    private String type2;
    @ExcelOrder(order = 5, headerName = "보상")
    private String type3;
    @ExcelOrder(order = 6, headerName = "기타")
    private String type4;
    @ExcelOrder(order = 7, headerName = "철거 월 계")
    private String sum;
    @ExcelOrder(order = 8, headerName = "기존무허가 관리대장 등재(판독) 내역")
    private String inc;
    @ExcelOrder(order = 9, headerName = "기존무허가 건축물 총 계")
    private String total;
}
