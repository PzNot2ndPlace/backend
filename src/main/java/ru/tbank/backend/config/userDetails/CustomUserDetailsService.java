package ru.tbank.backend.config.userDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tbank.backend.entity.UserEntity;
import ru.tbank.backend.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
        return new CustomUserDetails(userEntity);
    }
}