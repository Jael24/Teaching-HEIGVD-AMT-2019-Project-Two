package ch.heigvd.amt.project.two.auth.api.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Router {
    @Bean
    public FilterRegistrationBean<AuthFilter> authenticationFilter() {
        FilterRegistrationBean<AuthFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new AuthFilter());
        filterRegistrationBean.addUrlPatterns("/users/*");

        return filterRegistrationBean;
    }
}
