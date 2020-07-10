package com.parchenegar.capsule.repository.user;

import com.parchenegar.capsule.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
}
