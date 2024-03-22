package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.QaBbs;
import kr.go.seoul.noper2.dto.QuestionAndAnswerDTO;
import kr.go.seoul.noper2.dto.UserDTO;
import kr.go.seoul.noper2.repository.BoardQuestionAndAnswerRepository;
import kr.go.seoul.noper2.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardQuestionAndAnswerService {
    private final BoardQuestionAndAnswerRepository boardQuestionAndAnswerRepository;
    private final EmailUtil emailUtil;
    private final UserService userService;

    public List<QaBbs> findQaTypeByCdId(String cdId) {
        List<QaBbs> qaTypeList = boardQuestionAndAnswerRepository.findQaTypeByCdId(cdId);
        return qaTypeList;
    }

    public List<QuestionAndAnswerDTO> findQaListByQaDto(QuestionAndAnswerDTO qaDto) throws IOException {
        try {
            List<QuestionAndAnswerDTO> findQaList = boardQuestionAndAnswerRepository.findQaListByQaDto(qaDto);
            return findQaList;
        } catch (NoSuchElementException e) {
            throw new IOException("Q&A 조회 중 오류 발생", e);
        }
    }


    public long saveQaWrite(QuestionAndAnswerDTO qaDto) {
        boardQuestionAndAnswerRepository.saveQaWrite(qaDto);
        String subject = "서울시 기존무허가건축물관리 시스템 Q&A 관련";
        String content = "안녕하세요 서울시 기존무허가건축물관리 시스템입니다.<br>" +
                "<br>" +
                "서울시 기존무허가건축물관리 시스템에 Q&A가 등록되었습니다. <br>" +
                "<a href='http://98.33.11.180:8080/board/questionAndAnswer/list'>Q&A를 확인해주세요.</a><br>";
        emailUtil.sendEmailToMe(subject, content);
        return qaDto.getQaSeq();
    }

    public QuestionAndAnswerDTO findQaDetailByQaSeq(int qaSeq) {
        QuestionAndAnswerDTO dto = boardQuestionAndAnswerRepository.findQaDetailByQaSeq(qaSeq);
        return dto;
    }

    public int modifyQaWrite(QuestionAndAnswerDTO qaDto) {
        return boardQuestionAndAnswerRepository.modifyQaWrite(qaDto);
    }

    public int deleteQa(QuestionAndAnswerDTO qaDto) {
        return boardQuestionAndAnswerRepository.deleteQaFile(qaDto) + boardQuestionAndAnswerRepository.deleteQa(qaDto);
    }

    public int deleteQaList(List<QuestionAndAnswerDTO> qaDtoList) {
        int result = 0;
        if(qaDtoList != null && !qaDtoList.isEmpty()) {
            for(QuestionAndAnswerDTO qaDto : qaDtoList) {
                result += boardQuestionAndAnswerRepository.deleteQaFile(qaDto);
                result += boardQuestionAndAnswerRepository.deleteQa(qaDto);
            }
        }
        return result;
    }

    public int saveAnswer(QuestionAndAnswerDTO qaDto) {
        int result = boardQuestionAndAnswerRepository.saveAnswer(qaDto);
        QuestionAndAnswerDTO qna = boardQuestionAndAnswerRepository.findQaDetailByQaSeq(qaDto.getQaSeq().intValue());
        UserDTO user = userService.selectUser(qna.getRegistId(), "register");
        ArrayList<String> emails = new ArrayList<>();
        emails.add(user.getEMail());
        String subject = "서울시 기존무허가건축물관리 시스템 Q&A 답변안내";
        String content = "안녕하세요 서울시 기존무허가건축물관리 시스템입니다.<br>" +
                "<br>" +
                "서울시 기존무허가건축물관리 시스템에 Q&A의 답변이 등록되었습니다. <br>" +
                "<a href='http://98.33.11.180:8080/board/questionAndAnswer/list'>Q&A를 확인해주세요.</a><br>";
        emailUtil.sendEmail(subject, content, emails);
        return result;
    }

    public Object findAnswerDetailByQaSeq(int qaSeq) {
        return boardQuestionAndAnswerRepository.findAnswerDetailByQaSeq(qaSeq);
    }
    @Transactional
    public void increaseQAViews(int qaSeq) {
        boardQuestionAndAnswerRepository.increaseQAViews(qaSeq);
    }

}
/*
    public Board findBoard(Long id) {
        Optional<Board> board = repository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("Board not found");
        }
    }

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
    }*/
