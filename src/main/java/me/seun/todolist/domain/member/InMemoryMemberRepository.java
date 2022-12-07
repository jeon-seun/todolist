package me.seun.todolist.domain.member;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryMemberRepository implements MemberRepository {

    private static ConcurrentMap<Long, Member> store = new ConcurrentHashMap<>();

    private static AtomicLong sequence = new AtomicLong();

    @Override
    public Member save(Member member) {
        long id = sequence.incrementAndGet();
        store.put(id, member);
        member.setId(id);
        return member;
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return store.values().stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst();
    }
}
