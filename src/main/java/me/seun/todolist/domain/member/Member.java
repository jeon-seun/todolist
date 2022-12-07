package me.seun.todolist.domain.member;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {

    private Long id;

    private String username;

    private String password;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
