package me.seun.todolist.domain.auth;

import lombok.RequiredArgsConstructor;
import me.seun.todolist.domain.exception.LoginFailedException;
import me.seun.todolist.domain.member.Member;
import me.seun.todolist.domain.member.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(LoginFailedException::new);

        if (member.getPassword().equals(password)) {
            return member;
        }

        throw new LoginFailedException();
    }

}
