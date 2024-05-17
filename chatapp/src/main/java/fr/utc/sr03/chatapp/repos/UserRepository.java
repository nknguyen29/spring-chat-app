package fr.utc.sr03.chatapp.repos;

import fr.utc.sr03.chatapp.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);

    Page<User> findAllByIsAdmin(Boolean isAdmin, Pageable pageable);
    Page<User> findAllByIsLocked(Boolean isLocked, Pageable pageable);
    Page<User> findAllByIsAdminAndIsLocked(Boolean isAdmin, Boolean isLocked, Pageable pageable);
    Page<User> findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCase(
        String firstName, String lastName, String email, Pageable pageable
    );
    Page<User> findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCaseAndIsAdmin(
        String firstName, String lastName, String email, Boolean isAdmin, Pageable pageable
    );
    Page<User> findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCaseAndIsLocked(
        String firstName, String lastName, String email, Boolean isLocked, Pageable pageable
    );
    Page<User> findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCaseAndIsAdminAndIsLocked(
        String firstName, String lastName, String email, Boolean isAdmin, Boolean isLocked, Pageable pageable
    );

}

// Spring Data JPA also lets you define other query methods by declaring their
// method signature.
// For example, this UserRepository interface has a findByEmail() method that is
// used to find a User entity by its email address.
//
// In a typical Java application, you might expect to write a class that
// implements CustomerRepository.
// However, that is what makes Spring Data JPA so powerful: You need not write
// an implementation of the repository interface.
// Spring Data JPA creates an implementation when you run the application.
//
// See
// https://spring.io/guides/gs/accessing-data-jpa#:~:text=Create%20Simple%20Queries
// for more information.
