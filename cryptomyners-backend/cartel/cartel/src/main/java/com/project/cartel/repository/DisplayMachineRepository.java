package com.project.cartel.repository;

import com.project.cartel.entity.DisplayMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplayMachineRepository extends JpaRepository<DisplayMachine,Long> {
}
