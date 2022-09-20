package br.com.github.macgarcia.appanotacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class AppAnotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppAnotacaoApplication.class, args);
	}

}
