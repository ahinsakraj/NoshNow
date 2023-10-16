package com.seavol.NoshNow.dto.responseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppliedCouponResponseDTO {
    String message;
    double orderTotal;
    String coupon;
    double discount;
    double grandTotal;
}
