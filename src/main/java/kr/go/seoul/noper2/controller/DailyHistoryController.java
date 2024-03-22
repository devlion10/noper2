package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.domain.Code;
import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.dto.HistoryMainDTO;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.DailyHistoryService;
import kr.go.seoul.noper2.util.DTOConverter;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/unlcebldmng/DailyHistory")
@Controller
public class DailyHistoryController {
    private final DailyHistoryService dailyHistoryService;
    private final CodeService CodeService;

    @MenuId("PK")
    @GetMapping("/list")
    public String DailyHistoryList(@ModelAttribute HistoryMainDTO dto, Model model) {
        List<Code> codeWorkId = CodeService.findWorkId();
        List<Code> codeGmskk = CodeService.findGmskk();
        //List<HistoryMain> historyList = dailyHistoryService.findHistoryList(dto);

        //model.addAttribute("historyList", historyList);
        model.addAttribute("skk", CodeService.findSkkList());
        model.addAttribute("codeWorkId", codeWorkId);
        model.addAttribute("codeGmskk", codeGmskk);
        return "pages/buildingmanagement/DailyHistory";
    }

    @MenuId("PK")
    @PostMapping(value = "/download/dailyHistoryExcel")
    public String downloadExcel(Model model, @RequestBody HistoryMainDTO dto) {
        try {
            model.addAttribute("list", DTOConverter.convertToExcelList(dailyHistoryService.findHistoryDataExcel(dto)));
            return "excelView";
        } catch (IOException e) {
            log.error("ERROR-01: 파일 처리 중 에러", e);
        } catch (NoSuchElementException e) {
            log.error("ERROR-02: 데이터를 찾을 수 없음", e);
        } catch (Exception e) {
            log.error("ERROR-03: 알 수 없는 오류 발생", e);
        }
        return "errorView";
    }
}
