package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Permission {
    String perId;
    String perName;
    String perComment;
    String registId;
    String registTs;
    String updateId;
    String updateTs;
    String initFlag;
}
