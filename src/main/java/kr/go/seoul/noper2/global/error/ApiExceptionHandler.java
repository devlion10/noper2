package kr.go.seoul.noper2.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class ApiExceptionHandler {
    @ExceptionHandler({Exception.class, TemplateInputException.class})
    public String allExceptionPageHandler(final Exception ex) {
        log.error("Exception -> ", ex);
        return "error/500";
    }
}
