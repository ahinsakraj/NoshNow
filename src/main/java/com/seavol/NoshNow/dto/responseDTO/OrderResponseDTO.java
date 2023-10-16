package com.seavol.NoshNow.dto.responseDTO;

import com.seavol.NoshNow.model.Food;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponseDTO {

    String customerName;

    String restaurantName;

    List<FoodResponseDTO> foodResponseDTOList = new ArrayList<>();

    double orderTotal;

    String coupon;

    double discount;

    double grandTotal;

    String orderId;
}
