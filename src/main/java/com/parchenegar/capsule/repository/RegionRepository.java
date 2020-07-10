package com.parchenegar.capsule.repository;

import com.parchenegar.capsule.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>
{
}
