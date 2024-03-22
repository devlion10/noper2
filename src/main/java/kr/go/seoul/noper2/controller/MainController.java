package kr.go.seoul.noper2.controller;

import com.gpki.gpkiapi.cert.X509Certificate;
import com.gpki.secureweb.GPKISecureWEBException;
import com.gpki.servlet.GPKIHttpServletRequest;
import com.gpki.servlet.GPKIHttpServletResponse;
import kr.go.seoul.noper2.domain.NoperUserDamo;
import kr.go.seoul.noper2.domain.NoticeBbs;
import kr.go.seoul.noper2.dto.NoticeDTO;
import kr.go.seoul.noper2.dto.QuestionAndAnswerDTO;
import kr.go.seoul.noper2.global.auth.CustomAuthenticationProvider;
import kr.go.seoul.noper2.global.auth.CustomUserDetailsService;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.global.auth.UserContext;
import kr.go.seoul.noper2.service.BoardNoticeService;
import kr.go.seoul.noper2.service.BoardQuestionAndAnswerService;
import kr.go.seoul.noper2.service.LoginService;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    @Value("${noper.geoserver.url}")
    private String geoserverUrl;
    @Value("${spring.profiles.active}")
    private String profile;
    private final LoginService loginService;
    private final CustomUserDetailsService userDetailsService;
    private final BoardNoticeService boardNoticeService;
    private final BoardQuestionAndAnswerService boardQuestionAndAnswerService;

    @PostMapping("/login/gpki")
    public String actionCrtfctLogin(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal UserAuthentication auth, ModelMap model) throws Exception {
        // # GPKI 인증을 처리하는 경우
        // 접속IP
//        String userIp = EgovClntInfo.getClntIP(request);

        // 1. GPKI 인증
        GPKIHttpServletResponse gpkiresponse = null;
        GPKIHttpServletRequest gpkirequest = null;
        String dn = "";
        gpkiresponse = new GPKIHttpServletResponse(response);
        gpkirequest = new GPKIHttpServletRequest(request);
        gpkiresponse.setRequest(gpkirequest);
        X509Certificate cert = null;
        byte[] signData = null;
        byte[] privatekey_random = null;
        String signType = "";
        String queryString = "";
        cert = gpkirequest.getSignerCert();
        dn = cert.getSubjectDN();
        log.debug("\nGPKI DN: {}", dn);
        BigInteger b = cert.getSerialNumber();
        b.toString();
        int message_type =  gpkirequest.getRequestMessageType();
        if( message_type == gpkirequest.ENCRYPTED_SIGNDATA ||
                message_type == gpkirequest.LOGIN_ENVELOP_SIGN_DATA ||
                message_type == gpkirequest.ENVELOP_SIGNDATA ||
                message_type == gpkirequest.SIGNED_DATA){
            signData = gpkirequest.getSignedData();
            if(privatekey_random != null) {
                privatekey_random   = gpkirequest.getSignerRValue();
            }
            signType = gpkirequest.getSignType();
        }
        queryString = gpkirequest.getQueryString();

        // dn 이 null or "" 이 아닐 경우
        if (StringUtils.isNotBlank(dn)) {
            String userId = loginService.getGPKIUser(dn);
            if (StringUtils.isNotBlank(userId != null ? userId : "")) {
                if (auth != null) {
                    // DN 값이 계정에 있고, 로그인이 되어있을 경우
                    return "redirect:/login-success";
                } else {
                    // DN 값이 계정에 있고, 로그인이 안되어있을 경우
                    UserContext userContext = (UserContext) userDetailsService.loadUserByUsername(userId);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userContext.getUserAuth(), null, userContext.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                if (auth != null) {
                    // DN 값이 계정에 없고, 로그인이 되어있을 경우
                    loginService.modifyUserDn(dn, auth.getUserId());
                } else {
                    // DN 값이 계정에 없고, 로그인이 안되어있을 경우
                    return "redirect:/login-fail?error=3";
                }
            }
            return "redirect:/login-success";
        } else {
            return "redirect:/login-fail?error=1";
        }
    }

    @MenuId("NONE")
    @GetMapping("/")
    public String main(@AuthenticationPrincipal UserAuthentication auth, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        String osName = System.getProperty("os.name");
        log.debug(("osName"));
        log.debug((osName));
        if(osName.contains("Windows") || osName.contains("windows") || osName.contains("Mac")){
            log.info("OS Name: {}", osName);
        }else {
            GPKIHttpServletResponse gpkiresponse = null;
            GPKIHttpServletRequest gpkirequest = null;
            gpkiresponse = new GPKIHttpServletResponse(response);
            gpkirequest = new GPKIHttpServletRequest(request);
            gpkiresponse.setRequest(gpkirequest);
            model.addAttribute("challenge", gpkiresponse.getChallenge());
        }

        // 302 요청 체크후 에러 얼럿용 쿠키만 추가
        Cookie[] cookies = request.getCookies();
        Boolean loginFailRedirect = (Boolean) model.asMap().get("loginFailRedirect");
        if (cookies != null && loginFailRedirect != null && loginFailRedirect) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("error")) {
                    // 직접 쿠키 헤더를 설정하여 기존 헤더를 덮어씌우기
                    String cookieHeader = cookie.getName() + "=" + cookie.getValue();
                    // 개행 문자 제거
                    cookieHeader = cookieHeader.replaceAll("\\r|\\n", "");
                    response.setHeader("Set-Cookie", cookieHeader);
                }
            }
        }

        QuestionAndAnswerDTO qaDTO = new QuestionAndAnswerDTO();
        qaDTO.setAnswerState("all");
        qaDTO.setSearchState("all");
        qaDTO.setAllMyChk("all");
        qaDTO.setUserId(auth == null ? "@" : auth.getUserId());
        model.addAttribute("noticeList", boardNoticeService.findNoticeList(new NoticeDTO()));
        model.addAttribute("qaList", boardQuestionAndAnswerService.findQaListByQaDto(qaDTO));
        return "pages/main";
    }


    // 예제 컨트롤러 입니다.
    @GetMapping("/example/{path}")
    public String example(@PathVariable String path) {
        return "pages/template/" + path;
    }

    // 맵
    @MenuId("HH")
    @GetMapping("/getIntroMap")
    public String getIntroMap( HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, NoSuchElementException {
        log.debug(("getIntroMap_____"));

        model.addAttribute("geoserverUrl",geoserverUrl);
        return "pages/mapMain";
    }

    @MenuId("HH")
    @GetMapping("/getAirialImgFile")
    public String getAirialImgFile( HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, NoSuchElementException {
        log.debug(("getAirialImgFile_____"));
        return "pages/mapPopup";
    }

}
