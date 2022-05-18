package com.htecgroup.SkyNest.repository;

import com.htecgroup.SkyNest.model.enitity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findUserByEmail(String email);
}
