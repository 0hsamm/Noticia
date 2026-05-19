'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">front documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                                <li class="link">
                                    <a href="overview.html" data-type="chapter-link">
                                        <span class="icon ion-ios-keypad"></span>Overview
                                    </a>
                                </li>

                            <li class="link">
                                <a href="index.html" data-type="chapter-link">
                                    <span class="icon ion-ios-paper"></span>
                                        README
                                </a>
                            </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                                <li class="link">
                                    <a href="properties.html" data-type="chapter-link">
                                        <span class="icon ion-ios-apps"></span>Properties
                                    </a>
                                </li>

                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-bs-toggle="collapse" ${ isNormalMode ?
                                'data-bs-target="#modules-links"' : 'data-bs-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link" >AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-AppModule-426de858a85e1f41a3c324f84b690d6a74eb644692293e79a6587b521df0ed4c573caf035b4a4730de8714f92b186ed6cecd07fbffb64cffc48529947587e51e"' : 'data-bs-target="#xs-components-links-module-AppModule-426de858a85e1f41a3c324f84b690d6a74eb644692293e79a6587b521df0ed4c573caf035b4a4730de8714f92b186ed6cecd07fbffb64cffc48529947587e51e"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-426de858a85e1f41a3c324f84b690d6a74eb644692293e79a6587b521df0ed4c573caf035b4a4730de8714f92b186ed6cecd07fbffb64cffc48529947587e51e"' :
                                            'id="xs-components-links-module-AppModule-426de858a85e1f41a3c324f84b690d6a74eb644692293e79a6587b521df0ed4c573caf035b4a4730de8714f92b186ed6cecd07fbffb64cffc48529947587e51e"' }>
                                            <li class="link">
                                                <a href="components/App.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >App</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LoginUsuarios.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >LoginUsuarios</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PanelAdmin.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PanelAdmin</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PanelComentarista.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PanelComentarista</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PanelEditor.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PanelEditor</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PanelUsuarioNormal.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PanelUsuarioNormal</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SingInUsuarios.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SingInUsuarios</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRoutingModule.html" data-type="entity-link" >AppRoutingModule</a>
                            </li>
                </ul>
                </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#injectables-links"' :
                                'data-bs-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/AuthService.html" data-type="entity-link" >AuthService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ComentarioService.html" data-type="entity-link" >ComentarioService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/HoroscopoService.html" data-type="entity-link" >HoroscopoService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/NoticiaService.html" data-type="entity-link" >NoticiaService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UsuarioAdministradorService.html" data-type="entity-link" >UsuarioAdministradorService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UsuarioComentaristaService.html" data-type="entity-link" >UsuarioComentaristaService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UsuarioEditorService.html" data-type="entity-link" >UsuarioEditorService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UsuarioNormalService.html" data-type="entity-link" >UsuarioNormalService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#interfaces-links"' :
                            'data-bs-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/AuthResponse.html" data-type="entity-link" >AuthResponse</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Comentario.html" data-type="entity-link" >Comentario</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Horoscopo.html" data-type="entity-link" >Horoscopo</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Noticia.html" data-type="entity-link" >Noticia</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Usuario.html" data-type="entity-link" >Usuario</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#miscellaneous-links"'
                            : 'data-bs-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/enumerations.html" data-type="entity-link">Enums</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <a data-type="chapter-link" href="routes.html"><span class="icon ion-ios-git-branch"></span>Routes</a>
                        </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank" rel="noopener noreferrer">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});