package ru.nsu.assjohns.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class MenuPostRequest {
    @NotNull(message = "Name cannot be null.")
    private String name;

    @NotNull(message = "Description cannot be null.")
    private String description;

    @NotNull(message = "Price cannot be null.")
    private Double price;
}
