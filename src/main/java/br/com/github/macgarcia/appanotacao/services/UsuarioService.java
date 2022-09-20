package br.com.github.macgarcia.appanotacao.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
		return usuarioRepository.findByLoginAndSenha(vo.getLogin(),
				this.encodeSenhaComMd5(vo.getSenha()));
	}
	
	private String encodeSenhaComMd5(final String senha) {
		String s="Texto de Exemplo";
	       MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	       m.update(s.getBytes(),0,s.length());
	       return new BigInteger(1,m.digest()).toString(16).toLowerCase();
	}
}