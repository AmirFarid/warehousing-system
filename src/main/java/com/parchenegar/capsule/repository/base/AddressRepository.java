package com.parchenegar.capsule.repository.base;

import com.parchenegar.capsule.domain.base.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{
}
