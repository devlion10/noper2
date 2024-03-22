package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.dto.ExcelDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Excel Download Example Service Class
 * @author sunik
 *
 * ExcelController -> ExcelService
 */
@Slf4j
@Service
public class ExcelService {

    public List<HashMap<String , Object>> getExcel() throws IOException, NullPointerException {
        // 별도 제작
        HashMap<String, Object> h = new HashMap<>();
        h.put("AAA", 223);
        h.put("BBB", "asdfasdf");

        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(h);
        return list;
    }

    public List<ExcelDTO> getExcelToDTO() throws IOException, NullPointerException {
        ExcelDTO testDTO = new ExcelDTO();
        testDTO.setTest1("value1");
        testDTO.setTest2("value2");

        List<ExcelDTO> list = new ArrayList<>();
        list.add(testDTO);
        list.add(testDTO);
        list.add(testDTO);
        return list;
    }
}
