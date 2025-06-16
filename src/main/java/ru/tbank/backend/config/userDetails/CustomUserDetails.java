package ru.tbank.backend.config.userDetails;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.tbank.backend.entity.UserEntity;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    public UUID getId() {
        return userEntity.getId();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
}