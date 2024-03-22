package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.domain.NoticeBbs;
import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.service.BoardService;
import kr.go.seoul.noper2.service.DailyHistoryService;
import kr.go.seoul.noper2.service.UnlicensedBuildingManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/unlicensedBuilingManagement/DailyHistory")
@RestController
public class DailyHistoryApiController {
    private final DailyHistoryService dailyHistoryService;

    @PostMapping("/search")
    public HashMap<String, Object> historySearch(Model model, @RequestBody HistoryMainDTO dto){
        List<HistoryMain> findHistoryList = dailyHistoryService.findHistoryList(dto);
        int findHistoryListCount = findHistoryList.size();

        HashMap<String, Object> history = new HashMap<>();
        history.put("findHistoryList", findHistoryList);
        history.put("findHistoryListCount", findHistoryListCount);

        return history;
    }

}

