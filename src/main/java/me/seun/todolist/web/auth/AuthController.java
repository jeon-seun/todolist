package me.seun.todolist.web.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.seun.todolist.domain.auth.AuthService;
import me.seun.todolist.domain.exception.AuthenticationFailedException;
import me.seun.todolist.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/login")
    public String showLoginForm() {
        return "auth/login-form";
    }

    @PostMapping("/auth/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(defaultValue = "/") String redirectUri,
                               HttpSession session) {
        Member member = authService.authenticate(username, password);
        session.setAttribute("LOGIN_MEMBER", member);
        return "redirect:" + redirectUri;
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public String handleAuthenticationFail() {
        return "redirect:/auth/login?error";
    }
}
