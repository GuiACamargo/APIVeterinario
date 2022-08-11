package com.gft.veterinario.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
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
	
	private Contact contato() {
		return new Contact(
				"Guilherme Camargo",
				"https://github.com/GuiACamargo",
				""
				);
	}
	
	private ApiInfoBuilder informacoesApi() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("API Veterinário");
        apiInfoBuilder.description("Desafio de API REST");
        apiInfoBuilder.version("1.0");
        apiInfoBuilder.termsOfServiceUrl("Termo de uso: Open Source");
        apiInfoBuilder.license("Apache 2.0");
        apiInfoBuilder.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
        apiInfoBuilder.contact(this.contato());

        return apiInfoBuilder;

    }
	
	@Bean
    public Docket detalheApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket
        		.securityContexts(Arrays.asList(securityContext()))
        		.securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gft.veterinario.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.informacoesApi().build())
                .consumes(new HashSet<String>(Arrays.asList("application/json")))
                .produces(new HashSet<String>(Arrays.asList("application/json")));

        return docket;
    }
}
