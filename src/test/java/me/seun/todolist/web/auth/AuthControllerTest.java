package me.seun.todolist.web.auth;

import me.seun.todolist.domain.auth.AuthService;
import me.seun.todolist.domain.exception.AuthenticationFailedException;
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

@WebMvcTest
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @Test
    void showLoginForm() throws Exception {
        // given
        String requestUri = "/auth/login";

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
        given(authService.authenticate(username, password))
                .willReturn(new Member(username, password));

        // when & then
        mockMvc.perform(post("/auth/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void processLoginFail() throws Exception {
        // given
        given(authService.authenticate(any(), any()))
                .willThrow(AuthenticationFailedException.class);

        // when & then
        mockMvc.perform(post("/auth/login")
                        .param("username", "tester1")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login?error"));
    }

}