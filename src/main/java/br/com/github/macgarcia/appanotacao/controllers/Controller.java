package br.com.github.macgarcia.appanotacao.controllers;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.github.macgarcia.appanotacao.pojos.LoginVO;
import br.com.github.macgarcia.appanotacao.services.UsuarioService;

@org.springframework.stereotype.Controller
public class Controller {
	
	final static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	UsuarioService service;
	
	public Controller(UsuarioService service) {
		this.service = service;
	}

	@GetMapping(path = "/")
	public ModelAndView start() {
		return new ModelAndView("/login/index");
	}
	
	@PostMapping(path = "/logar")
	public ModelAndView logarNoSistema(final LoginVO vo) {
		try {
			final boolean isExists = service.verificaUsuario(vo);
			if (isExists) {
				return null;
			}
		} catch (NoResultException e ) {
			LOGGER.info("Usuario não encontrado");
		}
		return start();
	}

}
