package br.com.mercadolivre.social.reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.mercadolivre.social.reviews.dto.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
