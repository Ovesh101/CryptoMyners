package com.project.cartel.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.cartel.entity.Enums.Roles;
import com.project.cartel.entity.Machines;
import com.project.cartel.entity.User;
import com.project.cartel.entity.UserMachine;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long user_id;
    private String first_name;
    private String last_name;
    private String password;
    private Long  phone_number;
    private String email;
    private String self_referral_code;
    private String invited_referral_code;
    private Roles role;
    private int aadhaar_number;
    private String pan_card;
    private String upi_id;
    private Long account_no;
    private String bank_name;
    private String ifsc_code;
//    private List<User> invites_users;
    private int total_deposited_amount;
    private int total_interest_earned;
    private int total__referral_earned;
    private int referral_amount_withdraw;
    private int available_to_withdraw;
    private LocalDate joined_date;
    private List<UserMachine> user_machines;

}
