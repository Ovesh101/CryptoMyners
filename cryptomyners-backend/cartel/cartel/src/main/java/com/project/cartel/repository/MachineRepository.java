package com.project.cartel.repository;

import com.project.cartel.entity.Machines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machines,Integer> {
}
