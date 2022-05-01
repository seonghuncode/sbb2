package com.mysite.sbb2.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //중복 id를 허용하지 않기 위해서 사용
    private String username;

    private String password;

    @Column(unique = true)
    private String email;


}