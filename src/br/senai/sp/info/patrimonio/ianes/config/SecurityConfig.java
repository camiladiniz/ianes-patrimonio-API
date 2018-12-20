package br.senai.sp.info.patrimonio.ianes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//Habilitando o módulo de segurança web do SPRING
@EnableWebSecurity
/**
 * Configurações de segurança;
 * Permite ou barra o acesso através da autenticação
 * @author Camila Diniz
 *
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtFilter jwtFilter;
	
	/**
	 * HttpSecurity informa como o sistema de segurança da aplicação funciona
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Desabilitando as sessões, pois webService não trabalha com sessões
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		//liberando o endpoint de autenticação e geração de token para todos
		.authorizeRequests().antMatchers("/rest/auth/jwt").permitAll()
		//barrando o acesso aos endpoints restantes
		.anyRequest().authenticated()
		.and()
		.csrf().disable().cors();
		
		//Adicionando o filtro
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
