package com.products.config;

import com.products.service.UsuarioServiceIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {

                    //PERMISOS PARA CREAR USUARIOS SOLO USUARIO ADMINISTRADOR
                    http.requestMatchers(HttpMethod.GET, "/user").hasRole("ADMINISTRADOR");

                    http.requestMatchers(HttpMethod.POST, "/user/crear").hasRole("ADMINISTRADOR");
                    http.requestMatchers(HttpMethod.POST, "/user/crear/rol/{idRol}").hasRole("ADMINISTRADOR");
                    http.requestMatchers(HttpMethod.DELETE, "/user/eliminar/{usuarioID}").hasRole("ADMINISTRADOR");
                    http.requestMatchers(HttpMethod.PUT, "/user/actualizar/rol/{idRol}/permision/{idPermission}").hasRole("ADMINISTRADOR");
                    http.requestMatchers(HttpMethod.PUT, "/user/actualizar/usuario/{idUsuario}/rol/{idRol}").hasRole("ADMINISTRADOR");

                    //PETICION PARA PRODUCTOS
                    http.requestMatchers(HttpMethod.GET, "/producto").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/producto/categoria/{nombreCategoria}").hasAnyRole("ADMINISTRADOR", "DEVELOPER", "USUARIO");
                    http.requestMatchers(HttpMethod.GET, "/producto/{idProducto}").hasAnyRole("ADMINISTRADOR", "DEVELOPER", "USUARIO", "VISITANTE");
                    http.requestMatchers(HttpMethod.POST, "/producto/crear/categoriaID/{idCategoria}").hasAnyRole("ADMINISTRADOR", "DEVELOPER", "USUARIO");
                    http.requestMatchers(HttpMethod.DELETE, "/producto/delete/{idProducto}").hasAnyRole("ADMINISTRADOR", "DEVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/producto/actualizar/{idProducto}").hasAnyRole("ADMINISTRADOR", "DEVELOPER", "USUARIO");

                    //PETICION PARA CATEGORIAS
                    http.requestMatchers(HttpMethod.GET, "/categoria").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/categoria").hasAnyRole("ADMINISTRADOR", "DEVELOPER", "USUARIO", "VISITANTE");
                    http.requestMatchers(HttpMethod.GET, "/categoria/{idCategoria}").hasAnyRole("ADMINISTRADOR", "DEVELOPER", "USUARIO", "VISITANTE");
                    http.requestMatchers(HttpMethod.POST, "/categoria/crear").hasAnyRole("ADMINISTRADOR", "DEVELOPER");


                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UsuarioServiceIMP usuarioServiceIMP) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(usuarioServiceIMP);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }


}
