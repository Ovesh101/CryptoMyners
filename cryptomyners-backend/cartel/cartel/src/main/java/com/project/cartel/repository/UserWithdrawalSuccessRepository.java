package com.project.cartel.repository;

import com.project.cartel.entity.UserReferralAmountEarned;
import com.project.cartel.entity.UserWithdrawalSuccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWithdrawalSuccessRepository extends JpaRepository<UserWithdrawalSuccess,Integer> {
    @Query(value = "SELECT u FROM UserWithdrawalSuccess u  WHERE u.user_id =:user_id")
    public List<UserWithdrawalSuccess> getSingleUserWithdrawals(@Param("user_id") int user_id);
}
