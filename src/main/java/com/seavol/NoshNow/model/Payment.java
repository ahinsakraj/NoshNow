package com.seavol.NoshNow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "payment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "date")
    @CreationTimestamp
    Date date;

    @ManyToOne
    @JoinColumn
    Coupon coupon;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    OrderEntity orderEntity;
}
