package co.edu.unbosque.paginanoticia.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;

public interface UsuarioComentaristaRepository extends CrudRepository<UsuarioComentarista, Long>{
	
	public Optional<UsuarioComentarista> findByUsername(String username);

	public void deleteByUsername(String username); 
}
