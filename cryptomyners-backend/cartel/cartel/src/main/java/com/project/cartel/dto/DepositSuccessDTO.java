package com.project.cartel.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositSuccessDTO {
    private Long id;
    private Long user_id;
    private Long utr_number;
    private Boolean is_success;
}
