package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoperStatusDTO {
    @ExcelOrder(order = 1, headerName = "기관")
    private String gmskkcd;
    @ExcelOrder(order = 2, headerName = "전체 총계")
    private String totalCount;
    @ExcelOrder(order = 3, headerName = "기존무허가건물")
    private String normalCount;
    @ExcelOrder(order = 4, headerName = "부분철거")
    private String partRemoveCount;
    @ExcelOrder(order = 5, headerName = "자진철거")
    private String type1;
    @ExcelOrder(order = 6, headerName = "재개발")
    private String type2;
    @ExcelOrder(order = 7, headerName = "보상")
    private String type3;
    @ExcelOrder(order = 8, headerName = "기타")
    private String type4;
    @ExcelOrder(order = 9, headerName = "철거 총계")
    private String removeCount;
}
