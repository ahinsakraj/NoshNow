package com.seavol.NoshNow.model;

import com.seavol.NoshNow.Enum.CouponType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "coupon")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coupon {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @Column(name = "name")
    String name;

    @Column(name = "applicable_value", nullable = false)
    double applicableValue;

    @Column(name = "coupon_type", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    CouponType couponType;

    @Column(name = "percent_off")
    int percentOff;

    @Column(name = "cashback")
    int cashback;

    @Column(name = "flat_off")
    int flatOff;

    @Column(name = "expired")
    boolean expired;
}
