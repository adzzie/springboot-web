package com.belajar.java.web.config;

import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.apache.tomcat.util.buf.UEncoder;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import sun.security.provider.MD5;

import javax.sql.DataSource;
import java.beans.Encoder;
import java.util.Base64;

/**
 * Description konfigurasiSecurity
 *
 * @author aji gojali
 */
@EnableWebSecurity
@Configuration
public class konfigurasiSecurity extends WebSecurityConfigurerAdapter {

    private static final String SQL_LOGIN = "select username, password, active as enabled from s_users where username = ?";
    private static final String SQL_PERMISSION = "select u.username, r.nama as authority " +
            "from s_users u " +
            "join s_user_role ur on u.id = ur.id_user " +
            "join s_roles r on ur.id_role = r.id " +
            "where u.username = ?";

    @Autowired
    private DataSource ds;

    @Bean
    public static PasswordEncoder createDelegatingPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // login dr database tanpa menggunakan password encrip
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery(SQL_LOGIN)
                .authoritiesByUsernameQuery(SQL_PERMISSION)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

//    //untuk static login
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication().withUser("aji").password("{noop}1234").roles("USER");
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/peserta",true)
                .and()
                .logout();
    }

}
