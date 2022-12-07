package me.seun.todolist.domain.auth;

import me.seun.todolist.domain.exception.LoginFailedException;
import me.seun.todolist.domain.member.Member;
import me.seun.todolist.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    LoginService loginService;

    @Test
    void loginSuccess() {
        // given
        String username = "tester1";
        String password = "1234";
        Member member = new Member(username, password);
        given(memberRepository.findByUsername(username))
                .willReturn(Optional.of(member));

        // when & then
        assertThatCode(() -> loginService.login(username, password))
                .doesNotThrowAnyException();
    }

    @Test
    void loginFailUsernameNotFound() {
        // given
        String username = "invalid";
        given(memberRepository.findByUsername(username))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> loginService.login(username, "1234"))
                .isExactlyInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailPasswordDoesNotMatch() {
        // given
        String username = "tester1";
        String password = "1234";
        Member member = new Member(username, password);
        given(memberRepository.findByUsername(username))
                .willReturn(Optional.of(member));

        // when & then
        assertThatThrownBy(() -> loginService.login(username,  "mismatch"))
                .isExactlyInstanceOf(LoginFailedException.class);
    }
}