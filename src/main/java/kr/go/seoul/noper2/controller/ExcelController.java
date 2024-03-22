package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.dto.ExcelDTO;
import kr.go.seoul.noper2.service.ExcelService;
import kr.go.seoul.noper2.util.DTOConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

/**
 * Excel Download Example Controller Class
 * @author sunik
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class ExcelController {
    private final ExcelService excelService;

    /*
     * % 예제 메서드 입니다. %
     * @param model
     * @throws Exception
     */
    @GetMapping(value = "/download/search")
    public String downloadExcelSearch(Model model) throws IOException, NullPointerException {
        model.addAttribute("list", excelService.getExcelToDTO());
        return "htmlpage";
    }

    /*
     * 조회할때 여러줄로 나타내야할때, 사용하시면 됩니다. (기본)
     * @param model
     * @throws Exception
     */
    @GetMapping(value = "/download/excel")
    public String downloadExcel(Model model) throws IOException, NullPointerException {
        model.addAttribute("list", DTOConverter.convertToExcelList(excelService.getExcelToDTO()));
        return "excelView";
    }
    /*
     * DTO를 하나만 넘겨도 될 경우, 사용
     * @param model
     * @throws Exception
     */
    @GetMapping(value = "/download/excel2")
    public String downloadExcel2(Model model) throws IOException {
        ExcelDTO testDTO = new ExcelDTO();
        testDTO.setTest1("value3");
        testDTO.setTest2("value4");
        model.addAttribute("list", DTOConverter.getExcelToDTO(testDTO));
        return "excelView";
    }
}
