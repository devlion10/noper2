package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class HistoryMainDTO extends HistoryMain {
    private String HistoryState;
    private String searchState;
    private String searchTxt;
    private String stDate;
    private String endDate;
    private String userId;
    private String userNm;
    @ExcelOrder(order = 6, headerName = "작업자")
    private String bscdUsername;
    private String gmskkSearch;
    private String workidSearch;
    private String skkcdSearch;
    @ExcelOrder(order = 1, headerName = "No.")
    private int historySeq;
    @ExcelOrder(order = 2, headerName = "업무구분")
    private String workName;
    @ExcelOrder(order = 3, headerName = "작업구분")
    private String crgb;
    @ExcelOrder(order = 4, headerName = "작업내용")
    private String etc;
    @ExcelOrder(order = 5, headerName = "작업시각")
    private String crdate;
}
