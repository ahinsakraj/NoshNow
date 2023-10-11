package com.seavol.NoshNow.dto.requestDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponRequestDTO {
    String name;

    double applicableValue;
}
