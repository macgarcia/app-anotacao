package br.com.github.macgarcia.appanotacao.pojos;

import java.io.Serializable;

public class LoginVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	
	public LoginVO(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

}
