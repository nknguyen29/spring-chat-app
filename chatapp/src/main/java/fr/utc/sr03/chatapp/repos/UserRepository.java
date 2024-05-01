package fr.utc.sr03.chatapp.repos;

import fr.utc.sr03.chatapp.domain.User;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);

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
