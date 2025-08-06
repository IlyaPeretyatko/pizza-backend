package ru.nsu.assjohns.dto;

import lombok.*;

@Data
public class MenuPatchRequest {
    private String name;

    private String description;

    private Double price;
}
