package kr.go.seoul.noper2.util.Enum;


import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum Type {
    QNA("qa"),
    NOTICE("notice"),
    LEDGER_LAYOUT("ledgerLayout"),
    LEDGER_GREEN_BELT_LAYOUT("ledgerGreenBeltLayout"),
    LEDGER_SITE_CHK("ledgerSiteChk"),
    VIOLATION_BUILDING("viobldmng"),
    OWNER_CHANGE("ownerChange");

    private final String value;

    Type(String value) {
        this.value = value;
    }
    public static Type getType(String value) {
        return Arrays.stream(Type.values())
                .filter(v -> v.value.equalsIgnoreCase(value))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
    public static String getFileLocation(Type type) {
        String result = null;
        switch (type) {
            case QNA:
            case NOTICE: result = "bbs_tmp"; break;
            case LEDGER_LAYOUT:
            case LEDGER_SITE_CHK: result = "noper_tmp"; break;
            case LEDGER_GREEN_BELT_LAYOUT:result = "limited_tmp"; break;
            case VIOLATION_BUILDING:result = "viobldmng_tmp"; break;
            case OWNER_CHANGE:result = "owner_tmp"; break;
        }
        return result;
    }
}
