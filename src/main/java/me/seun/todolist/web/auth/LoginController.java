package me.seun.todolist.web.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.seun.todolist.domain.auth.LoginService;
import me.seun.todolist.domain.exception.LoginFailedException;
import me.seun.todolist.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static me.seun.todolist.web.constants.SessionConstants.LOGIN_MEMBER;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login-form";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(defaultValue = "/") String redirectUri,
                               HttpSession session) {
        Member member = loginService.login(username, password);
        session.setAttribute(LOGIN_MEMBER, member);
        return "redirect:" + redirectUri;
    }

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFail() {
        return "redirect:/login?error";
    }
}
