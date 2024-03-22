package kr.go.seoul.noper2.dto;

import lombok.Data;

@Data
public class PermissionDTO {
    String perId;
    String perName;
    String perComment;
    String registId;
    String registTs;
    String updateId;
    String updateTs;
    String initFlag;
}
