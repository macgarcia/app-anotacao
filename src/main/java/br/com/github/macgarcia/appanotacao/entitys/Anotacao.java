package br.com.github.macgarcia.appanotacao.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ANOTACAO")
@SequenceGenerator(name = "ANOTACAO_SEQ", sequenceName = "ANOTACAO_SEQ", allocationSize = 1, initialValue = 1)
public class Anotacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANOTACAO_SEQ")
	private Long id;
	
	@Column(name = "TITULO")
	private String titulo;
	
	@Column(name = "CONTEUDO")
	private String conteudo;
	
	@Column(name = "FAVORITA")
	private int favorita = 0;
	
	@ManyToOne
	@JoinColumn(name = "USUARIO_ID")
	private Usuario usuario;
	
	@OneToOne
	@JoinColumn(name = "CATEGORIA_ID")
	private Categoria categoria;	
	
	public Anotacao () {}

	public Anotacao(String titulo, String conteudo, Usuario usuario) {
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getFavorita() {
		return favorita;
	}

	public void setFavorita(int favorita) {
		this.favorita = favorita;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
