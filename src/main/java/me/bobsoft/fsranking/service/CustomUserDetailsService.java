package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.User;
import me.bobsoft.fsranking.model.utils.CustomUserDetails;
import me.bobsoft.fsranking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByMail(userEmail);

        return optionalUser
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }
}
