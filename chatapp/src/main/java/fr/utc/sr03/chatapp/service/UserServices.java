package fr.utc.sr03.chatapp.service;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import java.util.Date;

@Service
@Transactional
public class UserServices {
 
    public static final int MAX_FAILED_ATTEMPTS = 3;
     
    // private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours
     
    @Autowired
    private UserRepository repo;
     
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedConnectionAttempts() + 1;
        repo.updateFailedConnectionAttemptsByEmail(user.getEmail(), newFailAttempts);
    }
     
    public void resetFailedAttempts(String email) {
        repo.updateFailedConnectionAttemptsByEmail(email, 0);
    }
     
    public void lock(User user) {
        user.setIsLocked(true);
        // user.setLockTime(new Date());
         
        repo.save(user);
    }
     
    // public boolean unlockWhenTimeExpired(User user) {
    //     long lockTimeInMillis = user.getLockTime().getTime();
    //     long currentTimeInMillis = System.currentTimeMillis();
         
    //     if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
    //         user.setAccountNonLocked(true);
    //         user.setLockTime(null);
    //         user.setFailedAttempt(0);
             
    //         repo.save(user);
             
    //         return true;
    //     }
         
    //     return false;
    // }

    public User getByEmail(String email) {
        return repo.findByEmail(email);
    }
}
