package com.project.cartel.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeDTO {
    private Long qrcode_id;
    private String upi_id;
    private String qrcode_image;
    private String bank_name;
    private String owner_name;
    private Boolean isactive;
}
