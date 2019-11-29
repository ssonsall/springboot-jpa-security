package com.cos.crud.config;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.crud.handler.MyLogoutSuccessHandler;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터에 등록하는 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @Bean 어노테이션은 메서드에 붙이는데 객체를 만들어 줌. 즉 스태틱 함수가 아닌데 쓸 수 있음. 메모리에 띄워줌=스프링컨테이너에 들어감
	
	// 1) 첫번째 할 일 : 비밀번호 암호화 함수
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}

	// 2) 두번째 할 일 : 필터링
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/board/list").authenticated()
		.antMatchers("/admin/**","/board/**").authenticated()
		.anyRequest().permitAll()
		.and()
		.formLogin()
		//username이나 password 필드 이름을 내가 쓰고 싶은걸로 바꾸고 싶다면 usernameParameter, passwordParameter로 세팅해줘야 함
		.usernameParameter("username")
		.passwordParameter("password")
		.loginPage("/user/loginForm")
		.loginProcessingUrl("/user/loginProcess")
		.defaultSuccessUrl("/home")
		.and()
		.logout().logoutSuccessHandler(new MyLogoutSuccessHandler());
	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	//내가 인코딩하는게 아니라, 어떤 인코딩으로 패스워드가 만들어졌는지 알려주는것.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
	}

}
