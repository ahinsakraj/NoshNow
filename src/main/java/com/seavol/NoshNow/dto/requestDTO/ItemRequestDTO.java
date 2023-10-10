package com.seavol.NoshNow.dto.requestDTO;

import com.seavol.NoshNow.Enum.FoodCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDTO {
    int restaurantId;

    String dishName;

    double price;

    FoodCategory foodCategory;

    boolean veg;

    boolean available;
}
