package me.seun.todolist.web.member;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import me.seun.todolist.domain.member.Member;

@Setter
@Getter
public class MemberSignup {

    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    @NotBlank
    private String password;

    private String passwordCheck;

    @AssertTrue
    private boolean agree;

    @AssertTrue
    public boolean isMatchedPassword() {
        if (password == null) {
            return false;
        }
        return password.equals(passwordCheck);
    }

    public Member toEntity() {
        return new Member(username, password);
    }
}
