package com.mysite.sbb2;

import com.mysite.sbb2.answer.Answer;
import com.mysite.sbb2.answer.AnswerRepository;
import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.question.QuestionRepository;
import com.mysite.sbb2.question.QuestionService;
import com.mysite.sbb2.user.SiteUser;
import com.mysite.sbb2.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Sbb2ApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;

	@Test
	void testCreateUser() {
		for (int i = 1; i <= 10; i++) {
			String username = "user%02d".formatted(i);
			String email = "%s@test.com".formatted(username);
			String password = "1234";
			userService.create(username, email, password);
		}
	}


	@Test
	void testMakeQuestionMany() {
		SiteUser author = userService.getUser(2);
		for (int i = 1; i <= 300; i++) {
			String subject = "테스트 데이터입니다 : [%03d]".formatted(i);
			String content = "내용무 : %d".formatted((int) (Math.random() * 100));
			questionService.create(subject, content, author);
		}
	}


	@Test
	@Transactional
	void testFindRelatedAnswers() {
		// SELECT * FROM question WHERE id = 2;
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		// SELECT * FROM answer WHERE question_id = 2;
		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

	@Test
	void testQuestionFind() {
		Optional<Answer> oa = answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Test
	void testCreateAnswer() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		SiteUser author = userService.getUser(2);


		Answer a1 = new Answer();
		a1.setContent("네 자동으로 생성됩니다.");
		a1.setCreateDate(LocalDateTime.now());
		a1.setQuestion(q);
		a1.setAuthor(author);
		answerRepository.save(a1);
	}
	@Test
	void testRemoveQuestion() {
		assertEquals(2, questionRepository.count());
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertNotNull(q);
		questionRepository.delete(q);
		assertEquals(1, questionRepository.count());

		SiteUser author = userService.getUser(2);

		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		q1.setAuthor(author);
		questionRepository.save(q1);
	}
	@Test
	void testModifyQuestionSubject() {
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		questionRepository.save(q);
	}
	@Test
	void testFindBySubjectLike() {
		List<Question> qList = questionRepository.findBySubjectLike("%sbb%");
		Question q = qList.get(0);
		assertEquals(q.getId(), 1);
	}
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
	@Test
	void testCreateQuestions() {
		SiteUser author = userService.getUser(2);
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		q1.setAuthor(author);
		questionRepository.save(q1);
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		q1.setAuthor(author);
		questionRepository.save(q2);
	}
	@Test
	void contextLoads() {
	}
}