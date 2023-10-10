package com.seavol.NoshNow.dto.responseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {

    String customerName;

    String customerMobile;

    double cartTotal;

    List<FoodResponseDTO> foodBookingList = new ArrayList<>();
}
