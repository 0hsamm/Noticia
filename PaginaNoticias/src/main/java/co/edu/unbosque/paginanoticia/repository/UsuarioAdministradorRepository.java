package co.edu.unbosque.paginanoticia.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;

public interface UsuarioAdministradorRepository extends CrudRepository<UsuarioAdministrador, Long>{
	
	public Optional<UsuarioAdministrador> findByUsername(String username);

	public void deleteByUsername(String username); 
}
