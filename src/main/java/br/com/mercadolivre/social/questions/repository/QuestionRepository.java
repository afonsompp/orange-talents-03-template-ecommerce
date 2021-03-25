package br.com.mercadolivre.social.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.mercadolivre.social.questions.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
