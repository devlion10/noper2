package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.NoticeBbs;
import kr.go.seoul.noper2.domain.SearchParam;
import kr.go.seoul.noper2.domain.User;
import kr.go.seoul.noper2.domain.example.Board;
import kr.go.seoul.noper2.dto.TestBoardDTO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface BoardRepository {

    List<Board> findAll();

    Optional<Board> findById(Long id);
    Optional<TestBoardDTO> findTestById(Long id);
    void save(NoticeBbs board);
    void save(Board board);
    void createRegisterUser(User user);
    void deleteRegisterUser(String userId);
    void allowRegisterUser(Map<String, String> users);
    void companionRegisterUser(User userId);
    User registerUserInfo(String userId);
    List<User> registerUserList(SearchParam param);
    List<User> registerUserListByUserName(SearchParam param);
    List<User> registerUserListByDeptName(SearchParam param);
    List<User> registerUserListByTelNo(SearchParam param);
}
