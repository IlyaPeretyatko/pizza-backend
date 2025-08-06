package ru.nsu.assjohns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nsu.assjohns.dto.CartPatchRequest;
import ru.nsu.assjohns.dto.CartPostRequest;
import ru.nsu.assjohns.dto.CartResponse;
import ru.nsu.assjohns.service.CartService;
import ru.nsu.assjohns.validator.cart.CartValidator;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CartValidator cartValidator;

    @GetMapping("/{id}")
    public List<CartResponse> getCartItemsByUserId(@PathVariable Long id) {
        return cartService.getCartItemsByUserId(id);
    }

    @PostMapping
    public void addItemToCart(@Valid @RequestBody CartPostRequest cartPostRequest,
                              BindingResult bindingResult) {
        cartValidator.validate(cartPostRequest, bindingResult);
        cartService.addItemToCart(cartPostRequest);
    }

    @PatchMapping("/{id}")
    public void updateCartItem(@PathVariable  long id,
                               @RequestBody CartPatchRequest cartPatchRequest) {
        cartService.updateCartItem(id, cartPatchRequest);
    }
}
