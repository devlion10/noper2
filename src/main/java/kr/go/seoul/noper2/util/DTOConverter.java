package kr.go.seoul.noper2.util;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Component("dtoConverter")
public class DTOConverter {
    public static <T> List<LinkedHashMap<String, Object>> convertToExcelList(List<T> objs) throws RuntimeException, IOException {
        HashMap<Integer, String> sortedMap = new HashMap<>();
        List<LinkedHashMap<String, Object>> linkList = new ArrayList<>();
        Object objT = objs.get(0);
        for (Field field : objT.getClass().getDeclaredFields()) {
            ExcelOrder excelOrder = field.getAnnotation(ExcelOrder.class);
            field.setAccessible(true);
            if (excelOrder != null) sortedMap.put(excelOrder.order(), field.getName());
        }
        for (Object obj : objs) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            sortedMap.keySet().stream().sorted().forEach(key->{
                var field = sortedMap.get(key);
                Object value;
                try {
                    Field f = obj.getClass().getDeclaredField(field);
                    ExcelOrder excelOrder = f.getAnnotation(ExcelOrder.class);
                    f.setAccessible(true);
                    value = f.get(obj);
                    map.put(excelOrder.headerName(), value);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            });
            linkList.add(map);
        }
        return linkList;
    }

    public static <T> List<LinkedHashMap<String, Object>> getExcelToDTO(T dto) throws IOException {
        List<T> list = new ArrayList<>();
        list.add(dto);
        return convertToExcelList(list);
    }
}
