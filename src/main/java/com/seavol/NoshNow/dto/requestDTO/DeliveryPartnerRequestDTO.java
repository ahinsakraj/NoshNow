package com.seavol.NoshNow.dto.requestDTO;

import com.seavol.NoshNow.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerRequestDTO {

    String name;

    String mobileNo;

    Gender gender;
}
