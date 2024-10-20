package com.project.cartel.dto;

import com.project.cartel.entity.Enums.Roles;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRefferalChildDTO {
    private Long user_id;
    private String first_name;
    private String last_name;
    private LocalDate joined_date;
}
