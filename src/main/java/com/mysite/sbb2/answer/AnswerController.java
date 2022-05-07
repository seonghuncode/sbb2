package com.mysite.sbb2.answer;

import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.question.QuestionService;
import com.mysite.sbb2.user.SiteUser;
import com.mysite.sbb2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/answer")
@Controller
public class AnswerController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;


    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")  //로그인을 하지 않으면 기능을 사용할 수 없도록
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal){

        Question question = questionService.getQuestion(id);

        //만약에 들어온 데이터에 문제가 생겼다면
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail"; //주소는 그대로 인데 이파일을 실행한다는 의미이다,
        }
//        SiteUser author = userService.getUser(2); // 임시로 사용했던 것을 pricipal을 통해 사용자를 받아온다
        SiteUser author = userService.getUser(principal.getName());

        answerService.create(question, answerForm.getContent(), author);
        return "redirect:/question/detail/%d".formatted(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") int id, Principal principal) {
        Answer answer = answerService.getAnswer(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        answerForm.setContent(answer.getContent());

        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        answerService.modify(answer, answerForm.getContent());

        return "redirect:/question/detail/%d".formatted(answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(@PathVariable("id") int id, Principal principal) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        answerService.delete(answer);

        return "redirect:/question/detail/%d".formatted(answer.getQuestion().getId());
    }





}
