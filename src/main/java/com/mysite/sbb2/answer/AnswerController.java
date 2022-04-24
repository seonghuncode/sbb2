package com.mysite.sbb2.answer;

import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@Controller
public class AnswerController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @PostMapping(value = "/create/{id}")
    public String createAnswer (Model model, @PathVariable("id") Integer id, @RequestParam String content){
// @RequestParam String content => 이전 폼에서 값이 들어온다


        Question question = this.questionService.getQuestion(id);
        answerService.create(question, content);   //이질문(왼쪽)에 이답변을(오른쪽) 달겠다는 의미
         model.addAttribute("question", question);
        return "redirect:/question/detail/%d".formatted(id);

    }

}
