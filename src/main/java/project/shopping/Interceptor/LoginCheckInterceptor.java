package project.shopping.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import project.shopping.Util;
import project.shopping.web.login.SessionConst;

/**
 * 인터셉터 핸들
 * 로그인 체크
 */
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("pre interceptor URI = {}", requestURI);
        HttpSession session = request.getSession(false);

        //인터셉터 로그인 체크
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("login error");
            Util.sessionDestruct(request, response);
            //로그인후 있던자리로 돌아가는 부분
            response.sendRedirect("/login/form?redirectURL=" + requestURI);
            return false;
        }
        return true;
    }
}
