package ru.nsu.assjohns.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cart API")
public class CartController {

    private final CartService cartService;

    private final CartValidator cartValidator;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get cart items by User ID",
            description = "Returns cart items",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
            }
    )
    public List<CartResponse> getCartItemsByUserId(@PathVariable Long id) {
        return cartService.getCartItemsByUserId(id);
    }

    @PostMapping
    @Operation(
            summary = "Add item to cart",
            description = "Returns status code",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Validation exception", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
            }
    )
    public void addItemToCart(@Valid @RequestBody CartPostRequest cartPostRequest,
                              BindingResult bindingResult) {
        cartValidator.validate(cartPostRequest, bindingResult);
        cartService.addItemToCart(cartPostRequest);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update item in cart by ID",
            description = "Returns status code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Validation exception", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Cart item was not found", content = @Content())
            }
    )
    public void updateCartItem(@PathVariable  long id,
                               @Valid @RequestBody CartPatchRequest cartPatchRequest,
                               BindingResult bindingResult) {
        cartValidator.validate(cartPatchRequest, bindingResult);
        cartService.updateCartItem(id, cartPatchRequest);
    }
}
