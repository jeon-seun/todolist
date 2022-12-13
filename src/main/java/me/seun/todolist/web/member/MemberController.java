package me.seun.todolist.web.member;

import lombok.RequiredArgsConstructor;
import me.seun.todolist.domain.exception.UsernameDuplicatedException;
import me.seun.todolist.domain.member.MemberService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    private final MessageSource messageSource;

    @GetMapping("/members/add")
    public String showSignupForm(Model model) {
        model.addAttribute("signup", new MemberSignup());
        return "/members/signup-form";
    }

    @PostMapping("/members/add")
    public String processSignup(@ModelAttribute("signup") @Validated MemberSignup signup,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/members/signup-form";
        }

        memberService.signup(signup.toEntity());
        return "redirect:/login";
    }

    @ExceptionHandler(UsernameDuplicatedException.class)
    public String handleUsernameDuplicate(RedirectAttributes redirectAttributes, Locale locale) {
        redirectAttributes.addAttribute(
                "error",
                messageSource.getMessage("Duplicate.Username", null, locale)
        );
        return "redirect:/members/add";
    }
}
