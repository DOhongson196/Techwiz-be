package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_Email(String email);

    Cart findByUser_EmailAndProduct_Id(String email, Long id);

    @Query("UPDATE Cart c SET c.quantity = ?1 WHERE c.product.id = ?2 and c.user.id =?3")
    @Modifying
    void updateQuantity(Integer quantity, Long productId, Long userId);

    @Query("DELETE FROM Cart c where c.product.id = ?1 and c.user.id =?2")
    @Modifying
    void deleteByProductAndUser(Long productId, Long userId);

    @Query("DELETE FROM Cart c where  c.user.id =?1")
    @Modifying
    void deleteByUser(Long userId);

}