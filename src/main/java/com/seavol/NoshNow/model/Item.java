package com.seavol.NoshNow.model;

import com.seavol.NoshNow.Enum.FoodCategory;
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
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "dish_name")
    String dishName;

    @Column(name = "price")
    double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    FoodCategory category;

    @Column(name = "veg")
    boolean veg;

    @Column(name = "available")
    boolean available;

    @ManyToOne
    @JoinColumn
    Restaurant restaurant;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    List<Food> foodList = new ArrayList<>();
}
