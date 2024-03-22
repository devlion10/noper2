package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.QaBbs;
import kr.go.seoul.noper2.dto.QuestionAndAnswerDTO;
import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface BoardQuestionAndAnswerRepository {
    /*
    List<QaBbs> findQaListByQaDto(QaBbs qaBbs);
    */
    //문의유형 selectBox 조회
    List<QaBbs> findQaTypeByCdId(String cdId);

    //Q&A List 조회
    List<QuestionAndAnswerDTO> findQaListByQaDto(QuestionAndAnswerDTO qaDto);

    //Q&A 글 등록
    long saveQaWrite(QuestionAndAnswerDTO qaDto);

    QuestionAndAnswerDTO findQaDetailByQaSeq(int qaSeq);

    //Q&A 글 수정
    int modifyQaWrite(QuestionAndAnswerDTO qaDto);
    void increaseQAViews(@Param("qaSeq") int noticeSeq);

    //Q&A 글 삭제
    int deleteQa(QuestionAndAnswerDTO qaDto);

    //Q&A 답변 등록
    int saveAnswer(QuestionAndAnswerDTO qaDto);

    Object findAnswerDetailByQaSeq(int qaSeq);

    int deleteQaFile(QuestionAndAnswerDTO qaDto);
}
