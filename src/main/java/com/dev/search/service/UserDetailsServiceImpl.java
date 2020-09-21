package com.dev.search.service;

import com.dev.search.entity.User;
import com.dev.search.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Spring Security 필수 메소드 구현
     *
     * @param id 아이디
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public User loadUserByUsername(String id) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException((id)));
    }
}
