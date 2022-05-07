package com.mysite.sbb2.user;

import com.mysite.sbb2.question.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //암호화를 사용하기 위해 선언해준다

    public SiteUser create(String username, String email, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        //비밀번호는 법적으로 평문으로 저장이 불가 하므로 암호화 하여 저장해야 한다.
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(password));  --> 따로 클래스를 만들어 놓지 않았을 경우 이렇게 사용

        user.setPassword(passwordEncoder.encode(password)); // 클래스를 만들었기 때문에 이렇게 사용 가능
        userRepository.save(user);
        return user;

    }


    public SiteUser getUser(long id) {
        Optional<SiteUser> siteUser = this.userRepository.findById(id);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }


    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }



}
