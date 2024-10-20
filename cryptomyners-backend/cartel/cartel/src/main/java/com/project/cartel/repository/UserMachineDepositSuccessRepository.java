package com.project.cartel.repository;

import com.project.cartel.dto.DepositSuccessDTO;
import com.project.cartel.entity.UserMachineDepositSuccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMachineDepositSuccessRepository extends JpaRepository<UserMachineDepositSuccess,Integer> {

    @Query(value ="SELECT u FROM UserMachineDepositSuccess u WHERE u.user_id = :id" )
    public List<UserMachineDepositSuccess> getAllSuccessList(@Param("id") int id);
}
