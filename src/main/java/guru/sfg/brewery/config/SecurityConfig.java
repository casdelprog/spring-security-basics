package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
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
				.antMatchers("/h2-console/**").permitAll() //do not use in production!
	            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
	            .antMatchers(HttpMethod.GET, "/api/v1/beer/**")
	                .hasAnyRole("ADMIN", "CUSTOMER", "USER")
	            .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
	            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}")
	                .hasAnyRole("ADMIN", "CUSTOMER", "USER")
	            .mvcMatchers("/brewery/breweries")
	                .hasAnyRole("ADMIN", "CUSTOMER")
	            .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
	                .hasAnyRole("ADMIN", "CUSTOMER")
	            .mvcMatchers("/beers/find", "/beers/{beerId}")
	                .hasAnyRole("ADMIN", "CUSTOMER", "USER");
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
