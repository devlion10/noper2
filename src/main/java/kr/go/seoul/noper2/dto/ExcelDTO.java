package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelDTO {
    @ExcelOrder(order = 1, headerName = "테스트1")
    private String test1;
    @ExcelOrder(order = 2, headerName = "테스트2")
    private String test2;
}

