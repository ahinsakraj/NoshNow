package com.seavol.NoshNow.dto.responseDTO;

import com.seavol.NoshNow.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    String name;

    Gender gender;

    String email;

    String mobileNo;

    String address;

}
