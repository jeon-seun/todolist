package me.seun.todolist.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import static me.seun.todolist.web.constants.SessionConstants.LOGIN_MEMBER;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(LOGIN_MEMBER) == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
