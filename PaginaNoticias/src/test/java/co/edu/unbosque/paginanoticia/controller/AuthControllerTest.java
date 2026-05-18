package co.edu.unbosque.paginanoticia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unbosque.paginanoticia.dto.UsuarioDTO;
import co.edu.unbosque.paginanoticia.entity.Usuario;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.security.JwtUtil;
import co.edu.unbosque.paginanoticia.service.UsuarioNormalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsuarioNormalService usuarioNormalService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginReturnsRoleInResponse() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("samuel");
        usuario.setContrasena("12345678");
        usuario.setTipoUsuario(TipoUsuario.USUARIO);

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(jwtUtil.generateToken(any())).thenReturn("TOKEN_FAKE");

        UsuarioDTO loginRequest = new UsuarioDTO();
        loginRequest.setNombre("samuel");
        loginRequest.setContrasena("12345678");

        ResponseEntity<?> response = authController.login(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectMapper mapper = new ObjectMapper();
        String responseJson = mapper.writeValueAsString(response.getBody());
        System.out.println(responseJson);
        assertNotNull(responseJson);
        assertEquals(true, responseJson.contains("\"role\":\"USUARIO\""));
    }

    @Test
    void testRegisterSuccess() {

        when(usuarioNormalService.create(any())).thenReturn(0);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("samuel");
        dto.setContrasena("12345678");

        ResponseEntity<?> response = authController.register(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testRegisterUserAlreadyExists() {

        when(usuarioNormalService.create(any())).thenReturn(3);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("samuel");
        dto.setContrasena("12345678");

        ResponseEntity<?> response = authController.register(dto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}