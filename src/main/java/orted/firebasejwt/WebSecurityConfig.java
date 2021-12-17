package orted.firebasejwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import orted.firebasejwt.exceptions.CustomAccessDenied;
import orted.firebasejwt.exceptions.CustomUnauthorizedInFilter;
import orted.firebasejwt.filters.JwtRequestFilter;
import orted.firebasejwt.services.FirebaseAuthenticationProvider;

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FirebaseAuthenticationProvider firebaseAuthenticationProvider;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(firebaseAuthenticationProvider);
    }

    /*
        if there is some common path in url then keep more detailed one above
        to function it correctly
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1/auth/authenticate").permitAll()
                .antMatchers("/v1/admin/**").hasAnyAuthority("admin")
                .antMatchers("/v1/user/**").hasAnyAuthority("user", "admin")
                .anyRequest().authenticated().and()
        .exceptionHandling()
                .authenticationEntryPoint(new CustomUnauthorizedInFilter())
                .accessDeniedHandler(new CustomAccessDenied())
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
