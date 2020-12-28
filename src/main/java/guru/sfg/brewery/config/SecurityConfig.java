package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		//added to default configuration to exclude rss and login from auth
		.authorizeRequests(auth -> {
			//permits all for slash requests
			auth
				.antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
				.antMatchers("/beers/find", "/beers*").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
				.mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
		})
		.authorizeRequests()
			.anyRequest().authenticated()
			.and()
		.formLogin().and()
		.httpBasic();
	}

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("spring")
			.password("{noop}guru")
			.roles("ADMIN")
			.and()
			.withUser("user")
			.password("{noop}password")
			.roles("USER")
			.and()
			.withUser("scoot")
			.password("{noop}tiger")
			.roles("CUSTOMER");
	}



//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails admin = User.withDefaultPasswordEncoder()
//				.username("spring").password("guru").roles("ADMIN").build();
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("user").password("password").roles("USER").build();
//		
//		return new InMemoryUserDetailsManager(admin, user);
//	}

	
}
