package kr.go.seoul.noper2.global.config;

import kr.go.seoul.noper2.global.Interceptor.CustomInterceptor;
import kr.go.seoul.noper2.service.InterceptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    @Bean
    public CustomInterceptor customInterceptor() {
        return new CustomInterceptor(applicationContext.getBean("interceptorService", InterceptorService.class));
    }
//    @Bean(name = "urlService")
//    public InterceptorDTO urlService() {
//        if (RequestContextHolder.getRequestAttributes() == null) {
//            return null;
//        }
//        return (InterceptorDTO) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//                .getRequest().getAttribute("permission");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/images/**",
                        "/bootstrap/**",
                        "/fontawesome/**",
                        "/chart-js/**",
                        "/dropzone/**",
                        "/jquery/**",
                        "/sweetAlert/**",
                        "/summernote/**",
                        "/moment-js/**",
                        "/jstree/**",
                        "/ag-grid/**",
                        "/js/**",
                        "/css/**"
                );
    }
}
