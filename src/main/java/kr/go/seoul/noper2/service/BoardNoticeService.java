package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.NoperUserDamo;
import kr.go.seoul.noper2.domain.NoticeBbs;
import kr.go.seoul.noper2.dto.NoticeDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.repository.BoardNoticeRepository;
import kr.go.seoul.noper2.repository.UserRepository;
import kr.go.seoul.noper2.util.TypeCasting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardNoticeService {
    private final BoardNoticeRepository repository;
    private final FileService fileService;
    private final UserRepository userRepository;

    public List<NoticeDTO> findNoticeList(NoticeDTO dto) {
        return repository.findNoticeList(dto);
    }

    /*
    * 공지사항 상세 조회 메서드
    *
    * 쿼리에서 조인하여 정보를 조회하지않고, 도메인 클래스를 활용할 수 있도록 변경
    * 에러의 원인을 알기 쉽게 에러처리를 했습니다.
    * */
    public NoticeDTO getNoticeDetail(int noticeSeq) {
        NoticeBbs noticeBbs = repository.findNoticeDetail(noticeSeq).orElseThrow(NoSuchElementException::new);

        String registId = noticeBbs.getRegistId();
        NoperUserDamo user = userRepository.findUserById(registId).orElseThrow(NoSuchElementException::new);

        NoticeDTO noticeDTO = TypeCasting.changeType(noticeBbs, NoticeDTO.class);
        noticeDTO.setUserNm(user.getUserName());

        return noticeDTO;
    }

    public NoticeDTO saveNoticeWrite(NoticeDTO notice, UserAuthentication auth) {

        String userId = auth.getUserId();
        notice.setUserId(userId);
        repository.saveNoticeWrite(notice);

        // 공지사항 채번
        Long noticeSeq = repository.findNoticeSeq();
        log.debug("\nnoticeSeq: {}", noticeSeq);
        notice.setNoticeSeq(noticeSeq);
        return notice;
    }

    public NoticeBbs findNoticeDetailBynoticeSeq(int noticeSeq) {
        NoticeBbs dto = repository.findNoticeDetailBynoticeSeq(noticeSeq);
        return dto;
    }

    public int modifyNoticeWrite(NoticeDTO notice) {
        return repository.modifyNoticeWrite(notice);
    }

    public int deleteNotice(NoticeDTO notice) {
        return repository.deleteNotice(notice.getNoticeSeq());
    }

    @Transactional
    public int deleteNoticeList(List<Long> noticeSeqList) throws Exception {
        int result = 0;
        if (noticeSeqList != null && !noticeSeqList.isEmpty()) {
            for (Long noticeSeq : noticeSeqList) {
                fileService.deleteAllFile(noticeSeq, "notice");
                result += repository.deleteNotice(noticeSeq);
            }
        }
        return result;
    }

    @Transactional
    public void increaseNoticeViews(int noticeSeq) {
        repository.increaseNoticeViews(noticeSeq);
    }
    public Integer findNextByNoticeSeq(int noticeSeq) {
        return repository.findNextByNoticeSeq(noticeSeq);
    }
    public Integer findBeforeByNoticeSeq(int noticeSeq) {
        return repository.findBeforeByNoticeSeq(noticeSeq);
    }

}
