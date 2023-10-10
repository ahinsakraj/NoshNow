package com.seavol.NoshNow.model;

import jakarta.persistence.*;
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
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "cart_total")
    double cartTotal;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST)
    List<Food> foodList = new ArrayList<>();

    @OneToOne
    @JoinColumn
    Customer customer;
}
