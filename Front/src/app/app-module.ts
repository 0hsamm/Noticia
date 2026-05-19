import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { App } from './app';
import { AppRoutingModule } from './app-routing-module';
import { LoginUsuarios } from './login-usuarios/login-usuarios';
import { PanelAdmin } from './panel-admin/panel-admin';
import { PanelComentarista } from './panel-comentarista/panel-comentarista';
import { PanelEditor } from './panel-editor/panel-editor';
import { PanelUsuarioNormal } from './panel-usuario-normal/panel-usuario-normal';
import { SingInUsuarios } from './sing-in-usuarios/sing-in-usuarios';

/**
 * Módulo principal de la aplicación.
 *
 * Este módulo centraliza la configuración general,
 * registra los componentes principales e importa
 * los módulos necesarios para el funcionamiento
 * de la aplicación Angular.
 */
@NgModule({

  /**
   * Componentes declarados dentro del módulo.
   *
   * Incluye componentes de autenticación
   * y paneles según el rol del usuario.
   */
  declarations: [
    App,
    LoginUsuarios,
    SingInUsuarios,
    PanelAdmin,
    PanelEditor,
    PanelComentarista,
    PanelUsuarioNormal,
  ],

  /**
   * Módulos importados requeridos
   * para el funcionamiento de la aplicación.
   */
  imports: [

    /**
     * Permite ejecutar la aplicación Angular
     * dentro del navegador.
     */
    BrowserModule,

    /**
     * Habilita soporte para animaciones.
     */
    BrowserAnimationsModule,

    /**
     * Permite el uso de formularios
     * basados en template-driven forms.
     */
    FormsModule,

    /**
     * Habilita las solicitudes HTTP
     * hacia servicios backend.
     */
    HttpClientModule,

    /**
     * Módulo encargado de gestionar
     * el sistema de rutas de la aplicación.
     */
    AppRoutingModule,

    /**
     * Configuración global de las notificaciones toast.
     */
    ToastrModule.forRoot({

      /**
       * Tiempo de duración de la notificación.
       */
      timeOut: 3000,

      /**
       * Posición donde aparecerán las notificaciones.
       */
      positionClass: 'toast-top-right',

      /**
       * Evita mostrar notificaciones repetidas.
       */
      preventDuplicates: true,

      /**
       * Muestra una barra de progreso visual.
       */
      progressBar: true,

      /**
       * Agrega botón para cerrar manualmente la notificación.
       */
      closeButton: true,
    }),
  ],

  /**
   * Componente raíz utilizado
   * para inicializar la aplicación.
   */
  bootstrap: [App],
})
export class AppModule {}
