package h;

import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.WebApplicationInitializer;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer
{
  public static void main(String[] inArgs)
  {
    SpringApplication.run(Application.class, inArgs);
  }

  @Autowired
  private TypeResolver typeResolver;

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder inApplication)
  {
    return inApplication.sources(Application.class);
  }

  @Bean
  public Docket api()
  {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
        .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
        .alternateTypeRules(AlternateTypeRules.newRule(
            typeResolver.resolve(Map.class, String.class,
                typeResolver.resolve(Map.class, String.class,
                    typeResolver.resolve(List.class, String.class))),
            typeResolver.resolve(Map.class, String.class, WildcardType.class),
            Ordered.HIGHEST_PRECEDENCE));
  }

  private ApiInfo apiInfo()
  {
    return new ApiInfoBuilder().title("KHALL API")
        .contact(new Contact("Simeon Hearring", "", "simeonlhearring@gmail.com")).build();
  }
}