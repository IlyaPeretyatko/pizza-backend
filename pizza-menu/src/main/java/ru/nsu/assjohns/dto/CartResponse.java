package ru.nsu.assjohns.dto;

import lombok.*;

@Data
@Builder
public class CartResponse {

    private Long id;

    private Long userId;

    private Long menuId;

    private Integer quantity;
}
