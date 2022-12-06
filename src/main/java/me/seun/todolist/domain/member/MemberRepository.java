package me.seun.todolist.domain.member;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findByUsername(String username);

}
