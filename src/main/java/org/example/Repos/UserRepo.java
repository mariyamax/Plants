package org.example.Repos;

import org.example.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);

    User findByID(Long id);
}
