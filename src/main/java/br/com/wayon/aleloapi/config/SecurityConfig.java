package br.com.wayon.aleloapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.inMemoryAuthentication( )
            .withUser( "usuario" ).password( passwordEncoder( ).encode( "usuariosenha" ) )
                .authorities( "ROLE_USER" )
            .and( )
            .withUser( "administrador" ).password( passwordEncoder( ).encode( "administradorsenha" ) )
                .authorities( "ROLE_ADMIN" );
    }
    
    @Override
    protected void configure( HttpSecurity http ) throws Exception
    {
        http.sessionManagement( )
            .sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        
        http.authorizeRequests( )
            .antMatchers( "/h2-console/**" ).permitAll( )
            .antMatchers( "/swagger-ui.html" ).permitAll( )
            .anyRequest( ).authenticated( )
            .and( )
            .headers().frameOptions().sameOrigin( )
            .and( ).httpBasic( )
            .and( ).csrf( ).disable( );
    }
    
    @Bean
    public PasswordEncoder passwordEncoder( )
    {
        return new BCryptPasswordEncoder( );
    }
}
