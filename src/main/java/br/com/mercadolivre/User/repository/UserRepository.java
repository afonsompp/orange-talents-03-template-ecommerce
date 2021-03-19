package br.com.mercadolivre.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.mercadolivre.User.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
