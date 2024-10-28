package org.example.storetoolproject.repositories;

import org.example.storetoolproject.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.id =:id and p.status <> 'DELETED'")
    Optional<Product> findProductById(int id);

}
