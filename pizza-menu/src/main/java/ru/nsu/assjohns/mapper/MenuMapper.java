package ru.nsu.assjohns.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.assjohns.dto.MenuPatchRequest;
import ru.nsu.assjohns.dto.MenuPostRequest;
import ru.nsu.assjohns.dto.MenuResponse;
import ru.nsu.assjohns.entity.Menu;

@Component
public class MenuMapper {

    public MenuResponse toMenuResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .build();
    }

    public Menu toMenu(MenuPostRequest menuPostRequest) {
        Menu menu = new Menu();
        menu.setName(menuPostRequest.getName());
        menu.setDescription(menuPostRequest.getDescription());
        menu.setPrice(menuPostRequest.getPrice());
        return menu;
    }

    public void updateMenu(Menu menu, MenuPatchRequest menuPatchRequest) {
        if (menuPatchRequest.getName() != null) {
            menu.setName(menuPatchRequest.getName());
        }
        if (menuPatchRequest.getDescription() != null) {
            menu.setDescription(menuPatchRequest.getDescription());
        }
        if (menuPatchRequest.getPrice() != null) {
            menu.setPrice(menuPatchRequest.getPrice());
        }
    }
}
