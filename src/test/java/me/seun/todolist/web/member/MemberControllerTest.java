package me.seun.todolist.web.member;

import me.seun.todolist.domain.exception.UsernameDuplicatedException;
import me.seun.todolist.domain.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    void showSignupForm() throws Exception {
        // given
        String requestUri = "/members/add";

        // when & then
        mockMvc.perform(get(requestUri))
                .andExpect(status().isOk())
                .andExpect(view().name("/members/signup-form"))
                .andExpect(model().attributeExists("signup"));
    }

    @Test
    void processSignup() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "tester1");
        params.add("password", "1234");
        params.add("passwordCheck", "1234");
        params.add("agree", "true");

        // when & then
        mockMvc.perform(post("/members/add").params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void processSignupValidationFail() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "  ");
        params.add("password", "  ");
        params.add("passwordCheck", "1234!!");
        params.add("agree", "false");

        // when & then
        mockMvc.perform(post("/members/add").params(params))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("signup", "username", "password", "matchedPassword", "agree"))
                .andExpect(view().name("/members/signup-form"));
    }

    @Test
    void processSignupUsernameDuplicate() throws Exception {
        // given
        given(memberService.signup(any()))
                .willThrow(UsernameDuplicatedException.class);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "tester1");
        params.add("password", "1234");
        params.add("passwordCheck", "1234");
        params.add("agree", "true");

        // when & then
        mockMvc.perform(post("/members/add").params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/members/add?error=*"));
    }
}