package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.repository.CodeRepository;
import kr.go.seoul.noper2.repository.DailyHistoryRepository;
import kr.go.seoul.noper2.repository.UnlicensedBuildingManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DailyHistoryService {
    private final DailyHistoryRepository repository;

    public List<HistoryMain> findHistoryList(HistoryMainDTO dto) {
        return repository.findHistoryList(dto);
    }

    public List<HistoryMainDTO> findHistoryDataExcel(HistoryMainDTO dto) {
        return repository.findHistoryDataExcel(dto);
    }
}
