package com.example.cars.Config;

import com.example.cars.Models.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private MyUser user;

    public MyUserDetails(MyUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRoles().split(", ")) // Сплитим строку роли на отдельные кусочки
                .map(SimpleGrantedAuthority::new) // Преобразуем значение в нужный класс
                .collect(Collectors.toList());  // И собираем все роли в List
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() { // Не истек ли аккаунт
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  // Не истек ли пароль
        return true;
    }

    @Override
    public boolean isEnabled() { // Включен ли пользователь
        return true;
    }
}
