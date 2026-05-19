package co.edu.unbosque.paginanoticia;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Clase de prueba para validar la configuración del ServletInitializer.
 * Permite verificar que el SpringApplicationBuilder sea correctamente configurado
 * y no retorne valores nulos.
 */
public class ServletInitializerTest {

	/**
	 * Prueba que verifica que el método configure del ServletInitializer
	 * retorne un SpringApplicationBuilder válido y no nulo.
	 */
	@Test
	void testConfigure() {
		ServletInitializer initializer = new ServletInitializer();
		SpringApplicationBuilder builder = new SpringApplicationBuilder();

		SpringApplicationBuilder result = initializer.configure(builder);

		assertNotNull(result, "El SpringApplicationBuilder configurado no debe ser nulo");
	}
}