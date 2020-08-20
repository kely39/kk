package com.kk.d.framework.web;

import com.google.common.base.Predicates;
import com.kk.d.framework.web.filter.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * FrameworkWeb自动配置类
 *
 * @author yangqh
 * @date 2019/12/26
 **/
@Slf4j
@Configuration
@Import({FrameworkWebAutoConfiguration.CorsAutoConfiguration.class,
        FrameworkWebAutoConfiguration.SwaggerAutoConfiguration.class,
        GlobalExceptionHandler.class})
public class FrameworkWebAutoConfiguration {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /**
     * xss过滤器
     *
     * @author yangqh
     * @date 2019/12/26
     **/
    @ConditionalOnProperty(name = {"kk.frameworkweb.xss.enabled"}, havingValue = "true", matchIfMissing = false)
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        log.info("XssFilter init...");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns("/**");
        return registrationBean;
    }

    /**
     * 拦截器自动配置
     *
     * @author yangqh
     * @date 2019/12/26
     **/
    @ConditionalOnProperty(name = {"kk.frameworkweb.cors.enabled"}, havingValue = "true", matchIfMissing = false)
    @Configuration
    public static class CorsAutoConfiguration extends WebMvcConfigurerAdapter {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            log.info("cors init...");
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedHeaders("*")
                    .allowedMethods("*");
        }
    }

    /**
     * swagger
     *
     * @author yangqh
     * @date 2019/12/26
     **/
    @ConditionalOnClass(Docket.class)
    @ConditionalOnProperty(name = "kk.frameworkweb.swagger.enabled", havingValue = "true", matchIfMissing = false)
    @ConfigurationProperties(prefix = "kk.frameworkweb.swagger")
    @Configuration
    @EnableSwagger2
    public static class SwaggerAutoConfiguration {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Bean
        public Docket commonApi() {
            log.info("swagger-api commonApi init...");
            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("公共API")
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.kk.d"))
                    .paths(Predicates.or(PathSelectors.ant("/c/**")))
                    .build()
                    .securitySchemes(securitySchemes())
                    .securityContexts(securityContexts());
        }

        @Bean
        public Docket businessApi() {
            log.info("swagger-api businessApi init...");
            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("业务API")
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.kk.d"))
                    .paths(Predicates.or(PathSelectors.ant("/b/**")))
                    .build()
                    .securitySchemes(securitySchemes())
                    .securityContexts(securityContexts());
        }

        private List<ApiKey> securitySchemes() {
            List<ApiKey> apiKeyList = new ArrayList();
            apiKeyList.add(new ApiKey("authorization", "authorization", "header"));
            return apiKeyList;
        }

        private List<SecurityContext> securityContexts() {
            List<SecurityContext> securityContexts = new ArrayList<>();
            securityContexts.add(SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.regex("^(?!auth).*$")).build());
            return securityContexts;
        }

        private List<SecurityReference> defaultAuth() {
            AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
            AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
            authorizationScopes[0] = authorizationScope;
            List<SecurityReference> securityReferences = new ArrayList<>();
            securityReferences.add(new SecurityReference("authorization", authorizationScopes));
            return securityReferences;
        }

        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title(getName())
                    .description("API调试工具")
                    .version("1.0")
                    .build();
        }
    }
}
