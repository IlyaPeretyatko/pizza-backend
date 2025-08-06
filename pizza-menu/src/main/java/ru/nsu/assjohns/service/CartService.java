package ru.nsu.assjohns.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.assjohns.dto.CartPatchRequest;
import ru.nsu.assjohns.dto.CartPostRequest;
import ru.nsu.assjohns.dto.CartResponse;
import ru.nsu.assjohns.error.exception.ServiceException;
import ru.nsu.assjohns.mapper.CartMapper;
import ru.nsu.assjohns.entity.Cart;
import ru.nsu.assjohns.dao.CartRepository;
import ru.nsu.assjohns.grpc.UserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    @Transactional(readOnly = true)
    public List<CartResponse> getCartItemsByUserId(Long userId) {
        long id = ((UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (userId != id) {
            throw new ServiceException(401, "Unauthorized.");
        }
        return cartRepository.findByUserId(userId).stream().map(cartMapper::toCartResponse).toList();
    }

    @Transactional
    public void addItemToCart(CartPostRequest cartPostRequest) {
        long id = ((UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (cartPostRequest.getUserId() != id) {
            throw new ServiceException(401, "Unauthorized.");
        }
        Cart cart = cartMapper.toCart(cartPostRequest);
        cartRepository.save(cart);
    }

    @Transactional
    public void updateCartItem(long id, CartPatchRequest cartPatchRequest) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ServiceException(404, "Cart item not found."));
        long authenticationId = ((UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (cart.getUserId() != authenticationId) {
            throw new ServiceException(401, "Unauthorized.");
        }
        cartMapper.updateCart(cart, cartPatchRequest);
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public boolean existsByMenuIdAndUserId(long menuId, long userId) {
        return cartRepository.existsByMenuIdAndUserId(menuId, userId);
    }
}
