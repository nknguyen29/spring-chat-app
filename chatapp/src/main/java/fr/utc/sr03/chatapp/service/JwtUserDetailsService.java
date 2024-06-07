package fr.utc.sr03.chatapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.mapper.JwtUserMapper;
import fr.utc.sr03.chatapp.model.JwtUserDetails;
import fr.utc.sr03.chatapp.repos.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUserMapper jwtUserMapper;

    public JwtUserDetailsService(
            final UserRepository userRepository, final JwtUserMapper jwtUserMapper) {
        this.userRepository = userRepository;
        this.jwtUserMapper = jwtUserMapper;
    }

    @Override
    public JwtUserDetails loadUserByUsername(
            final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return jwtUserMapper.mapToDTO(user);
    }

}
