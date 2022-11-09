package br.com.github.macgarcia.appanotacao.controllers;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.github.macgarcia.appanotacao.entitys.Categoria;
import br.com.github.macgarcia.appanotacao.repositorys.CategoriaRepository;

@Controller
public class CateoriaController {
	
	private final CategoriaRepository repository;
	
	public CateoriaController(final CategoriaRepository repository) {
		this.repository = repository;
	}

	@GetMapping(path = "/salvarCategoria/{novaCategoria}")
	@Transactional
	public String salvarCategoria(@PathVariable("novaCategoria") final String nomeCategoria) {
		final Categoria novaCategoria = new Categoria(nomeCategoria);
		repository.saveAndFlush(novaCategoria);
		return "redirect:/anotacoes";
	}

}
