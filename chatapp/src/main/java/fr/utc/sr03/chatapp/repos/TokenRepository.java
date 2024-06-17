package fr.utc.sr03.chatapp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.utc.sr03.chatapp.domain.Token;
import fr.utc.sr03.chatapp.domain.User;


public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByUserAndType(User user, String type);
    Token findByTokenAndType(String token, String type);

}
