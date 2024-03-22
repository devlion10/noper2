package kr.go.seoul.noper2.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("typeCasting")
public class TypeCasting {
    private final static ModelMapper modelMapper = new ModelMapper();
    private final static ObjectMapper objectMapper = new ObjectMapper();
    public static <T, M> M changeType(T target, Class<M> type) {
        M result = modelMapper.map(target, type);
        return result;
    }

    public static <T, M> List<M> changeTypeList(List<T> target, Class<M> type) {
        List<M> result = new ArrayList<>();
        target.forEach(e -> result.add(modelMapper.map(e, type)));
        return result;
    }

    public static <T> T emptyToHyphen(T target) {
        Map<String, Object> map = objectMapper.convertValue(target, HashMap.class);
        Field[] fields = target.getClass().getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if(field.getType() == String.class && (map.get(fieldName) == null || map.get(fieldName).toString().isEmpty())) map.put(fieldName, "-");
        }
        target = (T) modelMapper.map(map, target.getClass());
        return target;
    }

    public static <T> HashMap<String, Object> dtoToMap(T target) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> map = objectMapper.convertValue(target, HashMap.class);
        map.forEach((k, v) -> {
            if(v != null) { result.put(k, v); }
        });
        return result;
    }
}
