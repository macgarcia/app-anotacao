package br.com.github.macgarcia.appanotacao.services;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import br.com.github.macgarcia.appanotacao.entitys.Usuario;
import br.com.github.macgarcia.appanotacao.pojos.LoginVO;

@Service
public class UsuarioService {
	
	private static final String BUSCAR_USUARIO = "from Usuario u where u.login = :login and u.senha = :senha";
	
	@PersistenceContext
	private EntityManager manager;

	public boolean verificaUsuario(final LoginVO vo) throws NoResultException {
		if (vo.getLogin().isEmpty() || vo.getSenha().isEmpty()) {
			return false;
		}
		TypedQuery<Usuario> query = manager.createQuery(BUSCAR_USUARIO, Usuario.class);
		query.setParameter("login", vo.getLogin()).setParameter("senha", vo.getSenha());
		query.getSingleResult();
		return true;
	}
}