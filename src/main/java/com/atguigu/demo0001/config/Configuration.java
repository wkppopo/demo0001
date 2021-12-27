package com.atguigu.demo0001.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@org.springframework.context.annotation.Configuration
@EnableSwagger2
public class Configuration {
    /**
     * 注入docket全局配置对象
     */
    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("swagger 练习")
                .apiInfo(new ApiInfoBuilder()
                        .title("jwt认证联系")
                        .description("描述部分")
                        .contact(new Contact("新手","http://localhost:8877/test","email"))
                        .version("1.0")
                        .build())

                .select() //拦截部分
                .apis(RequestHandlerSelectors.basePackage("com.atguigu.demo0001.controller"))//拦截这个包中带注解的类
                .paths(Predicates.or(
                        PathSelectors.regex("/.*") //拦截规则
                ))
                .build();
        return docket;
    }
}
