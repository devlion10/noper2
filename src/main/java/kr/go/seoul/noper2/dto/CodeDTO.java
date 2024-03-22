package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.domain.Code;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CodeDTO {
    private String CDID;
    private String CDKEY;
    private String CDVALUE;
    private String CDDESC;
}
