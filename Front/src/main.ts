import { platformBrowser } from '@angular/platform-browser';
import { AppModule } from './app/app-module';

/**
 * Punto de entrada principal de la aplicación Angular.
 *
 * Este archivo se encarga de inicializar
 * y arrancar el módulo principal AppModule
 * dentro del navegador.
 */

/**
 * Inicializa la plataforma del navegador
 * y ejecuta el módulo principal de la aplicación.
 */
platformBrowser().bootstrapModule(AppModule, {

})

  /**
   * Captura y muestra errores que puedan ocurrir
   * durante el proceso de inicialización.
   */
  .catch(err => console.error(err));
