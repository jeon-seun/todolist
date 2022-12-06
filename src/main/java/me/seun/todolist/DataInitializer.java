package me.seun.todolist;

import lombok.RequiredArgsConstructor;
import me.seun.todolist.domain.member.Member;
import me.seun.todolist.domain.member.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("dev")
public class DataInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        memberRepository.save(new Member("tester1", "1234"));
    }
}
