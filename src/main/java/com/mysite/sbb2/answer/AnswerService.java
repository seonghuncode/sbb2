package com.mysite.sbb2.answer;

import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setContent(content);
        answer.setAuthor(author);
        answer.setAuthor(author);

        answerRepository.save(answer);
    }

}
