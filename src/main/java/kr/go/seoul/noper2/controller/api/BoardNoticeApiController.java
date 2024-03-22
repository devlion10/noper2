package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.dto.NoticeDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.BoardNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
public class BoardNoticeApiController {
    private final BoardNoticeService boardNoticeService;

    @PostMapping("/notice/search")
    public Map<String, Object> noticeSearch(@RequestBody NoticeDTO dto){
        List<NoticeDTO> findNoticeList = boardNoticeService.findNoticeList(dto);
        int noticeListCount = findNoticeList.size();

        Map<String, Object> notice = new HashMap<>();
        notice.put("findNoticeList", findNoticeList);
        notice.put("findNoticeListCount", noticeListCount);
        return notice;
    }

    @PostMapping("/notice/save")
    public NoticeDTO saveNoticeWrite(@RequestBody NoticeDTO notice, @AuthenticationPrincipal UserAuthentication auth) {
        return boardNoticeService.saveNoticeWrite(notice, auth);
    }

    @PostMapping("/notice/modify")
    public int modifyNotice(@RequestBody NoticeDTO notice, @AuthenticationPrincipal UserAuthentication auth) {
        notice.setUserId(auth.getUserId());
        return boardNoticeService.modifyNoticeWrite(notice);
    }

    @PostMapping("/notice/delete")
    public int deleteQa(@RequestBody NoticeDTO notice) {
        return boardNoticeService.deleteNotice(notice);
    }

    @PostMapping("/notice/deleteList")
    public int deleteNoticeList(@RequestBody List<Long> noticeDtoList) throws IOException {
        try {
            return boardNoticeService.deleteNoticeList(noticeDtoList);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("공지사항 삭제 중 오류 발생", e);
        }
    }



}
