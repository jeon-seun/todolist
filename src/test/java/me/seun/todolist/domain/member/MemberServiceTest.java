package me.seun.todolist.domain.member;

import me.seun.todolist.domain.exception.UsernameDuplicatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    void signup() {
        // given
        String username = "tester1";
        Member member = new Member(username, "1234");
        Long memberId = 1L;
        given(memberRepository.findByUsername(username))
                .willReturn(Optional.empty());
        given(memberRepository.save(member))
                .will(i -> {
                    Member mem = i.getArgument(0);
                    mem.setId(memberId);
                    return mem;
                });
        // when
        Long savedId = memberService.signup(member);

        // then
        assertThat(savedId).isEqualTo(memberId);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void signupFail() {
        // given
        String username = "tester1";
        Member member = new Member(username, "1234");
        Long memberId = 1L;
        given(memberRepository.findByUsername(username))
                .willReturn(Optional.of(member));

        // when & then
        assertThatThrownBy(() -> memberService.signup(member))
                .isExactlyInstanceOf(UsernameDuplicatedException.class);
    }

}