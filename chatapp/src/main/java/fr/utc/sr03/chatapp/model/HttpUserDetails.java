package fr.utc.sr03.chatapp.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class HttpUserDetails extends User {

    public Long id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
    @UserEmailUnique
    private String email;

    public final Collection<? extends GrantedAuthority> authorities;

    @JsonProperty("failedConnectionAttempts")
    private Integer failedConnectionAttempts;

    public HttpUserDetails(
            final Long id,
            final String firstName,
            final String lastName,
            final String email,
            final String password,
            final Integer failedConnectionAttempts,
            final Collection<? extends GrantedAuthority> authorities,
            final boolean accountNonLocked) {
        super(email, password, true, true, true, accountNonLocked, authorities);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.failedConnectionAttempts = failedConnectionAttempts;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsAdmin() {
        return this.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public Integer getFailedConnectionAttempts() {
        return failedConnectionAttempts;
    }

    public void setFailedConnectionAttempts(final Integer failedConnectionAttempts) {
        this.failedConnectionAttempts = failedConnectionAttempts;
    }

    public Boolean getIsLocked() {
        return !isAccountNonLocked();
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getInitials() {
        return this.firstName.substring(0, 1) + this.lastName.substring(0, 1);
    }

}
