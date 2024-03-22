package kr.go.seoul.noper2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterceptorDTO {
    private String menuId;
    private Boolean canInsert;
    private Boolean canUpdate;
    private Boolean canDelete;
    private Boolean canSelect;
}
