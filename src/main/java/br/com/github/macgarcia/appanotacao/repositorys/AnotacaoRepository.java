package br.com.github.macgarcia.appanotacao.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.github.macgarcia.appanotacao.entitys.Anotacao;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {
	
	@Query("select a from Anotacao a where a.usuario.id = :id")
	List<Anotacao> findAnotacoesDoUsuarioPorId(@Param("id") final Long id);
	
	@Query("select a from Anotacao a where a.titulo like %:pesquisa%")
	List<Anotacao> findByTitulo(@Param("pesquisa") final String pesquisa);

}
