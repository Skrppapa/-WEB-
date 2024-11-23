package com.example.cars.Config;

import com.example.cars.Repositories.UserRepository;
import com.example.cars.Service.MyUserDetaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Обе эти аннотации используются для настройки SpringSecurity
@Configuration    // Показывает что класс являестя конфигурационным бином
@EnableWebSecurity  // Показывает что весь класс будет применен к глобальной веб-безопасности

@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRepository userRepository; // Внедряем UserRepository

    // PasswordEncoder - это интерфейс использующийся для одностороннего преобразования пароля
    // Такое преобразование используется для хранения пароля который будет сравниваться с введенным пользователем паролем
    // BCryptPasswordEncoder() - собственно сам кодировщик
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Метод создает и сохраняет нового пользователя в БД
    // UserDetailsService - это интерфейс позволяющий предоставлять сведения о пользователе в контексте безопасности
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetaleService(userRepository, passwordEncoder()); // Передаем зависимости
    }


    // SecurityFilterChain - интерфейс для создания своих слоев фильтрации предоставления контента
    // HttpSecurity - класс который позволяет конфигурировать аутентификацию и авторизацию HTTP запросов
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable) // Для защиты от CSRF атак
                .authorizeHttpRequests(auth -> auth.requestMatchers("/welcome", "/new-user").permitAll()
                        .requestMatchers("/**").authenticated()) // Авторизированный пользователь получает доступ
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

    // Аутентификация - кто вы?
    // AuthenticationProvider - используется для подтверждения личности пользователя
    // DaoAuthenticationProvider - реализация провайдера использующий UDS и PasswordEncoder для аутентиф. имени и пароля
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
