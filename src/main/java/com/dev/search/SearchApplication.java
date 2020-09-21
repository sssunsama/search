package com.dev.search;

import com.dev.search.entity.User;
import com.dev.search.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@SpringBootApplication
public class SearchApplication {

    private final UserRepository userRepository;

    public SearchApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public ApplicationRunner appRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                // 회원 정보를 미리 세팅
                userRepository.save(new User("test1", "테스트1", encoder.encode("1234"), "USER"));
                userRepository.save(new User("test2", "테스트2", encoder.encode("1234"), "USER"));
                userRepository.save(new User("test3", "테스트3", encoder.encode("1234"), "USER"));
                userRepository.save(new User("test4", "테스트4", encoder.encode("1234"), "USER"));

                log.debug("userInfo1 == {}", userRepository.findById("test1"));
                log.debug("userInfo2 == {}", userRepository.findById("test2"));
                log.debug("userInfo3 == {}", userRepository.findById("test3"));
                log.debug("userInfo4 == {}", userRepository.findById("test4"));
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
