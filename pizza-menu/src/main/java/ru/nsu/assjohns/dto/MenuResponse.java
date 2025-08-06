package ru.nsu.assjohns.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuResponse {

    private Long id;

    private String name;

    private String description;

    private Double price;
}
