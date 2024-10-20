package com.project.cartel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_referral_amount_earned")
public class UserReferralAmountEarned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private Long user_id;
    private Long  user_phone;
    private Long  referred_userid;
    private String referred_username;
    private Long first_deposit;
    private double referral_amount;
    private LocalDateTime  reffered_date;
}
