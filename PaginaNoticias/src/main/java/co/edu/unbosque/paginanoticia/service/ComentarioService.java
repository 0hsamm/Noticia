package co.edu.unbosque.paginanoticia.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.repository.ComentarioRepository;

public class ComentarioService implements CRUDOperation<ComentarioDTO>{
	
	@Autowired
	private ComentarioRepository comentarioRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public int create(ComentarioDTO data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ComentarioDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Long id, ComentarioDTO data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exist(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
