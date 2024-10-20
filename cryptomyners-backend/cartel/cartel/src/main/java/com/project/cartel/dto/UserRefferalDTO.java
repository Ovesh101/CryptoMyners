package com.project.cartel.dto;

import com.project.cartel.entity.Enums.Roles;
import com.project.cartel.entity.Machines;
import com.project.cartel.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRefferalDTO {
    private Long user_id;
    private int total__referral_earned;
    private int referral_amount_withdraw;
//    private List<User> invites_users;

}
