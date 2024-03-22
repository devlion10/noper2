package kr.go.seoul.noper2.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 타임리프에 Java8이상의 날짜 데이터를 표출할 때 사용.
 */
@Component("dateFormat")
public class DateFormat {
    /**
     * String -> LocalDateTime
     * @param date - 'yyyy-MM-dd HH:mm:ss' 형태의 String
     * @return 'yyyy-MM-dd HH:mm:ss' 형태의 LocalDateTime
     */
    public static LocalDateTime localDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
