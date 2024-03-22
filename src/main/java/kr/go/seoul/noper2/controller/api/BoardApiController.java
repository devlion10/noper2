package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.dto.BoardDTO;
import kr.go.seoul.noper2.dto.SearchParamDTO;
import kr.go.seoul.noper2.dto.UserDTO;
import kr.go.seoul.noper2.service.BoardService;
import kr.go.seoul.noper2.util.TypeCasting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<Void> createBoard(@ModelAttribute BoardDTO boardDTO) {
        boardService.save(boardDTO);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
