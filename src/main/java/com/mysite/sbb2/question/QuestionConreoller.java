package com.mysite.sbb2.question;

import com.mysite.sbb2.answer.AnswerForm;
import com.mysite.sbb2.user.SiteUser;
import com.mysite.sbb2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        page--;
        Page<Question> paging = questionService.getList(page);
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
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

//        SiteUser author = userService.getUser(2); // 임시로 사용했던 것을 현재 사용자를 받아 사용한다
        SiteUser author = userService.getUser(principal.getName());

        questionService.create(questionForm.getSubject(), questionForm.getContent(), author);
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }


}
