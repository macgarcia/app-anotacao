package br.com.github.macgarcia.appanotacao.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.github.macgarcia.appanotacao.entitys.Usuario;
import br.com.github.macgarcia.appanotacao.pojos.LoginVO;
import br.com.github.macgarcia.appanotacao.services.UsuarioService;

@Controller
public class IndexController {

	private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	UsuarioService service;
	AnotacaoController anotacaoController;

	public IndexController(UsuarioService service, AnotacaoController anotacaoController) {
		this.service = service;
		this.anotacaoController = anotacaoController;
	}

	@GetMapping(path = "/")
	public ModelAndView start() {
		return new ModelAndView("/login/index");
	}

	@PostMapping(path = "/")
	public ModelAndView logarNoSistema(final HttpSession session, LoginVO vo) {
		final Usuario usuario = service.verificaUsuario(vo);
		if (usuario != null && usuario.getId() != null) {
			session.setAttribute("Usuario", usuario);
			LOGGER.info("Usuario id: " + usuario.getId() + ", usuário nome: " + usuario.getLogin());
			return anotacaoController.telaDeAnotacoes(session);
		}
		return start();
	}

}
