package me.seun.todolist.domain.auth;

import me.seun.todolist.domain.exception.AuthenticationFailedException;
import me.seun.todolist.domain.member.Member;
import me.seun.todolist.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    AuthService authService;

    @Test
    void authenticateSuccess() {
        // given
        String username = "tester1";
        String password = "1234";
        Member member = new Member(username, password);
        given(memberRepository.findByUsername(username))
                .willReturn(Optional.of(member));

        // when & then
        assertThatCode(() -> authService.authenticate(username, password))
                .doesNotThrowAnyException();
    }

    @Test
    void authenticateFailUsernameNotFound() {
        // given
        String username = "invalid";
        given(memberRepository.findByUsername(username))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authService.authenticate(username, "1234"))
                .isExactlyInstanceOf(AuthenticationFailedException.class);
    }

    @Test
    void authenticateFailPasswordDoesNotMatch() {
        // given
        String username = "tester1";
        String password = "1234";
        Member member = new Member(username, password);
        given(memberRepository.findByUsername(username))
                .willReturn(Optional.of(member));

        // when & then
        assertThatThrownBy(() -> authService.authenticate(username,  "mismatch"))
                .isExactlyInstanceOf(AuthenticationFailedException.class);
    }
}