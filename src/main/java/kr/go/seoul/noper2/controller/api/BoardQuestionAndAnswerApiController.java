package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.domain.QaBbs;
import kr.go.seoul.noper2.dto.QuestionAndAnswerDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.BoardQuestionAndAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/questionAndAnswer")
@RestController
public class BoardQuestionAndAnswerApiController {
    private final BoardQuestionAndAnswerService boardQuestionAndAnswerService;
/*
    @PostMapping("/create")
    public ResponseEntity<Void> createBoard(@ModelAttribute BoardDTO boardDTO) {
        boardService.save(boardDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
*/
@PostMapping("/search")
public HashMap<String, Object> qaSearch(@AuthenticationPrincipal UserAuthentication auth, @RequestBody QuestionAndAnswerDTO qaDto) throws IOException {
    try {
        if ("my".equals(qaDto.getAllMyChk())) {
            qaDto.setUserId(auth.getUserId());
        }

        String cdId = "G0001";
        qaDto.setUserId(auth == null ? "@" : auth.getUserId());
        qaDto.setAdmin(auth != null && auth.getIsAdmin());
        List<QaBbs> qaTypeList = boardQuestionAndAnswerService.findQaTypeByCdId(cdId);

        List<QuestionAndAnswerDTO> findQaList = boardQuestionAndAnswerService.findQaListByQaDto(qaDto);
        // 조회 count 값 보류
        HashMap<String, Object> map = new HashMap<>();
        int totCnt = findQaList.size();
        map.put("qaTypeList", qaTypeList);
        map.put("findQaList", findQaList);
        map.put("totCnt", totCnt);
        return map;
    } catch (IOException e) {
        throw e;
    } catch (Exception e) {
        throw new IOException("QA 검색 중 오류 발생", e);
    }
}


    @PostMapping("/qaTypeList")
    public HashMap<String, Object> qaTypeList(@RequestBody QuestionAndAnswerDTO qaDto) {

        String cdId = "G0001";

        List<QaBbs> qaTypeList = boardQuestionAndAnswerService.findQaTypeByCdId(cdId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("qaTypeList", qaTypeList);
        return map;
    }

    @PostMapping("/save")
    public long saveQa(@AuthenticationPrincipal UserAuthentication auth, @RequestBody QuestionAndAnswerDTO qaDto) {
        //세션에 저장된 권한 값 가져와서 if문 조회 후 리턴
        qaDto.setUserId(auth.getUserId());
        long result = boardQuestionAndAnswerService.saveQaWrite(qaDto);

        return qaDto.getQaSeq();
    }

    @PostMapping("/modify")
    public int modifyQa(@AuthenticationPrincipal UserAuthentication auth, @RequestBody QuestionAndAnswerDTO qaDto) {
        //세션에 저장된 권한 값 가져와서 if문 조회 후 리턴
        qaDto.setUserId(auth.getUserId());
        int result = boardQuestionAndAnswerService.modifyQaWrite(qaDto);

        return result;
    }

    @PostMapping("/delete")
    public int deleteQa(@RequestBody QuestionAndAnswerDTO qaDto) {
        //세션에 저장된 권한 값 가져와서 if문 조회 후 리턴
        int result = boardQuestionAndAnswerService.deleteQa(qaDto);
        return result;
    }

    @PostMapping("/deleteList")
    public int deleteQaList(@RequestBody List<QuestionAndAnswerDTO> qaDtoList) {
        //세션에 저장된 권한 값 가져와서 if문 조회 후 리턴
        int result = boardQuestionAndAnswerService.deleteQaList(qaDtoList);
        return result;
    }

    // 답변 등록
    @PostMapping("/saveAnswer")
    public int saveAnswer(@AuthenticationPrincipal UserAuthentication auth, @RequestBody QuestionAndAnswerDTO qaDto) {
        //세션에 저장된 권한 값 가져와서 if문 조회 후 리턴
        qaDto.setUserId(auth.getUserId());
        return boardQuestionAndAnswerService.saveAnswer(qaDto);
    }
}
