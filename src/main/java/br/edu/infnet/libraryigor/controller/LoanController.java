package br.edu.infnet.libraryigor.controller;

import br.edu.infnet.libraryigor.model.entities.dto.BookDTO;
import br.edu.infnet.libraryigor.model.entities.dto.LoanDTO;
import br.edu.infnet.libraryigor.model.services.BookService;
import br.edu.infnet.libraryigor.model.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/loan") // rota
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<LoanDTO>> findAll(){

        List<LoanDTO> loanList = loanService.findAll();

        return ResponseEntity.ok().body(loanList);
    }
}
