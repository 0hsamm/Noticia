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

@NgModule({
  declarations: [
    App,
    LoginUsuarios,
    SingInUsuarios,
    PanelAdmin,
    PanelEditor,
    PanelComentarista,
    PanelUsuarioNormal,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
      progressBar: true,
      closeButton: true,
    }),
  ],
  bootstrap: [App],
})
export class AppModule {}
