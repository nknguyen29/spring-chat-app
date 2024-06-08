package fr.utc.sr03.chatapp.mapper;

import fr.utc.sr03.chatapp.domain.Token;
import fr.utc.sr03.chatapp.model.TokenWithoutUserDTO;


public class TokenMapper {

    public TokenWithoutUserDTO mapToDTO(final Token token, final TokenWithoutUserDTO tokenDTO) {
        tokenDTO.setId(token.getId());
        tokenDTO.setToken(token.getToken());
        tokenDTO.setType(token.getType());
        tokenDTO.setCreatedAt(token.getCreatedAt());
        tokenDTO.setExpiresAt(token.getExpiresAt());
        return tokenDTO;
    }

}
