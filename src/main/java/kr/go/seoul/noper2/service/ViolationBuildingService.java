package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.*;
import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.repository.ViolationBuildingRepository;
import kr.go.seoul.noper2.util.TypeCasting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViolationBuildingService {
    private final ViolationBuildingRepository repository;

    public List<ViomaMasterDTO> selectViolationBuildingList(SearchParamDTO param) {
        param.setGmskk(param.getGmskk() == null ? "" : param.getGmskk());
        param.setSkkCd(param.getSkkCd() == null ? "" : param.getSkkCd());
        param.setViolationType(param.getViolationType() == null ? "" : param.getViolationType());
        param.setUseType(param.getUseType() == null ? "" : param.getUseType());
        param.setGmcsah(param.getGmcsah() == null ? "" : param.getGmcsah());
        param.setAddress(param.getAddress() == null ? "" : param.getAddress());
        param.setNewAddress(param.getNewAddress() == null ? "" : param.getNewAddress());
        param.setStartDate(param.getStartDate() == null || param.getStartDate().isEmpty() ? "2000-01-01" : param.getStartDate());
        param.setEndDate(param.getEndDate() == null || param.getEndDate().isEmpty() ? "2099-12-31" : param.getEndDate());

        return TypeCasting.changeTypeList(repository.selectViolationBuildingList(TypeCasting.changeType(param, SearchParam.class)), ViomaMasterDTO.class);
    }

    public List<ViomaStatusDTO> selectViolationBuildingStatus(SearchParamDTO param) {
        param.setSkkCd(param.getSkkCd() == null ? "" : param.getSkkCd());
        param.setViolationType(param.getViolationType() == null ? "" : param.getViolationType());
        param.setUseType(param.getUseType() == null ? "" : param.getUseType());
        param.setStartDate(param.getStartDate() == null || param.getStartDate().isEmpty() ? "2000-01-01" : param.getStartDate());
        param.setEndDate(param.getEndDate() == null || param.getEndDate().isEmpty() ? "2099-12-31" : param.getEndDate());

        return TypeCasting.changeTypeList(repository.selectViolationBuildingStatus(TypeCasting.changeType(param, SearchParam.class)), ViomaStatusDTO.class);
    }

    public String updateViolationBuilding(ViomaFullDTO viomaFull) {
        if(viomaFull.getRegistId() == null || viomaFull.getRegistId().isEmpty()) viomaFull.setRegistId(viomaFull.getUpdateId());
        ViomaMaster viomaMaster = TypeCasting.changeType(viomaFull, ViomaMaster.class);
        repository.updateViolationBuilding(viomaMaster);
        if(viomaFull.getResiName() != null && !viomaFull.getResiName().isBlank()) {
            String[] resiNames = viomaFull.getResiName().split(",");
            if(resiNames.length > 1) {
                String[] resiSeqs = viomaFull.getResiSeq() != null ? viomaFull.getResiSeq().split(",") : null;
                String[] resiForigbs = viomaFull.getResiForigb().split(",");
                String[] resiBirths = viomaFull.getResiBirth().split(",");
                String[] resiTelnos = viomaFull.getResiTelno().split(",");
                String[] resiNewAddresses = viomaFull.getResiNewAddress().split(",");
                String[] resiAddresses = viomaFull.getResiAddress().split(",");
                for(int i = 0; i < resiNames.length; i++) {
                    ViomaResi viomaResi;
                    if ((resiSeqs != null) && i < resiSeqs.length) {
                        viomaResi = ViomaResi.builder().
                                resiSeq(Long.parseLong(resiSeqs[i])).
                                gmskk(viomaMaster.getGmskk()).
                                resiName(resiNames[i]).
                                resiForigb(resiForigbs[i]).
                                resiBirth(resiBirths[i]).
                                resiTelno(resiTelnos[i]).
                                resiNewAddress(resiNewAddresses[i]).
                                resiAddress(resiAddresses[i]).build();
                    } else {
                        viomaResi = ViomaResi.builder().
                                gmskk(viomaMaster.getGmskk()).
                                resiName(resiNames[i]).
                                resiForigb(resiForigbs[i]).
                                resiBirth(resiBirths[i]).
                                resiTelno(resiTelnos[i]).
                                resiNewAddress(resiNewAddresses[i]).
                                resiAddress(resiAddresses[i]).build();
                    }

                    repository.updateResident(viomaResi);
                }
            } else {
                repository.updateResident(TypeCasting.changeType(viomaFull, ViomaResi.class));
            }
        }
        if(viomaFull.getImpoDate() != null && !viomaFull.getImpoDate().isBlank()) {
            String[] impoDates = viomaFull.getImpoDate().split(",");
            if(impoDates.length > 1) {
                String[] impoSeqs = viomaFull.getImpoSeq() != null ? viomaFull.getImpoSeq().split(",") : null;
                String[] impoAmounts = viomaFull.getImpoAmount().split(",");
                for(int i = 0; i < impoDates.length; i++) {
                    ViomaEnfoImpo viomaEnfoImpo;
                    if((impoSeqs != null) && i < impoSeqs.length) {
                        viomaEnfoImpo = ViomaEnfoImpo.builder().
                                impoSeq(Long.parseLong(impoSeqs[i])).
                                gmskk(viomaMaster.getGmskk()).
                                impoDate(impoDates[i]).
                                impoAmount(Long.parseLong(impoAmounts[i])).build();
                    } else {
                        viomaEnfoImpo = ViomaEnfoImpo.builder().
                                gmskk(viomaMaster.getGmskk()).
                                impoDate(impoDates[i]).
                                impoAmount(Long.parseLong(impoAmounts[i])).build();
                    }
                    repository.updateEnforcementImposition(viomaEnfoImpo);
                }
            } else if(impoDates.length > 0) {
                repository.updateEnforcementImposition(TypeCasting.changeType(viomaFull, ViomaEnfoImpo.class));
            }
        }
        if(viomaFull.getCollDate() != null && !viomaFull.getCollDate().isBlank()) {
            String[] collDates = viomaFull.getCollDate().split(",");
            if(collDates.length > 1) {
                String[] collSeqs = viomaFull.getCollSeq() != null ? viomaFull.getCollSeq().split(",") : null;
                String[] collAmounts = viomaFull.getCollAmount().split(",");
                for(int i = 0; i < collDates.length; i++) {
                    ViomaEnfoColl viomaEnfoColl;
                    if((collSeqs != null) && i < collSeqs.length) {
                        viomaEnfoColl = ViomaEnfoColl.builder().
                                collSeq(Long.parseLong(collSeqs[i])).
                                gmskk(viomaMaster.getGmskk()).
                                collDate(collDates[i]).
                                collAmount(Long.parseLong(collAmounts[i])).build();
                    } else {
                        viomaEnfoColl = ViomaEnfoColl.builder().
                                gmskk(viomaMaster.getGmskk()).
                                collDate(collDates[i]).
                                collAmount(Long.parseLong(collAmounts[i])).build();
                    }
                    repository.updateEnforcementCollection(viomaEnfoColl);
                }
            } else if(collDates.length > 0) {
                repository.updateEnforcementCollection(TypeCasting.changeType(viomaFull, ViomaEnfoColl.class));
            }
        }

        return viomaMaster.getGmskk();
    }

    public ViomaMasterDTO selectViolationBuilding(ViomaMasterDTO viomaMaster) {
        return TypeCasting.changeType(repository.selectViolationBuilding(TypeCasting.changeType(viomaMaster, ViomaMaster.class)), ViomaMasterDTO.class);
    }

    public List<ViomaResiDTO> selectViolationBuildingResi(ViomaMasterDTO viomaMaster) {
        return TypeCasting.changeTypeList(repository.selectViolationBuildingResi(TypeCasting.changeType(viomaMaster, ViomaMaster.class)), ViomaResiDTO.class);
    }

    public List<ViomaEnfoImpoDTO> selectViolationBuildingImpo(ViomaMasterDTO viomaMaster) {
        return TypeCasting.changeTypeList(repository.selectViolationBuildingImpo(TypeCasting.changeType(viomaMaster, ViomaMaster.class)), ViomaEnfoImpoDTO.class);
    }

    public List<ViomaEnfoCollDTO> selectViolationBuildingColl(ViomaMasterDTO viomaMaster) {
        return TypeCasting.changeTypeList(repository.selectViolationBuildingColl(TypeCasting.changeType(viomaMaster, ViomaMaster.class)), ViomaEnfoCollDTO.class);
    }
}
