package com.seavol.NoshNow.service.impl;

import com.seavol.NoshNow.dto.requestDTO.DeliveryPartnerRequestDTO;
import com.seavol.NoshNow.model.DeliveryPartner;
import com.seavol.NoshNow.repository.DeliveryPartnerRepository;
import com.seavol.NoshNow.service.DeliveryPartnerService;
import com.seavol.NoshNow.transformer.DeliveryPartnerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    final DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    public DeliveryPartnerServiceImpl(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    @Override
    public String addDeliverPartner(DeliveryPartnerRequestDTO deliveryPartnerRequestDTO) {
        // Prepare the Driver by request and save it
        DeliveryPartner deliveryPartner = DeliveryPartnerTransformer.DeliveryPartnerRequestToDeliveryPartner(deliveryPartnerRequestDTO);
        DeliveryPartner savedDeliveryPartner = deliveryPartnerRepository.save(deliveryPartner);
        return "You have Registered Successfully";
    }
}
