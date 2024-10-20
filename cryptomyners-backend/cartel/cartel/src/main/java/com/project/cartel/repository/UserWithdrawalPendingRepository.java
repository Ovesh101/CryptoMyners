package com.project.cartel.repository;

import com.project.cartel.entity.UserReferralAmountEarned;
import com.project.cartel.entity.UserWithdrawalPending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWithdrawalPendingRepository extends JpaRepository<UserWithdrawalPending,Integer> {
    @Query(value = "SELECT u FROM UserWithdrawalPending u  WHERE u.user_id =:user_id")
    public List<UserWithdrawalPending> getSinglePendingWithdrawals(@Param("user_id") int user_id);

}
