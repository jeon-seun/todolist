package me.seun.todolist.web.auth;

import me.seun.todolist.domain.auth.LoginService;
import me.seun.todolist.domain.exception.LoginFailedException;
import me.seun.todolist.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoginService loginService;

    @Test
    void showLoginForm() throws Exception {
        // given
        String requestUri = "/login";

        // when & then
        mockMvc.perform(get(requestUri))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login-form"));
    }

    @Test
    void processLogin() throws Exception {
        // given
        String username = "tester1";
        String password = "1234";
        given(loginService.login(username, password))
                .willReturn(new Member(username, password));

        // when & then
        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void processLoginFail() throws Exception {
        // given
        given(loginService.login(any(), any()))
                .willThrow(LoginFailedException.class);

        // when & then
        mockMvc.perform(post("/login")
                        .param("username", "tester1")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

}