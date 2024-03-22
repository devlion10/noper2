package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.HistoryMain;
import kr.go.seoul.noper2.dto.HistoryMainDTO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface DailyHistoryRepository {

    List<HistoryMain> findHistoryList(HistoryMainDTO dto);

    List<HistoryMainDTO> findHistoryDataExcel(HistoryMainDTO dto);
}
