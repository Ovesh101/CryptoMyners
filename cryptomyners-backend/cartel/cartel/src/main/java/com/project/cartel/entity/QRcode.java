package com.project.cartel.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="qrcode")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "qrcode_id")
public class QRcode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qrcode_id;
    private String upi_id;
    private String qrcode_image;
    private String bank_name;
    private String owner_name;
    private Boolean isactive;
}
