package com.rubycon.rubyconteam2.global.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String version;
    private String title;

    @Bean
    public Docket api() {
        version = "V1";
        title = "Rubycon Party-ing API " + version;

        // Global Parameter
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name(HttpHeaders.AUTHORIZATION) //헤더 이름
                .description("Bearer Access Token") //설명
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();

        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());

        // Global Error 응답
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message("Success !")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message("Not Found Entity")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message("Internal Server Error")
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rubycon.rubyconteam2"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo(title, version))
                .globalOperationParameters(aParameters)
                .globalResponseMessage(RequestMethod.GET, responseMessages);
    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "Swagger로 생성한 API Docs",
                version,
                "",
                new Contact("Contact Me", "", "wwlee94@gmail.com"),
                "",
                "",
                new ArrayList<>());
    }
}
