package br.com.github.macgarcia.appanotacao.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.github.macgarcia.appanotacao.entitys.Anotacao;
import br.com.github.macgarcia.appanotacao.entitys.Usuario;
import br.com.github.macgarcia.appanotacao.repositorys.AnotacaoRepository;

@Controller
public class AnotacaoController {
	
	private static final String CAMINHO_PAGINA = "/anotacao/anotacao-index";
	
	@Autowired
	private AnotacaoRepository repository;
	
	private List<Anotacao> anotacoes;
	private Usuario usuarioLogado;
	private boolean executouPesquisa;

	private final static Logger LOGGER = LoggerFactory.getLogger(AnotacaoController.class);
	
	public ModelAndView telaDeAnotacoes(final HttpSession session) {
		usuarioLogado = (Usuario) session.getAttribute("Usuario");
		if (!executouPesquisa) {
			alimentarListaAnotacoes("");
		}
		
		final ModelAndView model = new ModelAndView(CAMINHO_PAGINA);
		model.addObject("anotacoes", anotacoes);
		
		LOGGER.info("Iniciando a tela de anotações");
		return model;
	}
	
	@PostMapping(path = "/pesquisa")
	public ModelAndView executarPesquisa(final HttpSession session, final String pesquisa) {
		this.executouPesquisa = true;
		alimentarListaAnotacoes(pesquisa);
		return telaDeAnotacoes(session);
	}
	
	//--------------------
	private void alimentarListaAnotacoes(final String pesquisa) {
		if (pesquisa.isBlank() || pesquisa.isEmpty()) {
			anotacoes = repository.findAnotacoesDoUsuarioPorId(usuarioLogado.getId());
		} else {
			anotacoes = repository.findByTitulo(pesquisa);
		}
	}
	
}
