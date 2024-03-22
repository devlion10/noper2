package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.domain.Bjdong;
import kr.go.seoul.noper2.domain.Code;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BjdongDTO {
    private String key;
    private String dpname;
    private String prntnm;
}
