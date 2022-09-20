package br.com.github.macgarcia.appanotacao.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.github.macgarcia.appanotacao.entitys.Usuario;
import br.com.github.macgarcia.appanotacao.pojos.LoginVO;
import br.com.github.macgarcia.appanotacao.services.UsuarioService;

@Controller
@RequestMapping(path = "/")
public class IndexController {
	
	private static final String CAMINHO_PAGINA = "/login/index";

	private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	private UsuarioService service;
	private AnotacaoController anotacaoController;

	public IndexController(UsuarioService service, AnotacaoController anotacaoController) {
		this.service = service;
		this.anotacaoController = anotacaoController;
	}

	@GetMapping
	public String start() {
		return CAMINHO_PAGINA;
	}

	@PostMapping(value = "/")
	public ModelAndView logarNoSistema(final HttpSession session, LoginVO vo) {
		final Usuario usuario = service.verificaUsuario(vo);
		if (usuario != null && usuario.getId() != null) {
			session.setAttribute("Usuario", usuario);
			LOGGER.info("Usuario id: " + usuario.getId() + ", usuário nome: " + usuario.getLogin());
			return anotacaoController.telaDeAnotacoes(session, this);
		}
		return new ModelAndView(CAMINHO_PAGINA);
	}

}
