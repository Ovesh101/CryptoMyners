package com.project.cartel.repository;

import com.project.cartel.entity.QRcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRcodeRepository extends JpaRepository<QRcode,Integer> {
}
