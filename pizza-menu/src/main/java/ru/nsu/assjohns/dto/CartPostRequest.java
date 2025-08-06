package ru.nsu.assjohns.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class CartPostRequest {
    @NotNull(message = "User id cannot be null.")
    private Long userId;

    @NotNull(message = "Menu id cannot be null.")
    private Long menuId;

    @NotNull(message = "Quantity cannot be null.")
    private Integer quantity = 1;
}
