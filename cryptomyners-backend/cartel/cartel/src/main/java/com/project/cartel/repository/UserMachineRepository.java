package com.project.cartel.repository;

import com.project.cartel.entity.Machines;
import com.project.cartel.entity.UserMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMachineRepository extends JpaRepository<UserMachine,Long> {

    @Query("SELECT um.machine FROM UserMachine um WHERE um.user.user_id = :userId")
    List<Machines> findMachinesByUserId(@Param("userId") Long userId);
}
