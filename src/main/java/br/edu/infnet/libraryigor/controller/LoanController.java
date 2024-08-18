package br.edu.infnet.libraryigor.controller;

import br.edu.infnet.libraryigor.model.entities.dto.LoanDTO;
import br.edu.infnet.libraryigor.model.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/loan") // rota
@Tag(name = "Loan", description = "Manage Loan") // descricao no swagger
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Operation(
            description = "Get all loans",
            summary = "The loans are going to retrieved from Library repository",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200")
            }
    )
    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<LoanDTO>> findAll(){

        List<LoanDTO> loanList = loanService.findAll();

        return ResponseEntity.ok().body(loanList);
    }
}
