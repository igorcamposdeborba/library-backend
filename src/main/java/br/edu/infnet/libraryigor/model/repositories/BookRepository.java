package br.edu.infnet.libraryigor.model.repositories;

import br.edu.infnet.libraryigor.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository com métodos pré-implementados que se comunicam com o banco de dados
@Repository // permite injeção de dependência para implementar os métodos de acesso ao banco de dados
public interface BookRepository extends JpaRepository<Book, Integer> {

    // Adicionados metodo personalizado que é implementado no UserService
    Optional<Book> findByTitle(String title);
}
