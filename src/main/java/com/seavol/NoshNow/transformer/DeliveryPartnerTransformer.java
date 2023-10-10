package com.seavol.NoshNow.transformer;

import com.seavol.NoshNow.dto.requestDTO.DeliveryPartnerRequestDTO;
import com.seavol.NoshNow.model.DeliveryPartner;

import java.util.ArrayList;

public class DeliveryPartnerTransformer {

    public static DeliveryPartner DeliveryPartnerRequestToDeliveryPartner(DeliveryPartnerRequestDTO deliveryPartnerRequestDTO) {
        return DeliveryPartner.builder()
                .name(deliveryPartnerRequestDTO.getName())
                .mobileNo(deliveryPartnerRequestDTO.getMobileNo())
                .gender(deliveryPartnerRequestDTO.getGender())
                .orderEntityList(new ArrayList<>())
                .build();
    }
}
