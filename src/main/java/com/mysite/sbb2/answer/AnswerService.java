package com.mysite.sbb2.answer;

import com.mysite.sbb2.question.DataNotFoundException;
import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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


    public Answer getAnswer(int id) {
        Optional<Answer> answer = answerRepository.findById(id);

        if ( answer.isPresent() ) {
            return answer.get();
        }

        throw new DataNotFoundException("answer not found");
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

}
