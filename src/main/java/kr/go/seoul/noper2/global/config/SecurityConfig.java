package kr.go.seoul.noper2.global.config;

import kr.go.seoul.noper2.global.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.security.SecureRandom;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final ApplicationContext applicationContext;
    @Bean
    public static PasswordEncoder passwordEncoder() {
        String salt = "$2a$10$noper";
        return new BCryptPasswordEncoder(10, new SecureRandom(salt.getBytes()));
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new LoginFailureHandler();
    }

    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutHandler();
    }

    public UserDetailsService userDetailsService() {
        return applicationContext.getBean("userDetailsService", CustomUserDetailsService.class);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService(), passwordEncoder());
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    @Bean
    public HttpFirewall httpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.httpFirewall(httpFirewall());
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
            http
                    .csrf().disable()
                    .addFilterBefore(filter, CsrfFilter.class)
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                            .sessionFixation().changeSessionId() // 기존 세션을 유지하고 세션 아이디만 변경
                            .invalidSessionUrl("/") // 유효하지 않는 세션 처리
                            .maximumSessions(1)  // 최대 세션 개수
                            .maxSessionsPreventsLogin(false)
                            .sessionRegistry(sessionRegistry())
                            .expiredUrl("/")
                    )
                    .authorizeRequests(request -> request
                            .antMatchers("/**").permitAll()
                    )
                    .userDetailsService(userDetailsService())
                    .authenticationProvider(authenticationProvider())
                    .formLogin(login -> login
                            .loginPage("/")
                            .usernameParameter("id")
                            .passwordParameter("pw")
                            .loginProcessingUrl("/api/login")
                            .successHandler(successHandler())
                            .failureHandler(failureHandler())
                    )
                    .logout(logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutSuccessHandler(logoutSuccessHandler())
                            .invalidateHttpSession(true)
                    )
                    .headers().frameOptions().sameOrigin();
            return http.build();
    }


    /*
     * resources filter setting
     * 지정한 경로를 filterChain() 에서 걸리지않게 해준다.
     * resources 의 해당하는 모든 경로의 파일은 필터를 거치지 않는다.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain resourceFilter(HttpSecurity http) throws Exception {
        http
                .requestMatchers(matchers -> matchers
                        .antMatchers(
                                "/resources/**",
                                "/error",
                                "/favicon.ico",
                                "/images/**",
                                "/webjars/**",
                                "/css/**",
                                "/js/**"
                        )
                )
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .requestCache().disable()
                .securityContext().disable()
                .sessionManagement().disable();
        return http.build();
    }
}
