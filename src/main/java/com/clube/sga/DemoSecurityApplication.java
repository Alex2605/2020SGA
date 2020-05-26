package com.clube.sga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.clube.sga.service.EmailService;

@SpringBootApplication
public class DemoSecurityApplication implements CommandLineRunner{

	public static void main(String[] args) {
		//System.out.println(new BCryptPasswordEncoder().encode("123456"));
		SpringApplication.run(DemoSecurityApplication.class, args);
	}
	
	@Autowired
	EmailService service;
	
	@Override
	public void run(String... args) throws Exception {
		//service.enviarPedidoConfirmacaoDeCadastro("alexanderprof@yahoo.com.br", "1759ALx92");
		

	}

}
