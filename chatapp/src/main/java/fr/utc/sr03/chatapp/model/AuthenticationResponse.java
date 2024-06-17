package fr.utc.sr03.chatapp.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class AuthenticationResponse {

    @NotNull
    @Size(min = 512) // minimum size of a JWT token
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

}
