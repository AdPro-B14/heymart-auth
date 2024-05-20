package id.ac.ui.cs.advprog.heymartauth.repository;

import id.ac.ui.cs.advprog.heymartauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<Long> deleteUserByEmail(String email);
}
