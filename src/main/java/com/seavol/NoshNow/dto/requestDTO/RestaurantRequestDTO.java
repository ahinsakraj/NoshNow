package com.seavol.NoshNow.dto.requestDTO;

import com.seavol.NoshNow.Enum.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDTO {

    String name;

    String location;

    RestaurantCategory restaurantCategory;

    String contactNumber;
}
