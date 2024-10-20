package com.project.cartel.repository;

import com.project.cartel.entity.UserReferralAmountEarned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReferalRepository extends JpaRepository<UserReferralAmountEarned,Integer> {
   @Query(value = "SELECT u FROM UserReferralAmountEarned u  WHERE u.user_id =:user_id")
   public List<UserReferralAmountEarned> getAllReferralsList(@Param("user_id") int user_id);
}
