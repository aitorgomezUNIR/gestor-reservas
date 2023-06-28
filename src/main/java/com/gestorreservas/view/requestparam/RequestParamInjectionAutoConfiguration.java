package com.gestorreservas.view.requestparam;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author jj
 */
@Configuration
public class RequestParamInjectionAutoConfiguration {

    @Bean
    @Order(1)
    public static CheckRequestParamAnnotationUsageAllowedScope checkRequestParamAnnotationUsageAllowedScope() {
        return new CheckRequestParamAnnotationUsageAllowedScope();
    }

    @Bean
    @Order(2)
    public static RequestParamConstructorInjectionPostProcessor requestParamConstructorInjectionPostProcessor() {
        return new RequestParamConstructorInjectionPostProcessor();
    }

    @Bean
    RequestParamFieldInjectionPostProcessor requestParamFieldInjectionPostProcessor(ApplicationContext applicationContext) {
        return new RequestParamFieldInjectionPostProcessor(applicationContext);
    }

    @Bean
    @RequestScope
    RequestParametersExtractor requestParametersExtractor(HttpServletRequest request) {
        return new RequestParametersExtractor(request);
    }

}
