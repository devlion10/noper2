package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.*;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface ViolationBuildingRepository {
    void updateViolationBuilding(ViomaMaster viomaMaster);
    void updateResident(ViomaResi viomaResi);
    void updateEnforcementImposition(ViomaEnfoImpo viomaEnfoImpo);
    void updateEnforcementCollection(ViomaEnfoColl viomaEnfoColl);
    List<ViomaMaster> selectViolationBuildingList(SearchParam param);
    List<ViomaStatus> selectViolationBuildingStatus(SearchParam param);
    ViomaMaster selectViolationBuilding(ViomaMaster viomaMaster);
    List<ViomaResi> selectViolationBuildingResi(ViomaMaster viomaMaster);
    List<ViomaEnfoImpo> selectViolationBuildingImpo(ViomaMaster viomaMaster);
    List<ViomaEnfoColl> selectViolationBuildingColl(ViomaMaster viomaMaster);
}
