package com.project.cartel.repository;

import com.project.cartel.dto.DepositPendingDTO;
import com.project.cartel.entity.UserMachineDepositPending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMachineDepositPendingRepository extends JpaRepository<UserMachineDepositPending,Integer> {

    @Query(value = "SELECT u FROM UserMachineDepositPending u WHERE u.user_id = :id")
    public List<UserMachineDepositPending> getPendingListById(@Param("id") int id);
}
