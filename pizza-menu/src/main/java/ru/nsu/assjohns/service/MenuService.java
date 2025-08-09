package ru.nsu.assjohns.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.assjohns.dto.MenuPatchRequest;
import ru.nsu.assjohns.dto.MenuPostRequest;
import ru.nsu.assjohns.dto.MenuResponse;
import ru.nsu.assjohns.error.exception.ServiceException;
import ru.nsu.assjohns.mapper.MenuMapper;
import ru.nsu.assjohns.entity.Menu;
import ru.nsu.assjohns.dao.MenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    @Transactional(readOnly = true)
    public List<MenuResponse> getMenuPositions() {
        return menuRepository.findAll().stream().map(menuMapper::toMenuResponse).toList();
    }

    @Transactional(readOnly = true)
    public MenuResponse getMenuPosition(long id) {
        return menuMapper.toMenuResponse(menuRepository.findById(id).orElseThrow(() -> new ServiceException(404, "Menu position was not found.")));
    }

    @Transactional
    public void addMenuPosition(MenuPostRequest menuPostRequest) {
        Menu menu = menuMapper.toMenu(menuPostRequest);
        menuRepository.save(menu);
    }

    @Transactional
    public void updateMenuPosition(long id, MenuPatchRequest menuPatchRequest) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ServiceException(404, "Menu position was not found."));
        menuMapper.updateMenu(menu, menuPatchRequest);
        menuRepository.save(menu);
    }

    @Transactional
    public void deleteMenuPosition(long id) {
        if (!existsMenuPosition(id)) {
            throw new ServiceException(404, "Menu position was not found.");
        }
        menuRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsMenuPosition(long id) {
        return menuRepository.existsById(id);
    }

}
