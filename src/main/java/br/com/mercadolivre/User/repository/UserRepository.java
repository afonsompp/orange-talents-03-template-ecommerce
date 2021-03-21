package br.com.mercadolivre.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.mercadolivre.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
