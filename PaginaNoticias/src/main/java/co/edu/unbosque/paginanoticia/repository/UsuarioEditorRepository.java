package co.edu.unbosque.paginanoticia.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;

public interface UsuarioEditorRepository extends CrudRepository<UsuarioEditor, Long>{
	
	public Optional<UsuarioEditor> findByUsername(String username);

	public void deleteByUsername(String username); 
	
}
