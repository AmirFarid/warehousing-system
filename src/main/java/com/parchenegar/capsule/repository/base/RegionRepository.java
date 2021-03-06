package com.parchenegar.capsule.repository.base;

import com.parchenegar.capsule.domain.address.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>
{
}
