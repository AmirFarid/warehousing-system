package com.parchenegar.capsule.repository;

import com.parchenegar.capsule.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long>
{
}
