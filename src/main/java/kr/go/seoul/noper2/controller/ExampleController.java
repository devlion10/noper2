package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.ExampleService;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

/**
 * CRUD Example Controller
 * url의 경우, 왠만해서는 대문자사용을 지양해주세요.
 * (/boardList -> /board-list or /board/list)
 * PathVariable에서 쿼리 파라미터로 변경되었습니다.
 *
 * @author sunik
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/example")
@Controller
public class ExampleController {
    private final ExampleService exampleService;
    /*
     * Model을 통해서 데이터를 넘겨도 되지만 Ajax를 사용하는 것을 권장합니다.
     * @param model
     */
    @GetMapping("/list")
    public String exampleList(Model model) {
        return "pages/list";
    }

    /*
     * 파일 업로드 화면 컨트롤러
     * @param model
     * @return fileUpload.html
     */
    @GetMapping("/file-upload")
    public String fileUpload(Model model) {
        return "pages/common/fileUpload";
    }
    /*
     * 예제에서는 DTO를 이용하여 화면에 표출하였습니다.
     * 동일한 클래스 위치에서 많은 DTO가 사용될시, 클래스 하나로 묶어서 사용해주시면 됩니다.
     * @param id
     * @param model
     */
    @GetMapping("/dtl")
    public String exampleDetail(@RequestParam Long id, Model model) throws NoSuchElementException, NullPointerException {
        model.addAttribute("detailInfo", exampleService.findBoard(id));
        return "pages/detail";
    }
    /*
     *
     * @param id
     * @param model
     */
    @GetMapping("/modify")
    public String exampleModify(@RequestParam String id,
                                Model model) {
        return "pages/form";
    }
    /*
     *
     * @param model
     */
    @GetMapping("/save")
    public String exampleSave(Model model) {
        return "pages/form";
    }

    /*
     * 로그인 처리를 안해도 되는 페이질 경우, Null 처리를 해줘야합니다.
     *
     * return 되는 화면에 가시면 화면 처리를 어떻게 하면 되는지 나타나있습니다.
     *
     * @param auth - 일부 유저 정보를 담은 Authentication 객체
     * @return - Auth 페이지
     */
    @MenuId("MV")
    @GetMapping("/session-test")
    public String securitySession(@AuthenticationPrincipal UserAuthentication auth) {
        log.debug("\nid: {}\nname: {}\ndeptCd: {}\nregistId: {}",
                auth.getUserId(), auth.getUserName(), auth.getDeptCd(), auth.getRegistId());
        return "pages/template/Auth";
    }
}
