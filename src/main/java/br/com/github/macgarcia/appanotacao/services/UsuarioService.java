package br.com.github.macgarcia.appanotacao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.github.macgarcia.appanotacao.entitys.Usuario;
import br.com.github.macgarcia.appanotacao.pojos.LoginVO;
import br.com.github.macgarcia.appanotacao.repositorys.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario verificaUsuario(final LoginVO vo) {
		if (vo.getLogin().isEmpty() || vo.getSenha().isEmpty()) {
			return new Usuario();
		}
		return usuarioRepository.findByLoginAndSenha(vo.getLogin(), vo.getSenha());
	}
}