package fr.utc.sr03.chatapp.service;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.repos.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import fr.utc.sr03.chatapp.model.HttpUserDetails;

// log
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class HttpUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(HttpUserDetailsService.class);

    public HttpUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public HttpUserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            log.warn("user not found: {}", email);
            throw new UsernameNotFoundException("User " + email + " not found");
        }
	    // UserDetails userDetails =
        //     org.springframework.security.core.userdetails.User
        //             .builder()    	
		// 			.username(user.getEmail())
		// 			.password(user.getPassword())
		// 			.roles(roles.toArray(new String[0]))
		// 			.build();
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new HttpUserDetails(user.getId(), email, user.getPassword(), roles);
        // return new InMemoryUserDetailsManager(user);
        // return userDetails;
    }

}
