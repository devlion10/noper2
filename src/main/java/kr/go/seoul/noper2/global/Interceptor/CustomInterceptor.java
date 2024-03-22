package kr.go.seoul.noper2.global.Interceptor;
import kr.go.seoul.noper2.dto.InterceptorDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.InterceptorService;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class CustomInterceptor implements HandlerInterceptor {
    private final InterceptorService service;
    private final ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception, IOException, SQLException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MenuId annotationValue = handlerMethod.getMethodAnnotation(MenuId.class);

            if (annotationValue == null) return true;

            String menuId = annotationValue.value();
            InterceptorDTO data = permissionData(menuId);
            if (menuId.equals("NONE")) {
                data.setCanSelect(true);
            }
            if (Boolean.FALSE.equals(data.getCanSelect())) {
                response.sendRedirect("/access-denied");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception, IOException, SQLException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MenuId annotationValue = handlerMethod.getMethodAnnotation(MenuId.class);

            if (annotationValue == null) return;

            String menuId = annotationValue.value();
            var data = permissionData(menuId);
            if (menuId.equals("NONE")) {
                modelAndView.addObject("userId", map.get("userId"));
                data.setCanSelect(true);
            }

            if (Boolean.FALSE.equals(data.getCanSelect())) {
                response.sendRedirect("/access-denied");
            }
            modelAndView.addObject("auth", data);
        }
    }

    private InterceptorDTO permissionData(String menuId) throws NoSuchElementException, IOException, SQLException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAuthentication user = (principal instanceof UserAuthentication) ? (UserAuthentication) principal : null;
        map.put("menuId", menuId);
        map.put("userId", user != null ?  user.getUserId(): "");
        return service.getMyPermission(map);
    }
}
