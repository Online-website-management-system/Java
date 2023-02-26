package com.lucas.xunta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;

/**
 * swagger配置
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/7/7 18:31
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Value(value = "${swagger.enabled}")
    private Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        return getDocket(swaggerEnabled, null, "xunta",
                "xunta service", "1.0", "com.lucas.xunta", null);
    }

    public static Docket getDocket(boolean swaggerFlag, String groupName, String apiTitle, String description, String version, String basePackage, List<Parameter> operationParameters) {
        return (new Docket(DocumentationType.SWAGGER_2))
                .enable(swaggerFlag)
                .groupName(groupName)
                .apiInfo(apiInfo(apiTitle, description, version == null ? "0.0.1" : version))
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey(), apiLang()))
                .globalOperationParameters(operationParameters)
                .securityContexts(Arrays.asList(securityContext()))
                .produces(newHashSet(new String[]{"application/json;charset=UTF-8"}));
    }

    private static ApiInfo apiInfo(String apiTitle, String description, String version) {
        return (new ApiInfoBuilder()).title(apiTitle)
                .description(description)
                .version(version)
                .termsOfServiceUrl("https://xt.junengw.com")
                .licenseUrl("https://xt.junengw.com").build();
    }

    private static ApiKey apiLang() {
        return new ApiKey("lang", "Accept-Language", "header");
    }

    private static ApiKey apiKey() {
        return new ApiKey("authorization", "Authorization", "header");
    }


    private static SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Arrays.asList(new SecurityReference("authorization", authorizationScopes),
                new SecurityReference("lang", authorizationScopes)
        );
    }
}

