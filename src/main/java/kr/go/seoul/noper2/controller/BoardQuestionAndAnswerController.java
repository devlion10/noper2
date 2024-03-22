package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.domain.QaBbs;
import kr.go.seoul.noper2.dto.QuestionAndAnswerDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.BoardQuestionAndAnswerService;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board/questionAndAnswer")
@Controller
public class BoardQuestionAndAnswerController {
    private final BoardQuestionAndAnswerService boardQuestionAndAnswerService;

    @MenuId("NONE")
    @GetMapping("/list")
    public String qaList(Model model) {
        return "pages/board/QnA";
    }

    @MenuId("QA")
    @GetMapping("/admin/list")
    public String adminQaList(Model model) {
        return "pages/board/QnA";
    }

    @MenuId("QA")
    @GetMapping("/add")
    public String qaAdd(Model model) {
        model.addAttribute("dto", new QuestionAndAnswerDTO());
        return "pages/board/QnAAdd";
    }

    @MenuId("NONE")
    @GetMapping("/detail")
    public String qaDetail(Model model, @RequestParam int qaSeq) {
        boardQuestionAndAnswerService.increaseQAViews(qaSeq);
        QuestionAndAnswerDTO dto = boardQuestionAndAnswerService.findQaDetailByQaSeq(qaSeq);
        model.addAttribute("dto", dto);
        model.addAttribute("answerDto", boardQuestionAndAnswerService.findAnswerDetailByQaSeq(qaSeq));
        return "pages/board/QnADetail";
    }

    @MenuId("QA")
    @GetMapping("/update")
    public String qaUpdate(Model model, @RequestParam int qaSeq) {
        QuestionAndAnswerDTO dto = boardQuestionAndAnswerService.findQaDetailByQaSeq(qaSeq);
        model.addAttribute("dto", dto);
        return "pages/board/QnAAdd";
    }

}
