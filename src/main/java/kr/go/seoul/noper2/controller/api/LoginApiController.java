package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.dto.LoginDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LoginApiController {
    private final LoginService service;
    @GetMapping("/session-time")
    public ResponseEntity<Integer> getSessionTime() throws IOException, SQLException, NoSuchElementException {
        return new ResponseEntity<>(service.findSessionTime(), HttpStatus.OK);
    }
    @GetMapping("/session-check")
    public ResponseEntity<Boolean> getSessionTime(@AuthenticationPrincipal UserAuthentication auth) throws IOException, SQLException, NoSuchElementException {
        boolean result = auth != null;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/login/session-time/{minute}")
    public ResponseEntity<Void> setSessionTime(@PathVariable String minute) {
        service.saveLoginSessionDefault(minute);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/find-id")
    public ResponseEntity<LoginDTO.FindUserIdResponseDTO> findId(LoginDTO.FindUserIdRequestDTO dto) throws IOException, SQLException, NoSuchElementException {
        return new ResponseEntity<>(service.getUserIdAndRegistTs(dto), HttpStatus.OK);
    }
    @PostMapping("/find-pw")
    public ResponseEntity<LoginDTO.FindUserPwResponseDTO> findPw(LoginDTO.FindUserPwRequestDTO dto) throws IOException, SQLException, NoSuchElementException {
        return new ResponseEntity<>(service.userPwCheck(dto), HttpStatus.OK);
    }
    @PostMapping("/reset-pw")
    public ResponseEntity<Integer> resetPw(LoginDTO.ResetUserPwRequestDTO dto) throws IOException, SQLException, NoSuchElementException {
        try {
            service.resetPassword(dto);
            return ResponseEntity.ok(1); // 성공한 경우 200 OK 반환
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0); // 실패한 경우 500 Internal Server Error 반환
        } catch (NullPointerException e) {
            log.error("resetPw method Null Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
        }
    }

    @GetMapping("/gpki-check")
    public ResponseEntity<Integer> gpkiCheck(@AuthenticationPrincipal UserAuthentication auth) throws IOException, SQLException, NoSuchElementException {
        return ResponseEntity.ok(service.dnCheck(auth.getUserId()));
    }
}
