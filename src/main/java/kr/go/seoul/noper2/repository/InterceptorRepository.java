package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.dto.InterceptorDTO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

@Mapper
public interface InterceptorRepository {
    Optional<InterceptorDTO> findByPermission(ConcurrentMap<String, Object> menuId);
}
