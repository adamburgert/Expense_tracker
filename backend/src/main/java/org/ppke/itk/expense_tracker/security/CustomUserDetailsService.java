package org.ppke.itk.expense_tracker.security;

import org.ppke.itk.expense_tracker.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.ppke.itk.expense_tracker.domain.User dbUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with username %s not found", username)
                ));
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(dbUser.getPassword())
                .authorities(dbUser.getRole())
                .build();
    }

}
