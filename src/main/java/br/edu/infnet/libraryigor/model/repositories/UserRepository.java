package br.edu.infnet.libraryigor.model.repositories;

import br.edu.infnet.libraryigor.model.entities.client.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // permite injeção de dependência para implementar os métodos de acesso ao banco de dados
public interface UserRepository extends JpaRepository<Users, Integer> {
}