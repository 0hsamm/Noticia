package co.edu.unbosque.paginanoticia;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de arranque de la aplicación.
 * Inicializa el contexto de Spring Boot y configura los beans necesarios
 * para el funcionamiento general del sistema.
 */
@SpringBootApplication
public class PaginanoticiaApplicationTests {

	public static void main(String[] args) {
		SpringApplication.run(PaginanoticiaApplication.class, args);
	}

	/**
	 * Proporciona una instancia de ModelMapper para la conversión
	 * entre entidades y DTOs dentro de la aplicación.
	 */
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}