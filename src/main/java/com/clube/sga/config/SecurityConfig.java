package com.clube.sga.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.clube.sga.domain.PerfilTipo;
import com.clube.sga.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String ADMIN 		= PerfilTipo.ADMIN.getDesc();
    private static final String ASSOCIADO 	= PerfilTipo.ASSOCIADO.getDesc();
    private static final String USUARIO 	= PerfilTipo.USUARIO.getDesc();
	
	@Autowired
	private UsuarioService service;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
		http.authorizeRequests()
			// acessos públicos liberados
			.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
			.antMatchers("/", "/home").permitAll()
			.antMatchers("/u/novo/cadastro","/u/cadastro/realizado","/u/cadastro/paciente/salvar").permitAll()
			.antMatchers("/u/confirmacao/cadastro").permitAll()
			.antMatchers("/u/p/**").permitAll()
			.antMatchers("/relatorios/associados").permitAll()
			// acessos privados do usuario
			.antMatchers("/u/listar/associados").hasAuthority(USUARIO)
			.antMatchers("/associados/editar/credenciais/associado/**").hasAuthority(USUARIO)
			.antMatchers("/associados/excluir/**").hasAuthority(USUARIO)
			.antMatchers("/associados/usuario/editar").hasAuthority(USUARIO)
			.antMatchers("/relatorios/associados").hasAuthority(USUARIO)

			
			// acessos privados admin
			.antMatchers("/u/editar/senha", "/u/confirmar/senha").hasAnyAuthority(ADMIN, USUARIO, ASSOCIADO)
			.antMatchers("/u/**").hasAnyAuthority(ADMIN, USUARIO)
			.antMatchers("/servicos/**").hasAuthority(ADMIN)
			
			// acessos privados associados incluído Alex
			.antMatchers("/associados/salvar").hasAuthority(ASSOCIADO)
			.antMatchers("/associados/**").hasAuthority(ASSOCIADO)
			.antMatchers("/dependentes/**").hasAuthority(ASSOCIADO) 
			.antMatchers("/agendamentos/agendar").hasAuthority(ASSOCIADO)
			.antMatchers("/servicos/servico/titulo").hasAuthority(ASSOCIADO)
				
			// acessos privados especialidades
			.antMatchers("/especialidades/**").hasAuthority(ADMIN)
			
			.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login-error")
				.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/acesso-negado")
			.and()
				.rememberMe();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	

}
