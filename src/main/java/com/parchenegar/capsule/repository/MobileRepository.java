package com.parchenegar.capsule.repository;

import com.parchenegar.capsule.domain.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileRepository extends JpaRepository<Mobile, Long>
{
}
