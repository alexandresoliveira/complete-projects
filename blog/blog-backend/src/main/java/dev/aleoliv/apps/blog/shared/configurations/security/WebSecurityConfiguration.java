package dev.aleoliv.apps.blog.shared.configurations.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.aleoliv.apps.blog.shared.database.entities.AuthorityEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.AuthorityRepository;
import dev.aleoliv.apps.blog.shared.database.repositories.UserRepository;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("#{'${blog.cors.allowed-origins}'.split(',')}")
	private List<String> allowedOrigins;
	
	@Value("${blog.user.admin.email}")
	private String userAdminEmail;
	
	@Value("${blog.user.admin.password}")
	private String userAdminPassword;
	
	private final AuthService authService;
	private final TokenService tokenService;
	private final UserRepository userRepository;

	public WebSecurityConfiguration(AuthService authService, TokenService tokenService, UserRepository userRepository) {
		this.authService = authService;
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/v1/users/create").permitAll()
			.antMatchers(HttpMethod.POST, "/v1/auth/*").permitAll()
			.anyRequest().authenticated().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.addFilterBefore(new AuthFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
	
	@Bean
	public ApplicationRunner initializer(ApplicationContext context) {
		return args -> {
			AuthorityRepository authorityRepository = context.getBean(AuthorityRepository.class);
			UserRepository userRepository = context.getBean(UserRepository.class);
			
			AuthorityEntity adminAuthority = authorityRepository.findByName("ROLE_ADMIN")
																.orElseGet(() -> authorityRepository.save(new AuthorityEntity("ROLE_ADMIN")));
			authorityRepository.findByName("ROLE_USER")
							   .orElseGet(() -> authorityRepository.save(new AuthorityEntity("ROLE_USER")));
			
			userRepository.findByEmail(userAdminEmail)
						  .orElseGet(() -> {
							  Set<AuthorityEntity> authorities = new HashSet<>();
							  authorities.add(adminAuthority);
							  
							  UserEntity admin = new UserEntity();
							  admin.setName("Admin");
							  admin.setEmail(userAdminEmail);
							  admin.setPassword(userAdminPassword);
							  admin.setAuthorities(authorities);
							  
							  return userRepository.save(admin);
						  });
		};
	}
	
}
