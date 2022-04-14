package br.com.zup.edu.biblioteca.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.biblioteca.model.Livro;
import br.com.zup.edu.biblioteca.model.TipoCirculacao;
import br.com.zup.edu.biblioteca.repository.LivroRepository;

@RestController
@RequestMapping("/livros")
public class DeletarLivroController {
	
	private final LivroRepository repository;

	public DeletarLivroController(LivroRepository repository) {
		super();
		this.repository = repository;
	}
	
	@DeleteMapping("/{isbn}")
	ResponseEntity<?> remove(@PathVariable("isbn") String isbn){
		
		Livro livro = repository.findByISBN(isbn).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ISBN informado não foi encontrado no cadastro"));
		
		if(!livro.verificaCirculacao("LIVRE")) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"não é possível remover livros do tipo de circulacao"+livro.getCirculacao());
		}
		
		repository.delete(livro);
		
		return ResponseEntity.noContent().build();
		
	}
	
}
