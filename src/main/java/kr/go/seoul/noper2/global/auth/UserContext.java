package kr.go.seoul.noper2.global.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Security Login Context Class
 * 해당 Context를 사용하기위해 user객체를 멤버변수로 선언함.
 */
@Getter
public class UserContext extends User {
    private final UserAuthentication userAuth;

    public UserContext(UserAuthentication user, String userPw, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserId(), userPw, authorities);
        this.userAuth = user;
    }
}