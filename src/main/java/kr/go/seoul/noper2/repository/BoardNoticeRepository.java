package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.NoticeBbs;
import kr.go.seoul.noper2.dto.NoticeDTO;
import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardNoticeRepository {

    List<NoticeDTO> findNoticeList(NoticeDTO dto);

    int findNoticeListCount();

    Optional<NoticeBbs> findNoticeDetail(@Param("noticeSeq") int noticeSeq);

    void saveNoticeWrite(NoticeDTO notice);

    NoticeBbs findNoticeDetailBynoticeSeq(int noticeSeq);

    int modifyNoticeWrite(NoticeDTO notice);

   int deleteNotice(Long notice);

    int deleteNoticeList(List<Long> noticeSeqList);

    void increaseNoticeViews(@Param("noticeSeq") int noticeSeq);

    Long findNoticeSeq();

    Integer findNextByNoticeSeq(@Param("noticeSeq") int noticeSeq);

    Integer findBeforeByNoticeSeq(@Param("noticeSeq") int noticeSeq);

    /*int findCntByNoticeSeq(@Param("noticeSeq") int noticeSeq);*/

    /*List<NoticeBbs> getPinnedTopRows();*/
}
