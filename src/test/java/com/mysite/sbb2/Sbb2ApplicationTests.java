package com.mysite.sbb2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Sbb2ApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void testFindBySubjectAndContent() {
		Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(q.getId(), 1);
	}

	@Test
	void testFindBySubject() {
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(q.getId(), 1);
	}

	@Test
	void testFindById() {
		Optional<Question> oq = questionRepository.findById(1);

		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	void testFindAll() {

		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}
	//테스트 함수 만드는 과정
	@Test
	void testCreateQuestions() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);
	}


//		@SpringBootTest 애너테이션은 SbbApplicationTests 클래스가 스프링부트 테스트 클래스임을 의미한다.
//	    그리고 @Autowired 애너테이션은 스프링의 DI 기능으로 questionRepository 객체를 스프링이 자동으로 생성해 준다.
		//실행 방법 : 디비 초기화(복사후 전체 재 실행 ==>디비가 비어있는 상황 f11눌러서 확인) -> 왼쪽 화살표 클릭
		//각가의 테스트 함수 옆의 버튼을 눌러 원하는 기능만 실행 시키기


	@Test //test인것을 알려주는 역할
	void contextLoads() {
	}



}
