package com.seavol.NoshNow.service;

import com.seavol.NoshNow.dto.responseDTO.CartResponseDTO;

public interface CartService {
    CartResponseDTO modifyCart(String mobile, int menuFoodId, boolean add);

    CartResponseDTO getCart(String mobile);

}
