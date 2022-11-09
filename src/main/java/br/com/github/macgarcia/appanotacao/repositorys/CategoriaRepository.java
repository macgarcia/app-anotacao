package br.com.github.macgarcia.appanotacao.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.github.macgarcia.appanotacao.entitys.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
