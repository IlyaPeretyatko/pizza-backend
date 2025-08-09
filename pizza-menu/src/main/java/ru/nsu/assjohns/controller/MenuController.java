package ru.nsu.assjohns.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nsu.assjohns.dto.MenuPatchRequest;
import ru.nsu.assjohns.dto.MenuPostRequest;
import ru.nsu.assjohns.dto.MenuResponse;
import ru.nsu.assjohns.service.MenuService;
import ru.nsu.assjohns.validator.menu.MenuValidator;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Tag(name = "Menu API")
public class MenuController {

    private final MenuService menuService;

    private final MenuValidator menuValidator;

    @GetMapping
    @Operation(
            summary = "Get menu positions",
            description = "Returns menu positions",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
            }
    )
    public List<MenuResponse> getMenuPositions() {
        return menuService.getMenuPositions();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get menu position by ID",
            description = "Returns menu position",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Menu position was not found", content = @Content())
            }
    )
    public MenuResponse getMenuPosition(@PathVariable Long id) {
        return menuService.getMenuPosition(id);
    }

    @PostMapping
    @Operation(
            summary = "Create menu position",
            description = "Returns status code",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Validation exception", content = @Content())
            }
    )
    public void addMenuPosition(@Valid @RequestBody MenuPostRequest menuPostRequest,
                                BindingResult bindingResult) {
        menuValidator.validate(menuPostRequest, bindingResult);
        menuService.addMenuPosition(menuPostRequest);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update menu position by ID",
            description = "Returns status code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Validation exception", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Menu position was not found", content = @Content())
            }
    )
    public void updateMenuPosition(@PathVariable Long id,
                                   @Valid @RequestBody MenuPatchRequest menuPatchRequest,
                                   BindingResult bindingResult) {
        menuValidator.validate(menuPatchRequest, bindingResult);
        menuService.updateMenuPosition(id, menuPatchRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete menu position by ID",
            description = "Returns status code",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Menu position was not found", content = @Content())
            }
    )
    public void deleteMenuPosition(@PathVariable Long id) {
        menuService.deleteMenuPosition(id);
    }
}
