package com.bestdeals.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by rohitkumar on 14/08/17.
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.bestdeals")
public class SwaggerConfig {

    /**
     *
     * @return Docket
     */
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(metaData());
    }

    /**
     *
     * @return ApiInfo
     */
    private ApiInfo metaData() {

        ApiInfo apiInfo = new ApiInfo(
                "BestDeals Bank",
                "BestDeals Bank Deals API",
                "1.0",
                "Terms of service",
                new Contact("Rohit Kumar", "https://projects.spring.io/spring-boot/", "rrohit.ramsen@mgail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;

    }

}