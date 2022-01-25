package com.parchenegar.capsule.repository.base;

import com.parchenegar.capsule.domain.media.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>
{
}
