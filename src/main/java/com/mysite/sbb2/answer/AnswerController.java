package com.mysite.sbb2.answer;

import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.question.QuestionService;
import com.mysite.sbb2.user.SiteUser;
import com.mysite.sbb2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult){

        Question question = questionService.getQuestion(id);
        //만약에 들어온 데이터에 문제가 생겼다면
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail"; //주소는 그대로 인데 이파일을 실행한다는 의미이다,
        }
        SiteUser author = userService.getUser(2); // 임시

        answerService.create(question, answerForm.getContent(), author);
        return "redirect:/question/detail/%d".formatted(id);
    }





}
