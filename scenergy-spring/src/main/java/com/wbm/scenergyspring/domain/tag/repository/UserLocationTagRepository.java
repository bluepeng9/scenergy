package com.wbm.scenergyspring.domain.tag.repository;

import com.wbm.scenergyspring.domain.user.entity.UserLocationTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationTagRepository extends JpaRepository<UserLocationTag,Long> {
}
