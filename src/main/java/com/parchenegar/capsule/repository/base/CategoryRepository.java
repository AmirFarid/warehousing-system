package com.parchenegar.capsule.repository.base;

import com.parchenegar.capsule.domain.base.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>
{
}
