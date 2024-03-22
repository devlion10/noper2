package kr.go.seoul.noper2.global.error;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestApiExceptionHandler {
    // @Valid 어노테이션 메시지 반환 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> validationErrorHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errMsg = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errMsg);
    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> allExceptionHandler(final Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "서비스에서 에러가 났습니다.", ex.getMessage()));
    }
}

@Getter
class ErrorResponse {
    private final int status;
    private final String error;
    private final String title;
    private final String message;
    @Builder(access = AccessLevel.PRIVATE)
    ErrorResponse(int status, String error, String title, String message) {
        this.status = status;
        this.error = error;
        this.title = title;
        this.message = message;
    }
    public static ErrorResponse of(final HttpStatus httpStatus, final String title, final String msg) {
        return ErrorResponse.builder()
                .status(500)
                .error(httpStatus.getReasonPhrase())
                .title(title)
                .message(msg)
                .build();
    }
}


