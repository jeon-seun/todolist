package me.seun.todolist.domain.auth;

import lombok.RequiredArgsConstructor;
import me.seun.todolist.domain.exception.AuthenticationFailedException;
import me.seun.todolist.domain.member.Member;
import me.seun.todolist.domain.member.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;

    public Member authenticate(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(AuthenticationFailedException::new);

        if (member.getPassword().equals(password)) {
            return member;
        }

        throw new AuthenticationFailedException();
    }

}
