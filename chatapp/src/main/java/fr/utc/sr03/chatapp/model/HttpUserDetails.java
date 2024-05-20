package fr.utc.sr03.chatapp.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.validation.constraints.NotNull;

public class HttpUserDetails extends User {

    public final Long id;

    public final String email;

    public final String hash;

    public final Collection<? extends GrantedAuthority> authorities;

    public HttpUserDetails(final Long id, final String email, final String hash,
            final Collection<? extends GrantedAuthority> authorities) {
        super(email, hash, authorities);
        this.id = id;
        this.email = email;
        this.hash = hash;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

}
