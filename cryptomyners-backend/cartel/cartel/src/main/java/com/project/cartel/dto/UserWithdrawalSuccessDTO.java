package com.project.cartel.dto;

import com.project.cartel.entity.User;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithdrawalSuccessDTO {
    private Long id;
    private User user;
    private int withdrawal_amount;
    private Boolean is_success;
    private LocalDate withdrawal_date;
}

