//package com.moeum.moeum.global.cors;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//@RequiredArgsConstructor
//public class FilterConfig {
//
//    private final CorsConfigurationSource corsConfigurationSource;
//
//    @Bean(name = "corsFilterRegistration")
//    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
//        CorsFilter filter = new CorsFilter(corsConfigurationSource);
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(filter);
//        bean.setOrder(0);
//        return bean;
//    }
//}
