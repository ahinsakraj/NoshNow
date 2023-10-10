package com.seavol.NoshNow.dto.responseDTO;

import com.seavol.NoshNow.Enum.FoodCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {

    String dishName;

    double price;

    FoodCategory foodCategory;

    boolean veg;
}
