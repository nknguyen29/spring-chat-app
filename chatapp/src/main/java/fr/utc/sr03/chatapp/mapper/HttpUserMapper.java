package fr.utc.sr03.chatapp.mapper;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.model.HttpUserDetails;


@Component
public class HttpUserMapper {

    public HttpUserDetails mapToDTO(final User user) {
        Collection<? extends GrantedAuthority> authorities = user.getIsAdmin()
                ? AuthorityUtils.createAuthorityList("ROLE_ADMIN")
                : AuthorityUtils.createAuthorityList("ROLE_USER");
        return new HttpUserDetails(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getFailedConnectionAttempts(),
                authorities,
                !user.getIsLocked());
    }

}
