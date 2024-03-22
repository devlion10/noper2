package kr.go.seoul.noper2.global.auth;

import kr.go.seoul.noper2.domain.NoperUserSession;
import kr.go.seoul.noper2.repository.UserRepository;
import kr.go.seoul.noper2.util.TypeCasting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Security 작업에서 유저 정보를 가져오고 SecurityContextHolder에 담을 세션 클래스를 반환합니다.
 * @Param - 입력한 ID
 * @Return - UserContext
 */
@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        NoperUserSession user = repository.findByUsername(userName).orElse(null);

        if (user == null) throw new UsernameNotFoundException("User not found");

        UserAuthentication userSession = TypeCasting.changeType(user, UserAuthentication.class);
        userSession.setIsAdmin(user.getPerId().startsWith("AD"));

        // 관리자 권한만 시큐리티 어드민 권한을 부여한다.
        String role = user.getPerId().startsWith("AD") ? "ROLE_ADMIN" : "ROLE_USER";
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role));

        return new UserContext(userSession, user.getUserPw(), roles);
    }
}
