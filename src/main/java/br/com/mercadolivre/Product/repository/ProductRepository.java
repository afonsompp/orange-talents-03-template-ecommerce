package br.com.mercadolivre.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.mercadolivre.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
