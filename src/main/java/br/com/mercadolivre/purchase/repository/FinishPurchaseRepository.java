package br.com.mercadolivre.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.mercadolivre.purchase.model.FinishPurchase;

@Repository
public interface FinishPurchaseRepository extends JpaRepository<FinishPurchase, Long> {

}
