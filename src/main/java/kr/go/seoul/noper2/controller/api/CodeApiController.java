package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.dto.BjdongDTO;
import kr.go.seoul.noper2.dto.CodeDTO;
import kr.go.seoul.noper2.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class CodeApiController {
    private final CodeService codeService;

    @GetMapping("/bjdong/list")
    public List<BjdongDTO> findBjdNameListBySkkCd(String skkCd) {
        return codeService.findBjdNameListBySkkCd(skkCd);
    }

    @GetMapping("/dept/list")
    public List<CodeDTO> findDeptList(String skkCd) {
        return codeService.findDptList(skkCd);
    }

    @GetMapping("/email/list")
    public List<CodeDTO> findEmailList(String skkCd) {return codeService.findEmailListBySkkCd(skkCd);}

}
