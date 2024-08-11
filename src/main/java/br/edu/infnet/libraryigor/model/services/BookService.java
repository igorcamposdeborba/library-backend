package br.edu.infnet.libraryigor.model.services;

import br.edu.infnet.libraryigor.model.entities.Book;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import br.edu.infnet.libraryigor.model.entities.dto.BookDTO;
import br.edu.infnet.libraryigor.model.entities.dto.UsersDTO;
import br.edu.infnet.libraryigor.model.repositories.BookRepository;
import br.edu.infnet.libraryigor.model.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository; // injetar instancia do repository para buscar do banco de dados via JPA

    public List<BookDTO> findAll(){
        List<Book> bookList = bookRepository.findAll(Sort.by("title")); // buscar no banco de dados e ordenar por nome
        // converter a lista de classe para DTO
        return bookList.stream().filter(Objects::nonNull).map((Book book) -> new BookDTO(book)).collect(Collectors.toList());
    }

    @Transactional
    public BookDTO insert(BookDTO bookDTO) {
        // Mapear DTO para classe
        Book entity = new Book(bookDTO);

        entity = bookRepository.save(entity); // salvar no banco de dados
        return new BookDTO(entity); // retornar o que foi salvo no banco de dados
    }

    @Transactional
    public List<BookDTO> insertAll(List<BookDTO> bookDTO) {
        // Mapear DTO para classe
        List<Book> entities = bookDTO.stream().map(book -> new Book(book)).collect(Collectors.toList());

        entities = bookRepository.saveAll(entities); // salvar no banco de dados

        // Mapear as entities salvas de volta para DTO
        List<BookDTO> savedDTOs = entities.stream()
                                            .map(entity -> new BookDTO(entity))
                                            .collect(Collectors.toList());
        return savedDTOs; // retornar o que foi salvo no banco de dados
    }
}
