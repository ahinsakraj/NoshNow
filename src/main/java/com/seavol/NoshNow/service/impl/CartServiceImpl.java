package com.seavol.NoshNow.service.impl;

import com.seavol.NoshNow.dto.responseDTO.CartResponseDTO;
import com.seavol.NoshNow.exception.InvalidQuantityException;
import com.seavol.NoshNow.exception.ItemNotFoundException;
import com.seavol.NoshNow.model.Cart;
import com.seavol.NoshNow.model.Customer;
import com.seavol.NoshNow.model.Food;
import com.seavol.NoshNow.model.Item;
import com.seavol.NoshNow.repository.CartRepository;
import com.seavol.NoshNow.repository.FoodRepository;
import com.seavol.NoshNow.repository.ItemRepository;
import com.seavol.NoshNow.service.CartService;
import com.seavol.NoshNow.transformer.CartTransformer;
import com.seavol.NoshNow.transformer.FoodTransformer;
import com.seavol.NoshNow.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    final CartRepository cartRepository;
    final ValidationUtils validationUtils;
    final ItemRepository itemRepository;
    final FoodRepository foodRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           ValidationUtils validationUtils,
                           ItemRepository itemRepository, FoodRepository foodRepository) {
        this.cartRepository = cartRepository;
        this.validationUtils = validationUtils;
        this.itemRepository = itemRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    public CartResponseDTO modifyCart(String mobile, int ItemId, boolean add) {
        // Validate Customer and Item
        Customer customer = validationUtils.validateCustomer(mobile);
        Item item = validationUtils.validateItem(ItemId);

        // Get Cart and Food List
        Cart cart = customer.getCart();
        List<Food> foodList = cart.getFoodList();
        
        if(add) {
            return addToCart(cart, item, foodList);
        } else {
            return removeFromCart(cart, item, foodList);
        }
    }

    private CartResponseDTO addToCart(Cart cart, Item item, List<Food> foodList) {
        if(!item.getRestaurant().isOpened()) {
            throw new ItemNotFoundException("This restaurant is closed");
        }

        // Empty Cart if this Item's Restaurant is different
        validateRestaurant(item, foodList, cart);
        // Increase Quantity if this Item already Exist
        Food food = doesExist(foodList, item.getId());
        if(food != null) {
            int quantity = food.getQuantity();
            if(quantity >= 10) {
                throw new InvalidQuantityException("Sorry : We don't serve more than 10 orders at once.");
            }
            food.setQuantity(quantity + 1);
            cart.setCartTotal(calculateCartTotal(foodList));
            cartRepository.save(cart);
            return CartTransformer.cartTocartResponseDTO(cart);
        }

        // Prepare food by item
        food = FoodTransformer.ItemToFood(item);
        food.setCart(cart); // Set cart
        Food savedFood = foodRepository.save(food);
        foodList.add(savedFood); // add food to cartList
        item.getFoodList().add(savedFood); // add food to itemList
        cart.setCartTotal(calculateCartTotal(foodList));
        cartRepository.save(cart);
        return CartTransformer.cartTocartResponseDTO(cart);
    }

    private CartResponseDTO removeFromCart(Cart cart, Item item, List<Food> foodList) {
        Food food = doesExist(foodList, item.getId());
        if(food == null) {
            throw new InvalidQuantityException("You can't remove food that hasn't added to your cart");
        }
        int quantity = food.getQuantity();
        quantity = quantity - 1;
        food.setQuantity(quantity);
        if(quantity <= 0) {
            foodList.remove(food); // removes from the list
            foodRepository.deleteById(food.getId());  // removes from the database
        }
        cart.setFoodList(foodList);
        cart.setCartTotal(calculateCartTotal(foodList));
        cartRepository.save(cart);
        return CartTransformer.cartTocartResponseDTO(cart);
    }

    private Food doesExist(List<Food> foodList, int ItemId) {
        for(Food food : foodList) {
            if(food.getItem().getId() == ItemId) {
                return food;
            }
        }
        return null;
    }
    
    private void validateRestaurant(Item item, List<Food> foodList, Cart cart) {
        if(foodList.isEmpty()) {
            return;
        }
        if(foodList.get(0).getItem().getRestaurant().getId() != item.getRestaurant().getId()) {
            foodRepository.deleteAllInBatch(foodList);
            foodList.clear();
        }
        cart.setCartTotal(0);
        cartRepository.save(cart);
    }

    public CartResponseDTO getCart(String mobile) {
        Customer customer = validationUtils.validateCustomer(mobile);
        return CartTransformer.cartTocartResponseDTO(customer.getCart());
    }


    public double calculateCartTotal(List<Food> foodList) {
        double total = 0;
        for(Food food : foodList) {
            total += (food.getQuantity() * food.getItem().getPrice());
        }
        return total;
    }
}
