package com.mysite.sbb2.question;

import com.mysite.sbb2.answer.AnswerForm;
import com.mysite.sbb2.user.SiteUser;
import com.mysite.sbb2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/question")
public class QuestionConreoller {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page,  @RequestParam(value = "kw", defaultValue = "") String kw) {
        page--;
        Page<Question> paging = questionService.getList(page, kw);
        model.addAttribute("kw", kw);
        model.addAttribute("paging", paging);

        return "question_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id); // 질문을 가지고 온다

        model.addAttribute("question", question);

        return "question_detail";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

//        SiteUser author = userService.getUser(2); // 임시로 사용했던 것을 현재 사용자를 받아 사용한다
        SiteUser author = userService.getUser(principal.getName());

        questionService.create(questionForm.getSubject(), questionForm.getContent(), author);
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") int id, Principal principal) {
        Question question = questionService.getQuestion(id);

        if ( !question.getAuthor().getUsername().equals(principal.getName()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());

        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, @PathVariable("id") int id, Principal principal) {
        if( bindingResult.hasErrors() ) {
            return "question_form";
        }

        Question question = questionService.getQuestion(id);

        if ( !question.getAuthor().getUsername().equals(principal.getName()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/detail/%d".formatted(id);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") int id) {
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") int id) {
        Question question = questionService.getQuestion(id);
        SiteUser siteUser = userService.getUser(principal.getName());

        questionService.vote(question, siteUser);

        return "redirect:/question/detail/%d".formatted(id);
    }




}
