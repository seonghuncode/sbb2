package com.mysite.sbb2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll();
        //시큐리티의 라이브러리를 추가하면 모든 파일의 접근을 위해 로그인을 요총하게 되기 때문에 모든 페이지에 접근할
        // 수 있도록 뚫어 주는역할 을 한다
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //암호화 할때 매번 선언하면 번거럽기 때문에 더 편하게 사용하기 위해서 여기에 따로 만들어 넣는다.
    }

}