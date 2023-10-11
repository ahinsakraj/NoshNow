package com.seavol.NoshNow.model;

import com.seavol.NoshNow.Enum.CouponType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coupon")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coupon {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "applicable_value", nullable = false, unique = true)
    double applicableValue;

    @Column(name = "coupon_type")
    @Enumerated(EnumType.STRING)
    CouponType couponType;

    @Column(name = "percent_off")
    @Min(1)
    @Max(100)
    int percentOff;

    @Column(name = "cashback")
    int cashback;

    @Column(name = "flat_off")
    int flatOff;

    @Column(name = "expired")
    boolean expired;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    List<Payment> paymentList = new ArrayList<>();
}
