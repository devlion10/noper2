package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.domain.NoticeBbs;
import kr.go.seoul.noper2.domain.QaBbs;
import kr.go.seoul.noper2.dto.NoticeDTO;
import kr.go.seoul.noper2.service.BoardNoticeService;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board/notice")
@Controller
public class BoardNoticeController {
    private final BoardNoticeService boardNoticeService;

    @MenuId("NONE")
    @GetMapping("/list")
    public String boardNoticeList(NoticeDTO dto, Model model) {
        List<NoticeDTO> noticeList = boardNoticeService.findNoticeList(dto);
        model.addAttribute("noticeList", noticeList);

        return "pages/board/noticeList";
    }

    @MenuId("NONE")
    @GetMapping("/detail")
    public String boardNoticeDetail(@RequestParam int noticeSeq, Model model) throws IOException {
        try {
            boardNoticeService.increaseNoticeViews(noticeSeq);

            NoticeDTO noticeDTO = boardNoticeService.getNoticeDetail(noticeSeq);
            Integer nextCnt = boardNoticeService.findNextByNoticeSeq(noticeSeq);
            Integer beforeCnt = boardNoticeService.findBeforeByNoticeSeq(noticeSeq);
            model.addAttribute("noticeDetail", noticeDTO);
            model.addAttribute("nextCnt", nextCnt);
            model.addAttribute("beforeCnt", beforeCnt);

            return "pages/board/noticeDetail";
        } catch (NoSuchElementException | UsernameNotFoundException e) {
            throw new IOException("공지사항 상세 조회 중 오류 발생", e);
        }
    }


    @MenuId("NT")
    @GetMapping("/regist")
    public String BoardNoticeRegist(Model model) {
        model.addAttribute("dto", new NoticeDTO());
        return "pages/board/noticeRegist";
    }

    @MenuId("NT")
    @GetMapping("/update")
    public String qaUpdate(Model model, @RequestParam int noticeSeq) {
        NoticeBbs dto = boardNoticeService.findNoticeDetailBynoticeSeq(noticeSeq);
        model.addAttribute("dto", dto);
        return "pages/board/noticeModify";
    }
}
