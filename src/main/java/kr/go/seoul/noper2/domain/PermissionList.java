package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PermissionList {
    String perId;
    String menuId;
}
