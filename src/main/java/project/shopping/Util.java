package project.shopping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.shopping.web.login.SessionConst;

import static project.shopping.web.login.SessionConst.LOGIN_MEMBER;

@Slf4j
public class Util {

    //쿠키 세션 비교
    public static void checkLogin(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getValue().equals(request.getSession().getId())) {
                    log.info("correct login.");
                    return;
                }
            }
        }
        log.info("wrong approach.");
        sessionDestruct(request, response);
    }

    //세션 쿠키 삭제
    public static void sessionDestruct(HttpServletRequest request, HttpServletResponse response) {
        crushCookies(request, response);
        request.getSession().invalidate();
        log.info("destruct session.");
    }

    //쿠키 삭제
    private static void crushCookies(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                log.info("crush cookies.");
            }
        }
    }
}
