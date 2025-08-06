package ru.nsu.assjohns.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.assjohns.entity.Cart;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    boolean existsByMenuIdAndUserId(Long menuId, Long userId);
}
