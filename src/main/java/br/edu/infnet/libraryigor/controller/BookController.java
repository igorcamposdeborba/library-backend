package br.edu.infnet.libraryigor.controller;

import br.edu.infnet.libraryigor.model.entities.dto.BookDTO;
import br.edu.infnet.libraryigor.model.entities.dto.UsersDTO;
import br.edu.infnet.libraryigor.model.services.BookService;
import br.edu.infnet.libraryigor.model.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/book") // rota
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<BookDTO>> findAll(){

        List<BookDTO> bookList = bookService.findAll();

        return ResponseEntity.ok().body(bookList);
    }

    @PostMapping("/single")
    public ResponseEntity<BookDTO> insert(@Valid @RequestBody BookDTO bookDTO){
        // Inserir pelo service no banco de dados
        BookDTO newBook = bookService.insert(bookDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBook.getId()).toUri();

        return ResponseEntity.created(uri).build(); // retornar status created 201 com uri do objeto criado
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> insertAll(@Valid @RequestBody List<BookDTO> booksDTO){
        // Inserir pelo service no banco de dados
        List<BookDTO> newBooks = bookService.insertAll(booksDTO);

        List<URI> uris = newBooks.stream()
                                    .map(book -> ServletUriComponentsBuilder.fromCurrentRequest()
                                            .path("/{id}").buildAndExpand(book.getId()).toUri())
                                    .collect(Collectors.toList());

        return ResponseEntity.created(uris.get(0)).build(); // retornar status created 201 com uri do objeto criado
    }
}
