package com.nus.dealhunter.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

  private ApiKey apiKey() {
    return new ApiKey("JWT", "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }

  @Bean
  public Docket docket(){
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .enable(true)
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .groupName("Baobao Huayuan")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.nus.dealhunter.controller"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Deal Hunter")
        .contact(new Contact("makabaka", "wuxidixi", "dingdingche"))
        .version("1.0")
        .description("baobao bashi")
        .build();
  }

  @Bean
  public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
    return new BeanPostProcessor() {
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebMvcRequestHandlerProvider) {
          customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
        }
        return bean;
      }

      private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(
          List<T> mappings) {
        List<T> copy = mappings.stream().filter(mapping -> mapping.getPatternParser() == null)
            .collect(Collectors.toList());
        mappings.clear();
        mappings.addAll(copy);
      }

      @SuppressWarnings("unchecked")
      private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
        try {
          Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
          if (field == null) {
            throw new IllegalStateException();
          }
          field.setAccessible(true);
          return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
          throw new IllegalStateException(e);
        }
      }
    };
  }

}
