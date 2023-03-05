package project.shopping;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.shopping.web.login.LoginArgumentResolver;
import project.shopping.Interceptor.LoginCheckInterceptor;

import java.util.List;

/**
 * WebConfig
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //아그먼트리졸버
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }

    //인터셉터
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginCheckInterceptor())
                    .order(1)
                    .addPathPatterns("/**")
                    .excludePathPatterns("/", "/member/form", "/login/form", "/login/logout", "/product/list",

                            "/css/**", "/*.ico", "/error");
    }
}
