package com.project.cartel.repository;

import com.project.cartel.entity.UserInterestEarned;
import com.project.cartel.entity.UserWithdrawalPending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInterestEarnedRepository extends JpaRepository<UserInterestEarned,Long> {
    @Query(value = "SELECT u FROM UserInterestEarned u  WHERE u.user_id =:user_id")
    public List<UserInterestEarned> getUserInterestEarned(@Param("user_id") int user_id);
}
