package com.seavol.NoshNow.model;

import com.seavol.NoshNow.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Size(min = 2, message = "name is too short")
    @Size(max = 50, message = "name is too long")
    @Column(name = "name")
    String name;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Email
    @Column(unique = true, name = "email")
    String email;

    @Column(unique = true, nullable = false, name = "mobile_no")
    @Size(min = 10, max = 10)
    String mobileNo;

    @Column(name = "address")
    @Size(min = 10, max = 100)
    String address;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<OrderEntity> orderEntityList = new ArrayList<>();
}
