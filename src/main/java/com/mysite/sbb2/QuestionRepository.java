package com.mysite.sbb2;

import org.springframework.data.jpa.repository.JpaRepository;

//Question하나당 하나의 리포지 터리를 갖게 돠는 역할>>
public  interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    //Integer : QuestionRepository dml idrj integer이기 땨문에 같데 해준는 것이 암묵적인 약속이다.

//    역할 : 리포지터리는 엔티티에 의해 생성된 데이터베이스 테이블에 접근하는 메서드들(예: findAll, save 등)을
//    사용하기 위한 인터페이스이다. 데이터 처리를 위해서는 테이블에 어떤 값을 넣거나 값을 조회하는
//    등의 CRUD(Create, Read, Update, Delete)가 필요하다. 이 때 이러한 CRUD를 어떻게 처리할지 정의하는 계층이 바로 리포지터리이다.

//    <Question, Integer> 처럼 리포지터리의 대상이 되는 엔티티의 타입(Question)과 해당 엔티티의 PK의 속성 타입(Integer)을
//    지정해야 한다. 이것은 JpaRepository를 생성하기 위한 규칙이다.
}
