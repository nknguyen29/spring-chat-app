package fr.utc.sr03.chatapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.mapper.HttpUserMapper;
import fr.utc.sr03.chatapp.model.HttpUserDetails;
import fr.utc.sr03.chatapp.repos.UserRepository;


@Service
public class HttpUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpUserMapper httpUserMapper;

    public HttpUserDetailsService(
            final UserRepository userRepository, final HttpUserMapper httpUserMapper) {
        this.userRepository = userRepository;
        this.httpUserMapper = httpUserMapper;
    }

    public HttpUserDetails findUserByEmail(final String email) {
        final User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            return null;
        }
        return httpUserMapper.mapToDTO(user);
    }

    @Override
    public HttpUserDetails loadUserByUsername(
            final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return httpUserMapper.mapToDTO(user);
    }

}
