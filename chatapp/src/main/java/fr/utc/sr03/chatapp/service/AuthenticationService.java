package fr.utc.sr03.chatapp.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.model.HttpUserDetails;
import fr.utc.sr03.chatapp.repos.UserRepository;


@Service
@Transactional
public class AuthenticationService {

    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    @Autowired
    private UserRepository userRepository;

    public void resetFailedAttempts(HttpUserDetails httpUserDetails) {
        final User user = userRepository.findByEmailIgnoreCase(httpUserDetails.getEmail());
        user.setLastConnection(new Timestamp(System.currentTimeMillis()));
        user.setFailedConnectionAttempts(0);
        userRepository.save(user);
    }

    public void increaseFailedAttempts(HttpUserDetails httpUserDetails) {
        final User user = userRepository.findByEmailIgnoreCase(httpUserDetails.getEmail());
        if (user.getFailedConnectionAttempts() == null) {
            user.setFailedConnectionAttempts(1);
        } else {
            user.setFailedConnectionAttempts(user.getFailedConnectionAttempts() + 1);
        }
        userRepository.save(user);
    }

    public void lock(HttpUserDetails httpUserDetails) {
        final User user = userRepository.findByEmailIgnoreCase(httpUserDetails.getEmail());
        user.setIsLocked(true);
        user.setLockedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    public void unlock(User user) {
        user.setIsLocked(false);
        user.setFailedConnectionAttempts(0);
        user.setLockedAt(null);
        userRepository.save(user);
    }

    public boolean unlockWhenTimeExpired(HttpUserDetails httpUserDetails) {
        final User user = userRepository.findByEmailIgnoreCase(httpUserDetails.getEmail());
        if (user.getLockedAt() != null
                && user.getLockedAt().getTime() + LOCK_TIME_DURATION < System.currentTimeMillis()) {
            unlock(user);
            return true;
        }
        return false;
    }

}
