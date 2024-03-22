package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.SearchParam;
import kr.go.seoul.noper2.domain.User;
import kr.go.seoul.noper2.domain.example.Board;
import kr.go.seoul.noper2.dto.BoardDTO;
import kr.go.seoul.noper2.dto.SearchParamDTO;
import kr.go.seoul.noper2.dto.UserDTO;
import kr.go.seoul.noper2.repository.BoardRepository;
import kr.go.seoul.noper2.util.TypeCasting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository repository;

    @Transactional
    public void save(BoardDTO boardDTO) {
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .author("GEON")
                .build();
        repository.save(board);
    }

    public void update(BoardDTO dto) {
        Optional<Board> board = repository.findById(11L);
        if(board.isPresent()){
            //
            //repository.update(board);
        }
    }

    public void createRegisterUser(UserDTO user) {
        repository.createRegisterUser(TypeCasting.changeType(user, User.class));
    }
    public void deleteRegisterUser(String userId) {
        repository.deleteRegisterUser(userId);
    }
    public void allowRegisterUser(Map<String, String> userIds) {
        repository.allowRegisterUser(userIds);
    }
    public void companionRegisterUser(UserDTO user) {
        repository.companionRegisterUser(TypeCasting.changeType(user, User.class));
    }
    public UserDTO registerUserInfo(String userId) {
        return TypeCasting.changeType(repository.registerUserInfo(userId), UserDTO.class);
    }
    public List<UserDTO> registerUserList(SearchParamDTO param) {
        List<User> result;
        List<UserDTO> users = new ArrayList<>();
        param.setCalendarType(param.getCalendarType() == null || param.getCalendarType().equals("all") ? "" : param.getCalendarType());
        param.setStartDate(param.getStartDate() == null || param.getStartDate().isEmpty() ? "2000-01-01" : param.getStartDate());
        param.setEndDate(param.getEndDate() == null || param.getEndDate().isEmpty() ? "2099-12-31" : param.getEndDate());
        param.setSkkCd(param.getSkkCd() == null ? "" : param.getSkkCd());
        param.setBjdCd(param.getBjdCd() == null ? "" : param.getBjdCd());
        param.setJoinType(param.getJoinType() == null || param.getJoinType().equals("all") ? "" : param.getJoinType());
        param.setCompType(param.getCompType() == null ? "" : param.getCompType());
        param.setSearchType(param.getSearchType() == null || param.getSearchType().equals("all") ? "" : param.getSearchType());
        param.setSearchText(param.getSearchText() == null  ? "" : param.getSearchText());
        if(param.getJoinType().equals("NN")) {
            param.setJoinType("N");
            param.setCompType("Y");
        }
        switch (param.getSearchType()) {
            case "userName":
                result = repository.registerUserListByUserName(TypeCasting.changeType(param, SearchParam.class));
                break;
            case "deptName":
                result = repository.registerUserListByDeptName(TypeCasting.changeType(param, SearchParam.class));
                break;
            case "telNo":
                result = repository.registerUserListByTelNo(TypeCasting.changeType(param, SearchParam.class));
                break;
            default:
                result = repository.registerUserList(TypeCasting.changeType(param, SearchParam.class));
                break;
        }
        result.forEach(user -> users.add(TypeCasting.changeType(user, UserDTO.class)));
        return users;
    }
}
