    package com.project.cartel.entity;

    import com.fasterxml.jackson.annotation.*;
    import com.project.cartel.entity.Enums.Roles;
    import jakarta.persistence.*;
    import lombok.*;
    import java.time.LocalDate;
    import java.util.List;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "user")
    @Entity
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long user_id;
        private String first_name;
        private String last_name;
        private String password;
        @Column(unique = true)
        private Long  phone_number;
        private String email;
        private String self_referral_code;
        private String invited_referral_code;
        @Enumerated(EnumType.STRING)
        private Roles role;
        private int aadhaar_number;
        private String pan_card;
        private String upi_id;
        private Long account_no;
        private String bank_name;
        private String ifsc_code;
        @ManyToMany
        @JoinTable(name = "user_connections",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "connection_id"))
        @JsonIgnore
        private List<User> invites_users;
        private int total_deposited_amount;
        private int total_interest_earned;
        private int total__referral_earned;
        private int referral_amount_withdraw;
        private int available_to_withdraw;
        private LocalDate joined_date;

        @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
        @JsonManagedReference
        private List<UserMachine> user_machines;

    }
