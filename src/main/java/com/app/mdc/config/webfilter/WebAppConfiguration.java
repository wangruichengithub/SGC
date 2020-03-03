package com.app.mdc.config.webfilter;

import com.app.mdc.interceptor.ApiAdminAccessInterceptor;
import com.app.mdc.interceptor.ApiBaseAccessInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    ApiAdminAccessInterceptor ApiAdminAccessFilter() {
        return new ApiAdminAccessInterceptor();
    }
    @Bean
    ApiBaseAccessInterceptor ApiBaseAccessFilter() {
        return new ApiBaseAccessInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /*//通用的拦截
        registry.addInterceptor(ApiBaseAccessFilter()).addPathPatterns("/**");*/
        //拦截登录admin模块
        registry.addInterceptor(ApiAdminAccessFilter()).addPathPatterns("/admin/**");
        registry.addInterceptor(ApiAdminAccessFilter()).addPathPatterns("/common/**");
        registry.addInterceptor(ApiAdminAccessFilter()).addPathPatterns("/mdc/**");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
