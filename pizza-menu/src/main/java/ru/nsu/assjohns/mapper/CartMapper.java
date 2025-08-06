package ru.nsu.assjohns.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.nsu.assjohns.dto.CartPatchRequest;
import ru.nsu.assjohns.dto.CartPostRequest;
import ru.nsu.assjohns.dto.CartResponse;
import ru.nsu.assjohns.entity.Cart;
import ru.nsu.assjohns.error.exception.ServiceException;
import ru.nsu.assjohns.service.CartService;
import ru.nsu.assjohns.service.MenuService;

@Component
public class CartMapper {

    private final MenuService menuService;

    private final CartService cartService;

    @Autowired
    public CartMapper(MenuService menuService, @Lazy CartService cartService) {
        this.menuService = menuService;
        this.cartService = cartService;
    }


    public CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .menuId(cart.getMenuId())
                .userId(cart.getUserId())
                .quantity(cart.getQuantity())
                .build();
    }

    public Cart toCart(CartPostRequest cartPostRequest) {
        Cart cart = new Cart();
        if (!menuService.existsMenuPosition(cartPostRequest.getMenuId())) {
            throw new ServiceException(404, "Menu not found.");
        }
        if (cartService.existsByMenuIdAndUserId(cartPostRequest.getMenuId(), cartPostRequest.getUserId())) {
            throw new ServiceException(400, "Item already exists.");
        }
        cart.setUserId(cartPostRequest.getUserId());
        cart.setMenuId(cartPostRequest.getMenuId());
        cart.setQuantity(cartPostRequest.getQuantity());
        return cart;
    }

    public void updateCart(Cart cart, CartPatchRequest cartPatchRequest) {
        if (cartPatchRequest.getQuantity() != null) {
            cart.setQuantity(cartPatchRequest.getQuantity());
        }
    }
}
