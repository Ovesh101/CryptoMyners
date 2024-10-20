package com.project.cartel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "machines")
public class Machines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machine_id;
    private String machine_name;
    private Float price;
    private int valid_days;
    private int interest_per_day;
    private String url;
    private LocalDate start_date;
    private LocalDate end_date;

   @OneToMany(mappedBy = "machine",cascade = CascadeType.ALL)
   @JsonBackReference
    private List<UserMachine> user_machines;

}
