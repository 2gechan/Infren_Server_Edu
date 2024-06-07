package com.group.libraryapp.config;

import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/*
설정 파일로 빈 주입해주기
 */
//@Configuration
public class UserConfiguration {

    /*
     UserRepository 클래스에서 @Repository 어노테이션을 사용하지 않고
     Configuration 클래스에서 @Configuration 어노테이션을 설정해주고
     UserRepository에서 필요한 의존성과 UserRepository를 스프링 컨테이너에 등록한다.
     */
//    @Bean
    public UserJdbcRepository userRepository(JdbcTemplate jdbcTemplate) {
        return new UserJdbcRepository(jdbcTemplate);
    }

}
