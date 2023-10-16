package com.seavol.NoshNow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_entity")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "oder_id")
    String orderId;

    @Column(name = "order_total")
    double orderTotal;

    @CreationTimestamp
    @Column(name = "order_time")
    Date orderTime;

    @ManyToOne
    @JoinColumn
    Customer customer;

    @ManyToOne
    @JoinColumn
    DeliveryPartner deliveryPartner;

    @ManyToOne
    @JoinColumn
    Restaurant restaurant;

    @ManyToOne
    @JoinColumn
    Coupon coupon;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    List<Food> foodList = new ArrayList<>();
}
