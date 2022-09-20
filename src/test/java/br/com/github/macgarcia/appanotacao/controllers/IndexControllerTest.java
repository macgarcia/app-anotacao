package br.com.github.macgarcia.appanotacao.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import br.com.github.macgarcia.appanotacao.pojos.LoginVO;

@SpringBootTest
public class IndexControllerTest {

	@Autowired
	IndexController controller;
	
	@Test
	@Order(value = 1)
	void buscarUsuarioComSucesso() {
		final LoginVO vo = new LoginVO("macgarcia", "marcos1985");
		ModelAndView logarNoSistema = controller.logarNoSistema(null, vo);
		assertNull(logarNoSistema);
	}
	
	@Test
	@Order(value = 2)
	void buscarUsuarioSemSucesso() {
		final LoginVO vo = new LoginVO("abc", "123");
		ModelAndView logarNoSistema = controller.logarNoSistema(null, vo);
		assertNotNull(logarNoSistema.hasView());
		assertEquals(logarNoSistema.getViewName(), "/login/index");
	}
}
