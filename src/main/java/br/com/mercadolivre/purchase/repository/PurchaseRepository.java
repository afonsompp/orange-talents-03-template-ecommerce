package br.com.mercadolivre.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.mercadolivre.purchase.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
