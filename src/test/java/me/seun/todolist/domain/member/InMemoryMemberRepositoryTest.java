package me.seun.todolist.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryMemberRepositoryTest {

    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new InMemoryMemberRepository();
    }

    @Test
    void save() {
        // given
        String username = "test";
        Member member = new Member(username, "test");

        // when
        memberRepository.save(member);

        // then
        Optional<Member> findMember = memberRepository.findByUsername(username);
        assertThat(findMember.isPresent()).isTrue();
    }

    @Test
    void findByUsername() {
        // given
        String username = "test";
        Member member = new Member(username, "test");
        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findByUsername(username);

        // then
        assertThat(findMember.isPresent()).isTrue();
    }

    @Test
    void findByUsernameFail() {
        // given
        String username = "test";

        // when
        Optional<Member> findMember = memberRepository.findByUsername(username);

        // then
        assertThat(findMember.isEmpty()).isTrue();
    }
}