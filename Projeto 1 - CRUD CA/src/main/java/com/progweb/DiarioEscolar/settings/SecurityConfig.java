package com.progweb.DiarioEscolar.settings;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.progweb.DiarioEscolar.settings.security.AuthenticationFilter;
import com.progweb.DiarioEscolar.settings.security.AuthorizationFilter;
import com.progweb.DiarioEscolar.settings.security.JWTUtil;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
	@Autowired
    private JWTUtil jwtUtil;
    
    //rotas que nao precisam de autenticacao
	private static final String[] PUBLIC_ROUTES = {
        "/v2/api-docs",
        "/signup",
        "/h2-console/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/login",
        "/alunos/**",
        "/professores/**",
        "/turmas/**",
        "/projetos/**"
    };


    //configuracao da criptografia das senhas
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        //configuracao cors
        http.httpBasic();
        http.cors().and().csrf().disable();
        http.headers().frameOptions().sameOrigin(); 

        //filtros
        http.addFilter(new AuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new AuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        
        //requisicoes
        http.authorizeRequests()
            .antMatchers(HttpMethod.PATCH, "/turmas/**").hasAnyRole("PROF")
            .antMatchers(HttpMethod.PATCH, "/projetos/**").hasAnyRole("PROF")
            .antMatchers( PUBLIC_ROUTES).permitAll()
            .anyRequest().authenticated();
            
        //configuracao nao criar secao so usuario
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        //adicionar as permisoes do cors e umas a mais dos methods post, get...
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS", "PATCH"));

        //aadicionar para todas as requisicoes em todos os url /** */
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
