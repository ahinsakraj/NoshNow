package com.seavol.NoshNow.dto.responseDTO;

import com.seavol.NoshNow.Enum.CouponType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponResponseDTO {
    String name;

    double applicableValue;

    CouponType couponType;

    boolean expired;

    String message;
}
