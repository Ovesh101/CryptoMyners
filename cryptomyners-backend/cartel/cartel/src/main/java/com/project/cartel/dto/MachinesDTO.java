package com.project.cartel.dto;

import com.project.cartel.entity.User;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachinesDTO {
    private Long machine_id;
    private String machine_name;
    private Float  price;
    private int valid_days;
    private String url;
    private int interest_per_day;
}
