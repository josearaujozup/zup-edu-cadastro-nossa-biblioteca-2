package br.com.zup.edu.biblioteca.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.biblioteca.model.Autor;
import br.com.zup.edu.biblioteca.repository.AutorRepository;

@RestController
@RequestMapping("/autores")
public class AtualizarAutorController {
	
	private final AutorRepository repository;

	public AtualizarAutorController(AutorRepository repository) {
		this.repository = repository;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long idAutor, @RequestBody @Valid AutorRequest request){
		
		Autor autor = repository.findById(idAutor).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Autor não cadastrado"));
		
//		autor.setNome(request.getNome());
//		autor.setCpf(request.getCpf());
//		autor.setDescricao(request.getDescricao());
//		autor.setEmail(request.getEmail());
		
		autor.atualiza(request.getNome(), request.getEmail(), request.getDescricao(),request.getCpf());
		
		repository.save(autor);
		
		return ResponseEntity.noContent().build();
	}
	
}
