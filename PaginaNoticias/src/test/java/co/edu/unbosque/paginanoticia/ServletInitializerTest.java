package co.edu.unbosque.paginanoticia;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;



public class ServletInitializerTest {

	
	
	@Test
	   void testConfigure() {
	    ServletInitializer initializer = new ServletInitializer();
	    SpringApplicationBuilder builder = new SpringApplicationBuilder();

	    SpringApplicationBuilder result = initializer.configure(builder);

	    assertNotNull(result, "El SpringApplicationBuilder configurado no debe ser nulo");
	  }
	
}
