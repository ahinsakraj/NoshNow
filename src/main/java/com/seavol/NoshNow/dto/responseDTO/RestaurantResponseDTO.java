package com.seavol.NoshNow.dto.responseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDTO {

    String name;

    String contactNumber;

    String location;

    boolean opened;

    List<ItemResponseDTO> itemResponseDTOList;

}
