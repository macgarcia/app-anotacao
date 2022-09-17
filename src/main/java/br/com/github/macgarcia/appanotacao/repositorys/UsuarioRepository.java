package br.com.github.macgarcia.appanotacao.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.github.macgarcia.appanotacao.entitys.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByLoginAndSenha(String login, String senha);

}
