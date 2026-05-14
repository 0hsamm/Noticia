package co.edu.unbosque.paginanoticia.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.paginanoticia.entity.UsuarioNormal;

public interface UsuarioNormalRepository extends CrudRepository<UsuarioNormal, Long>{
	
	public Optional<UsuarioNormal> findByUsername(String username);

	public void deleteByUsername(String username); 
	
}
