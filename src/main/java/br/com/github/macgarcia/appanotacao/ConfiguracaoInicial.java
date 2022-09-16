package br.com.github.macgarcia.appanotacao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.github.macgarcia.appanotacao.entitys.Usuario;

@Configuration
public class ConfiguracaoInicial {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Bean
	@Transactional
	public void criarUsuario() {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setLogin("macgarcia");
		novoUsuario.setSenha("marcos1985");
		manager.persist(novoUsuario);
	}

}
