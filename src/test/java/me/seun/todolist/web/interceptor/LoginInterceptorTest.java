package me.seun.todolist.web.interceptor;


import me.seun.todolist.domain.member.Member;
import me.seun.todolist.web.constants.SessionConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;

class LoginInterceptorTest {

    LoginInterceptor loginInterceptor;

    @BeforeEach
    void beforeEach() {
        loginInterceptor = new LoginInterceptor();
    }

    @Test
    void loginCheck() throws Exception {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute(SessionConstants.LOGIN_MEMBER, new Member("mockUser", "1234"));
        request.setSession(httpSession);

        // when
        boolean result = loginInterceptor.preHandle(request, response, null);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void redirectLoginPage() throws Exception {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        boolean result = loginInterceptor.preHandle(request, response, null);

        // then
        assertThat(result).isFalse();
        assertThat(response.getRedirectedUrl()).isEqualTo("/login");
    }

}