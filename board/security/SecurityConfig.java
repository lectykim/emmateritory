package com.flowering.project.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder123(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception{
        security.userDetailsService(userDetailsService);

        security.authorizeRequests().antMatchers("/","/board/getBoardList","/system/**").permitAll();
        security.authorizeRequests().antMatchers("/board/getBoard","/board/insertBoard","/board/deleteBoard").authenticated();
        security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        security.authorizeRequests().anyRequest().authenticated();

        security.csrf().disable();
        security.formLogin().loginPage("/system/login")
                .defaultSuccessUrl("/board/getBoardList",true);
        security.exceptionHandling().accessDeniedPage("/system/login?error=true");
        security.logout().logoutUrl("/system/logout")
                .invalidateHttpSession(true).logoutSuccessUrl("/");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder123());
    }
}
