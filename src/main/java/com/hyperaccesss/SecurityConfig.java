package com.hyperaccesss;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // Pour proteger toutes les méthodes (DAO, Metier ...)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void globalConfig(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery(
						"SELECT username AS principal, password AS credentials, etat FROM utilisateur WHERE username = ? AND etat = 1")
				.authoritiesByUsernameQuery(
						"SELECT username AS principal, role AS role FROM utilisateur WHERE username = ?  AND etat = 1")
				.rolePrefix("ROLE_");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()

				.authorizeRequests()
				.antMatchers("/build/**", "/dist/**", "/plugins/**", "/shared/**", "/smsapi/**")
				.permitAll() // Ressources à autoriser à tout le monde
				.anyRequest().authenticated().and().formLogin().loginPage("/login").failureUrl("/login").permitAll()
				.defaultSuccessUrl("/").and().exceptionHandling().accessDeniedPage("/403").and().logout()
				.invalidateHttpSession(true).logoutUrl("/logout").permitAll().and().sessionManagement()
				.maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/login");
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() { // enabling the concurrent session-control support
		return new HttpSessionEventPublisher();
	}
}
