package com.seavol.NoshNow.repository;

import com.fasterxml.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import com.seavol.NoshNow.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Integer> {
    String findRandomDP = "select dp from DeliveryPartner dp order by RAND() LIMIT 1";

    @Query(value = findRandomDP)
    DeliveryPartner findRandomDeliveryPartner();
}
