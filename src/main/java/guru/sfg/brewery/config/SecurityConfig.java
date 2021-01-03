package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
    PasswordEncoder passwordEncoder(){	   
		return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }	  
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		//added to default configuration to exclude rss and login from auth
		.authorizeRequests(auth -> {
			//permits all for slash requests
			auth
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
				.antMatchers("/beers/find", "/beers*").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
				.mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
				.mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
		})
		.authorizeRequests()
			.anyRequest().authenticated()
			.and()
		.formLogin().and()
		.httpBasic().and().csrf().disable();
		
		//h2 console config
		http.headers().frameOptions().sameOrigin();
	}

//As no other userdetails is provided, it takes this by default
	
//	@Autowired
//	JpaUserDetailsService jpaUserDetailsService; 
//	
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.userDetailsService(this.jpaUserDetailsService).passwordEncoder(passwordEncoder());
//    }


	
}
