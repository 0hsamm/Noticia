package co.edu.unbosque.paginanoticia;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase utilizada para la configuración de despliegue en servidores servlet.
 * Permite inicializar la aplicación Spring Boot cuando se ejecuta en un contenedor externo.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación Spring Boot para su despliegue en un servidor servlet.
	 *
	 * @param application constructor de la aplicación Spring Boot
	 * @return configuración de la aplicación con la clase principal registrada
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PaginanoticiaApplication.class);
	}

}