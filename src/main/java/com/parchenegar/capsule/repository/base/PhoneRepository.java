package com.parchenegar.capsule.repository.base;

import com.parchenegar.capsule.domain.base.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long>
{
}
