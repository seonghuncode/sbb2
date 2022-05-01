DROP DATABASE IF EXISTS sbb_2nd;
CREATE DATABASE sbb_2nd;
USE sbb_2nd;


CREATE TABLE question (
  id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  create_date DATETIME NOT NULL,
  `subject` VARCHAR(200) NOT NULL,
  content TEXT NOT NULL
);

CREATE TABLE answer (
  id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  create_date DATETIME NOT NULL,
  question_id BIGINT UNSIGNED NOT NULL,
  content TEXT NOT NULL
);

#2-11 답변등록, db.sql에서 테스트용 질문 2개 생성

INSERT INTO question
SET create_date = NOW(),
`subject` = 'sbb가 무엇인가요?',
content = 'sbb에 대해서 알고 싶습니다.';

INSERT INTO question
SET create_date = NOW(),
`subject` = '스프링부트 모델 질문입니다.',
content = 'id는 자동으로 생성되나요?';

CREATE TABLE site_user (
  id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username CHAR(100) NOT NULL UNIQUE,
  `password` CHAR(100) NOT NULL,
  email CHAR(100) NOT NULL UNIQUE
);