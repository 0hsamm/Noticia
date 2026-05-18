package co.edu.unbosque.paginanoticia;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaginanoticiaApplicationTests {

	public static void main(String[] args) {
		SpringApplication.run(PaginanoticiaApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
		
	}
	
}
