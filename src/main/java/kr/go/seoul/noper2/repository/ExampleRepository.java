package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.NoticeBbs;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.Optional;

/**
 * CRUD Example Repository
 * 단순히 예제이기때문에 쿼리는 없습니다.
 *
 * @author sunik
 */
@Mapper
public interface ExampleRepository {
    Optional<NoticeBbs> findById(long id);
}
