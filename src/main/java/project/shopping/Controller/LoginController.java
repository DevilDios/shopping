package project.shopping.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shopping.domain.Member;
import project.shopping.dto.LoginForm;
import project.shopping.service.LoginService;
import project.shopping.web.login.SessionConst;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
@Controller
public class

LoginController {

    private final LoginService loginService;

    @GetMapping("/form")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }

    //로그인
    @PostMapping("/form")
    public String loginLogin(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, @RequestParam(defaultValue = "/") String redirectURL,
                             HttpServletRequest request) {

        log.info("redirectURL: {}", redirectURL);

        if (bindingResult.hasErrors()) {
            log.info("binding result: {}", bindingResult);
            return "login/loginForm";
        }

        //로그인 id, password 검증
        Member loginMember = loginService.login(loginForm.getUserId(), loginForm.getPassword());
        if (loginMember == null) {
            log.info("login fail = {}", bindingResult);
            bindingResult.reject("loginFail");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        session.setAttribute("mStatus", loginMember.getMemberStatus().name());
        session.setAttribute("userId", loginMember.getUserId());

        return "redirect:" + redirectURL;
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}

