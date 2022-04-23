package com.mysite.sbb2.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionConreoller {

    @Autowired
    private QuestionRepository questionRepository;

    @RequestMapping("/question/list")
    public String list(Model model) {
        List<Question> questionList = questionRepository.findAll();
        model.addAttribute("questionList", questionList);

        return "question_list";
    }

}
