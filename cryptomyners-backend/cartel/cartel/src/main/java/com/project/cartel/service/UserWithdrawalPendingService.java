package com.project.cartel.service;

import com.project.cartel.repository.UserWithdrawalPendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWithdrawalPendingService {
    @Autowired
    private UserWithdrawalPendingRepository userWithdrawalPendingRepository;
}
