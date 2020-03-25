package com.practice.microservices.service;

import java.math.BigDecimal;
import java.util.Collection;

import com.practice.microservices.dto.OrderResponseDto;
import com.practice.microservices.model.Cart;
import com.practice.microservices.model.CartItem;
import com.practice.microservices.model.Product;

public interface CartService {

	

	

	public Cart removeItem(long userId, Product product);

	public int getItemCount(long userId);

	public BigDecimal getTotalCartAmount(long userId);

	public Cart getOrCreateCart(long userId);

	public boolean clearItems(long userId);

	public Cart addOneProduct(long userId, Product product);
	public Cart removeOneProduct(long userId, Product product);
	
	public Cart addCartItems(long userId,Collection<CartItem> cartItems) ;

	public Cart updateItemQuantity(long userId, Product product, int quantity);

	OrderResponseDto placeOrder(long userId);

}
