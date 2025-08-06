package ru.nsu.assjohns.controller;

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
public class MenuController {

    private final MenuService menuService;

    private final MenuValidator menuValidator;

    @GetMapping
    public List<MenuResponse> getMenuPositions() {
        return menuService.getMenuPositions();
    }

    @GetMapping("/{id}")
    public MenuResponse getMenuPosition(@PathVariable Long id) {
        return menuService.getMenuPosition(id);
    }

    @PostMapping
    public void addMenuPosition(@Valid @RequestBody MenuPostRequest menuPostRequest,
                                BindingResult bindingResult) {
        menuValidator.validate(menuPostRequest, bindingResult);
        menuService.addMenuPosition(menuPostRequest);
    }

    @PatchMapping("/{id}")
    public void updateMenuPosition(@PathVariable Long id,
                                   @Valid @RequestBody MenuPatchRequest menuPatchRequest) {
        menuService.updateMenuPosition(id, menuPatchRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuPosition(@PathVariable Long id) {
        menuService.deleteMenuPosition(id);
    }
}
