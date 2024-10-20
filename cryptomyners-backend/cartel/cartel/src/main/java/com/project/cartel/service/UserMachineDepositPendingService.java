package com.project.cartel.service;

import com.project.cartel.repository.UserMachineDepositPendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMachineDepositPendingService {
    @Autowired
    private UserMachineDepositPendingRepository userMachineDepositPendingRepository;
}
