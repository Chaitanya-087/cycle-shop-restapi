package com.api.cycleshop.dto;

import java.util.List;

import com.api.cycleshop.entity.CartItem;

import lombok.Data;

@Data
public class CartDTO {
    private int id;
    private int totalQuantity;
    private int totalPrice;
    private List<CartItem> cartItems;
}
