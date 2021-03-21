package br.com.mercadolivre.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.mercadolivre.Product.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
