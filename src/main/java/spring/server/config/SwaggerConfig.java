//package spring.server.config;
//
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//@EnableAutoConfiguration
//public class SwaggerConfig {
//
//    private final String version = "v1";
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("review API")
//                .description("리뷰콕 API 명세서")
//                .build();
//    }
//
//    @Bean
//    public Docket commonApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName(version)
//                .apiInfo(this.apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//}