import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

/**
 * Definición de las rutas principales de la aplicación.
 *
 * Actualmente no existen rutas configuradas,
 * por lo que el arreglo se encuentra vacío.
 */
const routes: Routes = [];

/**
 * Módulo encargado de la configuración
 * del sistema de enrutamiento de Angular.
 *
 * Este módulo:
 * - Inicializa el RouterModule.
 * - Registra las rutas principales.
 * - Exporta el sistema de rutas
 *   para ser utilizado en otros módulos.
 */
@NgModule({

  /**
   * Importación y configuración inicial
   * del sistema de rutas de Angular.
   */
  imports: [RouterModule.forRoot(routes)],

  /**
   * Exporta RouterModule para permitir
   * el uso de directivas de enrutamiento
   * en otros módulos de la aplicación.
   */
  exports: [RouterModule]
})
export class AppRoutingModule { }
