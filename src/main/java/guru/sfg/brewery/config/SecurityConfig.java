package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import guru.sfg.brewery.security.google.Google2FaFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserDetailsService userDetailsService;
    private final Google2FaFilter google2FaFilter;

	@Bean
    PasswordEncoder passwordEncoder(){	   
		return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }	  
	
	// needed for use with Spring Data JPA SPeL
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterBefore(google2FaFilter, SessionManagementFilter.class);
		
		http
		//added to default configuration to exclude rss and login from auth
		.authorizeRequests(auth -> {
			//permits all for slash requests
			auth
				.antMatchers("/h2-console/**").permitAll() //do not use in production!
	            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll();
		})
		.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			   .formLogin(loginConfigurer -> {
                   loginConfigurer
                           .loginProcessingUrl("/login")
                           .loginPage("/").permitAll()
                           .successForwardUrl("/")
                           .defaultSuccessUrl("/")
                           .failureUrl("/?error");
               })
                .logout(logoutConfigurer -> {
                    logoutConfigurer
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                            .logoutSuccessUrl("/?logout")
                            .permitAll();
                })
		.httpBasic()
		.and().csrf().ignoringAntMatchers("/h2-console/**", "/api/**")
        .and().rememberMe()
                .key("sfg-key")
                .userDetailsService(userDetailsService);
		
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
