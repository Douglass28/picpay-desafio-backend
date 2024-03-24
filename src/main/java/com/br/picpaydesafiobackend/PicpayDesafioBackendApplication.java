package com.br.picpaydesafiobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
public class PicpayDesafioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicpayDesafioBackendApplication.class, args);
	}

}
