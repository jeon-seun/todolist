package me.seun.todolist.domain.member;

import lombok.RequiredArgsConstructor;
import me.seun.todolist.domain.exception.UsernameDuplicatedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long signup(Member member) {

        validateUsernameDuplicate(member.getUsername());

        memberRepository.save(member);

        return member.getId();
    }

    private void validateUsernameDuplicate(String username) {
        Optional<Member> findMember = memberRepository.findByUsername(username);
        if (findMember.isPresent()) {
            throw new UsernameDuplicatedException();
        }
    }

}
