package kz.narxoz.monamieproject.repositories;

import kz.narxoz.monamieproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
