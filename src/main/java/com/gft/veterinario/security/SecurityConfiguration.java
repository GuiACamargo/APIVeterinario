package com.gft.veterinario.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gft.veterinario.filter.FiltroAutenticacao;
import com.gft.veterinario.services.AutenticacaoService;
import com.gft.veterinario.services.UsuarioService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**", "/swagger-ui/**"
			// other public endpoints of your API may be appended to this array
	};

   
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	// HttpSecurity 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers(HttpMethod.POST, "/v1/autenticacoes").permitAll().and().authorizeRequests()
        	.antMatchers(HttpMethod.POST, "/v1/clientes").permitAll().and().authorizeRequests()
        	.antMatchers("/v1/raca/gif/aleatorio").permitAll().and().authorizeRequests()
        	.antMatchers("/v1/raca/imagem/aleatoria").permitAll().and().authorizeRequests()
        	.antMatchers(AUTH_WHITELIST).permitAll()
        	.anyRequest().authenticated()
        	.and().csrf().disable()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and().addFilterBefore(new FiltroAutenticacao(autenticacaoService, usuarioService), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
	
    // WebSecurity
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring();
    }
	
}

