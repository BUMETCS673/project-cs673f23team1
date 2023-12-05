package com.aceteam.tm.rest.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @description: some desc
 * @author: haoran
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("true")
    private Boolean swaggerShow;


    /**
     * Default Version Api
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = requestHandler -> {
            Class<?> declaringClass = requestHandler.declaringClass();
            if (declaringClass == BasicErrorController.class) {
                return false;
            }
            if (declaringClass.isAnnotationPresent(Controller.class)) {
                return true;
            }
            if (declaringClass.isAnnotationPresent(RestController.class)) {
                return true;
            }
            if (requestHandler.isAnnotatedWith(ResponseBody.class)) {
                return true;
            }
            return false;
        };
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .enable(swaggerShow)
                .groupName("all")
//                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate)
                .build();
    }

    /**
     * V1.0.0
     *
     * @return
     */
    @Bean
    Docket version300() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .enable(swaggerShow)
                .groupName(ApiVersionConstant.V_300)
                .select()
                .apis(input -> {
                    Optional<ApiVersion> apiVersionOptional = input.findAnnotation(ApiVersion.class);
                    if (apiVersionOptional.isPresent()) {
                        if (Arrays.asList(apiVersionOptional.get().group()).contains(ApiVersionConstant.V_300)) {
                            return true;
                        }
                    }
                    return false;
                })
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Api Information
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TerrierMall Restful API Document")
                // Show the latest Version
                .version(ApiVersionConstant.V_LATEST)
                .description("Terrier Mall works for all ")
                .contact(new Contact("AceTeam",
                        "https://github.com/BUMETCS673/project-cs673f23team1",
                        "kevdaup@bu.edu"))
                .build();
    }
}
