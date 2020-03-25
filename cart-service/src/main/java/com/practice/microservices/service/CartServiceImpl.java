package com.practice.microservices.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.microservices.dto.OrderResponseDto;
import com.practice.microservices.dto.UserServiceBean;
import com.practice.microservices.model.Cart;
import com.practice.microservices.model.CartItem;
import com.practice.microservices.model.Product;
import com.practice.microservices.proxy.ProductServiceProxy;
import com.practice.microservices.proxy.UserServiceProxy;
import com.practice.microservices.repository.CartRepository;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	CartItemService cartItemService;

	@Autowired
	ProductService productService;

	@Autowired
	CartRepository cartrepo;

	@Autowired
	ProductServiceProxy productServiceProxy;
	
	

	private ModelMapper modelMapper;

	@Override
	public boolean clearItems(long userId) {
		// TODO Auto-generated method stub
		UserServiceBean userServiceBean = productServiceProxy.userStatus(userId);
		if (userServiceBean == null || !userServiceBean.getStatus().equals("LOGIN"))
			return false;
		cartrepo.deleteByUserId(userId);
		Cart deletedCart = cartrepo.findByUserId(userId);
		if (deletedCart == null)
			return true;

		return false;
	}

	@Override
	public Cart getOrCreateCart(long userId) {

		Cart cart = cartrepo.findByUserId(userId);
		if (cart != null)
			return cart;
		Collection<CartItem> items = new ArrayList<CartItem>();
		cart = new Cart(items, userId);

		return cartrepo.save(cart);

	}

	@Override
	public int getItemCount(long userId) {
		int quantity = 0;
		Cart cart = getOrCreateCart(userId);
		Collection<CartItem> cartItemList = cart.getItems();
		for (CartItem itrCartItem : cartItemList) {
			quantity = quantity + itrCartItem.getQuantity();
		}

		return quantity;

	}

	@Override
	public Cart removeItem(long userId, Product product) {
		Cart cart = getOrCreateCart(userId);
		CartItem cartItem = null;
		for (CartItem itrcartItem : cart.getItems()) {
			if (itrcartItem.getProduct().getId() == product.getId()) {
				cartItem = itrcartItem;
				break;
			}

		}
		Collection<CartItem> itemList = cart.getItems();
		itemList.remove(cartItem);
//		cart.setItems(itemList);

		return cartrepo.save(cart);

	}

	@Override
	public BigDecimal getTotalCartAmount(long userId) {
		BigDecimal amount = new BigDecimal("0.0");
		Cart cart = getOrCreateCart(userId);
		Collection<CartItem> cartItemList = cart.getItems();
		for (CartItem itrCartItem : cartItemList) {
			amount = amount.add(cartItemService.getSubTotal(itrCartItem));
		}
		// TODO Auto-generated method stub
		return amount;
	}

	@Override
	public Cart addOneProduct(long userId, Product product) {
		UserServiceBean userServiceBean = productServiceProxy.userStatus(userId);
		if (userServiceBean == null || !userServiceBean.getStatus().equals("LOGIN"))
			return null;
		long productId = product.getId();
		List<Product> productList = new ArrayList<Product>();
		productList.add(product);
		List<Product> productListinDb = fetchProducts(productList);

		if (!productListinDb.stream().map(item -> item.getId()).collect(Collectors.toList())
				.contains(product.getId())) {
			return null;
		}
		Cart cart = getOrCreateCart(userId);
		Collection<CartItem> dbCartItemList = cart.getItems();
		boolean productAvailable = false;

		for (CartItem itrCartItem : dbCartItemList) {
			if (itrCartItem.getProduct().getId() == product.getId()) {
				itrCartItem.setQuantity(itrCartItem.getQuantity() + 1);
				productAvailable = true;
				break;
			}
		}

		if (!productAvailable) {
			dbCartItemList.add(new CartItem(product, 1));
		}
		cart.setItems(dbCartItemList);

		return cartrepo.save(cart);
	}

	@Override
	public Cart addCartItems(long userId, Collection<CartItem> cartItems) {

		UserServiceBean userServiceBean = productServiceProxy.userStatus(userId);
		if (userServiceBean == null || !userServiceBean.getStatus().equals("LOGIN"))
			return null;
		List<Long> requestProductIds = cartItems.stream().map(item -> item.getProduct().getId())
				.collect(Collectors.toList());
		List<Product> productListinDb = fetchCartItemProducts(cartItems.stream().collect(Collectors.toList()));

		if (!productListinDb.stream().map(item -> item.getId()).collect(Collectors.toList())
				.containsAll(requestProductIds)) {
			return null;
		}
		boolean productAvailable = false;
		Cart cart = getOrCreateCart(userId);
		Collection<CartItem> dbCartItemList = cart.getItems();

		for (CartItem itrCartItem : cartItems) {
			productAvailable = false;
			for (CartItem itrDbCartItem : dbCartItemList) {
				if (itrCartItem.getProduct().getId() == itrDbCartItem.getProduct().getId()) {
					itrDbCartItem.setQuantity(itrCartItem.getQuantity() + itrDbCartItem.getQuantity());
					productAvailable = true;
					break;
				}
			}
			if (!productAvailable) {
				dbCartItemList.add(itrCartItem);
			}

		}

		cart.setItems(dbCartItemList);
		return cartrepo.save(cart);
	}

	@Override
	public Cart updateItemQuantity(long userId, Product product, int quantity) {
		// TODO Auto-generated method stub
		Cart cart = getOrCreateCart(userId);
		Collection<CartItem> cartItemList = cart.getItems();

		for (CartItem itrCartItem : cartItemList) {
			if (itrCartItem.getProduct().getId() == product.getId()) {
				itrCartItem.setQuantity(itrCartItem.getQuantity() + quantity);
				break;
			}
		}
		cart.setItems(cartItemList);

		return cartrepo.save(cart);
	}

	@Override
	public Cart removeOneProduct(long userId, Product product) {
		UserServiceBean userServiceBean = productServiceProxy.userStatus(userId);
		if (userServiceBean == null || !userServiceBean.getStatus().equals("LOGIN"))
			return null;
		// TODO Auto-generated method stub
		Cart cart = getOrCreateCart(userId);
		Collection<CartItem> cartItemList = cart.getItems();

		for (CartItem itrCartItem : cartItemList) {
			if (itrCartItem.getProduct().getId() == product.getId()) {
				if (itrCartItem.getQuantity() == 1) {
					cart.getItems().remove(itrCartItem);
				} else
					itrCartItem.setQuantity(itrCartItem.getQuantity() - 1);
				break;
			}
		}
		cart.setItems(cartItemList);

		return cartrepo.save(cart);
	}

	public List<Product> fetchCartProducts(Cart cart) {

		List<Long> requestProductIds = cart.getItems().stream().map(item -> item.getProduct().getId())
				.collect(Collectors.toList());
		List<Product> productList = productService.getAllProductsByIds(requestProductIds);
		return productList;
	}

	public List<Product> fetchCartItemProducts(List<CartItem> cartItemList) {

		List<Long> requestProductIds = cartItemList.stream().map(item -> item.getProduct().getId())
				.collect(Collectors.toList());
		List<Product> productList = productService.getAllProductsByIds(requestProductIds);
		return productList;
	}

	public List<Product> fetchProducts(List<Product> productList) {

		List<Long> requestProductIds = productList.stream().map(item -> item.getId()).collect(Collectors.toList());
		List<Product> responseProductList = productService.getAllProductsByIds(requestProductIds);
		return responseProductList;
	}

	@Override
	public OrderResponseDto placeOrder(long userId) {

		UserServiceBean userServiceBean = productServiceProxy.userStatus(userId);
		if (userServiceBean == null || !userServiceBean.getStatus().equals("LOGIN"))
			return null;
		Cart cart = getOrCreateCart(userId);
		if (cart.getItems().size() < 1)
			return null;

		modelMapper = new ModelMapper();
		OrderResponseDto orderResponse = modelMapper.map(cart, OrderResponseDto.class);
		orderResponse.setTotalAmount(cart.getTotalCartAmount());

		/*
		 * if(orderResponse.getTotalAmount().intValue()>0) { clearItems(userId); }
		 */

		return orderResponse;
	}

}
