package br.com.github.macgarcia.appanotacao.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.github.macgarcia.appanotacao.entitys.Anotacao;
import br.com.github.macgarcia.appanotacao.entitys.Usuario;
import br.com.github.macgarcia.appanotacao.enums.Situacao;
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
	private boolean executouFavorita;

	private final static Logger LOGGER = LoggerFactory.getLogger(AnotacaoController.class);
	
	@GetMapping(path = "/anotacoes")
	public ModelAndView telaDeAnotacoes(final HttpSession session, final IndexController indexController) {
		
		setIndexController(indexController);
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		
		if (!podeUsarSistema) {
			return new ModelAndView(CAMINHO_INICIO_APP);
		}
		
		if (!executouPesquisa && !executouFavorita) {
			alimentarListaAnotacoes(Situacao.NORMAL, "");
		}
		
		final ModelAndView model = new ModelAndView(CAMINHO_PAGINA_ANOTACAO);
		model.addObject("anotacoes", anotacoes);
		
		LOGGER.info("Iniciando a tela de anotações");
		return model;
	}
	
	
	@PostMapping(path = "/pesquisa")
	public String executarPesquisa(final HttpSession session, final String pesquisa) {
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		if (!podeUsarSistema) {
			return "redirect:/";
		}
		
		this.executouPesquisa = true;
		alimentarListaAnotacoes(Situacao.PESQUISA, pesquisa);
		return "redirect:/anotacoes";
	}
	
	@GetMapping(path = "/sair")
	public String sair(final HttpSession session) {
		session.removeAttribute("Usuario");
		usuarioLogado = null;
		anotacoes = null;
		executouPesquisa = false;
		executouFavorita = false;
		return "redirect:/";
	}
	
	@GetMapping(path = "todas")
	public String todasAnotacoes(final HttpSession session) {
		return this.executarPesquisa(session, "");
	}
	
	@GetMapping(path = "/favoritas")
	public String favoritas(final HttpSession session) {
		this.executouFavorita = true;
		alimentarListaAnotacoes(Situacao.FAVORITA, "");
		return "redirect:/anotacoes";
	}
	// ----------------------------------------------------------------------------
	
	@GetMapping(path = "/add/{id}")
	public ModelAndView add(@PathVariable("id") final Long id) {		
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
		this.executouPesquisa = false;
		return model;
	}
	
	@GetMapping(path = "/novo-editar")
	public String novoEditar(final HttpSession session, final Long id) {
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		if (!podeUsarSistema) {
			return "redirect:/";
		}
		return "redirect:/add/"+id;
	}
	
	@PostMapping(path = "/salvar")
	@Transactional
	public String atualizar(final HttpSession session, final Anotacao anotacao) {
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		
		if (!podeUsarSistema) {
			return "redirect:/";
		}
		anotacao.setUsuario(usuarioLogado);
		repository.saveAndFlush(anotacao);
		this.executouPesquisa = false;
		this.executouFavorita = false;
		return "redirect:/anotacoes";
	}
	
	@GetMapping(path = "/apagar")
	@Transactional
	public String apagar(final HttpSession session, final Long id) {
		
		final boolean podeUsarSistema = verificarUsoDoSistema(session);
		
		if (!podeUsarSistema) {
			return "redirect:/"; 
		}
		
		repository.deleteById(id);
		this.executouPesquisa = false;
		this.executouFavorita = false;
		return "redirect:/anotacoes";
	}
	
	//--------------------
	private void alimentarListaAnotacoes(final Situacao situacao, final String pesquisa) {
		
		switch(situacao) {
			case NORMAL:
				anotacoes = repository.findAnotacoesDoUsuarioPorId(usuarioLogado.getId());
				break;
			case PESQUISA:
				anotacoes = repository.findByTitulo(pesquisa);
				break;
			case FAVORITA:
				anotacoes = repository.findByFavorita();
				break;
			default:
				throw new IllegalArgumentException("Erro interno na pesquisa");
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
		this.usuarioLogado = (Usuario) session.getAttribute("Usuario");
		if (usuarioLogado == null) {
			return false;
		}
		return true;
	}
	
}
