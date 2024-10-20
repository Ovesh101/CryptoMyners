package com.project.cartel.dto;

import com.project.cartel.entity.Enums.Roles;
import com.project.cartel.entity.Machines;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicDetailsDTO {
    private Long user_id;
    private String first_name;
    private String last_name;
    private Long  phone_number;
    private String email;
    private String self_referral_code;
    private int aadhaar_number;
    private String pan_card;
    private String upi_id;
    private Long account_no;
    private String bank_name;
    private String ifsc_code;
    private LocalDate joined_date;
}
