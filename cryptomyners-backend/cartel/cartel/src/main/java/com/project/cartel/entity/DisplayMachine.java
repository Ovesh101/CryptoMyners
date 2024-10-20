package com.project.cartel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "display_machines")
public class DisplayMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machine_id;
    private String machine_name;
    private Float price;
    private int valid_days;
    private int interest_per_day;
    private String url;
    private String description;
}
