package com.mysite.sbb2.answer;

import org.springframework.data.jpa.repository.JpaRepository;


//Answer하나당 하나의 리포지 터리를 갖게 돠는 역할>>
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

//    interface의 역할
//    QuestionRepository, AnswerRepository를 이용하여 question, answer 테이블에 데이터를 저장하거나 조회할 수 있다.
}
