package com.parchenegar.capsule.repository;

import com.parchenegar.capsule.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByMobile(String mobile);
}
