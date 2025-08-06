package ru.nsu.assjohns.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.assjohns.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
