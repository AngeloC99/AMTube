package it.uniroma1.userManagement.repositories;

import it.uniroma1.userManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
}
