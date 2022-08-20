package it.uniroma1.userManagement.auth;

import it.uniroma1.userManagement.models.User;
import it.uniroma1.userManagement.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailsUserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(DetailsUserService.class);
    private final UserRepository userRepository;

    public DetailsUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DetailsUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) throw new UsernameNotFoundException("Could not find any user with provided credentials");

        return new DetailsUser(user.get().getUsername(), user.get().getPassword());
    }
}
