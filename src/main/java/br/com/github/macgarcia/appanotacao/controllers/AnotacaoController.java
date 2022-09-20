package br.com.github.macgarcia.appanotacao.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.github.macgarcia.appanotacao.entitys.Anotacao;
import br.com.github.macgarcia.appanotacao.entitys.Usuario;
import br.com.github.macgarcia.appanotacao.repositorys.AnotacaoRepository;

@Controller
public class AnotacaoController {
	
	private static final String CAMINHO_INICIO_APP = "login/index";
	
	private static final String CAMINHO_PAGINA_ANOTACAO = "anotacao/anotacao-index";
	private static final String CAMINHO_PAGINA_NOVO_EDITAR = "anotacao/novo-editar-anotacao.html";
	
	@Autowired
	private AnotacaoRepository repository;
	
	private IndexController indexController;
	
	private List<Anotacao> anotacoes;
	private Usuario usuarioLogado;
	private boolean executouPesquisa;

	private final static Logger LOGGER = LoggerFactory.getLogger(AnotacaoController.class);
	
	public ModelAndView telaDeAnotacoes(final HttpSession session, final IndexController indexController) {
		
		setIndexController(indexController);
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		
		if (!podeUsarSistema) {
			return new ModelAndView(CAMINHO_INICIO_APP);
		}
		
		if (!executouPesquisa) {
			alimentarListaAnotacoes("");
		}
		
		final ModelAndView model = new ModelAndView(CAMINHO_PAGINA_ANOTACAO);
		model.addObject("anotacoes", anotacoes);
		
		LOGGER.info("Iniciando a tela de anotações");
		return model;
	}
	
	@PostMapping(path = "/pesquisa")
	public ModelAndView executarPesquisa(final HttpSession session, final String pesquisa) {
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		if (!podeUsarSistema) {
			return new ModelAndView(CAMINHO_INICIO_APP);
		}
		
		this.executouPesquisa = true;
		alimentarListaAnotacoes(pesquisa);
		return telaDeAnotacoes(session, null);
	}
	
	@GetMapping(path = "/sair")
	public ModelAndView sair(final HttpSession session) {
		session.removeAttribute("Usuario");
		usuarioLogado = null;
		anotacoes = null;
		executouPesquisa = false;
		return indexController.start();
	}
	// ----------------------------------------------------------------------------
	
	@GetMapping(path = "/novo-editar")
	public ModelAndView novoEditar(final HttpSession session, final Long id) {
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		
		if (!podeUsarSistema) {
			return new ModelAndView(CAMINHO_INICIO_APP);
		}
		
		final ModelAndView model = new ModelAndView(CAMINHO_PAGINA_NOVO_EDITAR);
		String titulo = null;
		if (id != 0) {
			// Editar informações.
			titulo = "Editar anotação";
			final Anotacao anotacaoSelecionada = this.getAnotacao(id);
			model.addObject("anotacao", anotacaoSelecionada);
		} else {
			//Nova informações.
			titulo = "Nova anotação";
			model.addObject("anotacao", new Anotacao());
		}
		model.addObject("titulo", titulo);
		return model;
	}
	
	@PostMapping(path = "/salvar")
	@Transactional
	public ModelAndView atualizar(final HttpSession session, final Anotacao anotacao) {
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		
		if (!podeUsarSistema) {
			return new ModelAndView(CAMINHO_INICIO_APP);
		}
		anotacao.setUsuario(usuarioLogado);
		repository.saveAndFlush(anotacao);
		return telaDeAnotacoes(session, null);
	}
	
	@GetMapping(path = "/apagar")
	@Transactional
	public ModelAndView apagar(final HttpSession session, final Long id) {
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		
		if (!podeUsarSistema) {
			return new ModelAndView(CAMINHO_INICIO_APP);
		}
		
		repository.deleteById(id);
		return telaDeAnotacoes(session, null);
	}
	
	//--------------------
	private void alimentarListaAnotacoes(final String pesquisa) {
		if (pesquisa.isBlank() || pesquisa.isEmpty()) {
			anotacoes = repository.findAnotacoesDoUsuarioPorId(usuarioLogado.getId());
		} else {
			anotacoes = repository.findByTitulo(pesquisa);
		}
	}
	
	private void setIndexController(IndexController indexController) {
		if (indexController != null) {
			this.indexController = indexController;
		}
	}
	
	private Anotacao getAnotacao(final Long id) {
		int count = 0;
		boolean encontrou = false;
		Anotacao aEncontrada = null;
		final int tamanho = this.anotacoes.size();
		while(count < tamanho && !encontrou) {
			final Anotacao a = this.anotacoes.get(count);
			if (a.getId().equals(id)) {
				encontrou = true;
				aEncontrada = a;
			}
			count++;
		}
		return aEncontrada;
	}
	
	private boolean verificarUsoDoSistema(final HttpSession session) {
		final Usuario usuario = (Usuario) session.getAttribute("Usuario");
		if (usuario == null) {
			return false;
		}
		return true;
	}
	
}
