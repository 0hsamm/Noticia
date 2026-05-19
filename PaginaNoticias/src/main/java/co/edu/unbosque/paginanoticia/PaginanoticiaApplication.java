package co.edu.unbosque.paginanoticia;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación Spring Boot.
 * Encargada de iniciar el contexto de la aplicación y levantar el servidor.
 * También define beans de configuración global utilizados en el proyecto.
 */
@SpringBootApplication
public class PaginanoticiaApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 *
	 * @param args argumentos de línea de comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(PaginanoticiaApplication.class, args);
	}

	/**
	 * Crea e inyecta una instancia de ModelMapper como bean.
	 * Se utiliza para el mapeo de objetos entre DTOs y entidades.
	 *
	 * @return instancia de ModelMapper configurada
	 */
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}