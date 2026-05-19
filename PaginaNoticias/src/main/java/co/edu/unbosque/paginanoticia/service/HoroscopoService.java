package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.HoroscopoDTO;
import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import co.edu.unbosque.paginanoticia.exception.EmptyWordException;
import co.edu.unbosque.paginanoticia.exception.LanzadorDeExcepcion;
import co.edu.unbosque.paginanoticia.repository.HoroscopoRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Servicio encargado de la gestión de horóscopos.
 * Permite crear, actualizar, eliminar y consultar horóscopos,
 * validando la autenticación del usuario editor y las reglas de negocio.
 */
@Service
public class HoroscopoService implements CRUDOperation<HoroscopoDTO> {

	@Autowired
	private HoroscopoRepository horoscopoRepo;

	@Autowired
	private UsuarioEditorRepository usuarioEditorRepo;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Crea un nuevo horóscopo asociado al usuario editor autenticado.
	 * Valida contenido vacío, tipo de publicación y existencia del usuario.
	 */
	@Override
	public int create(HoroscopoDTO data) {

		try {
			LanzadorDeExcepcion.verificarPalabraVacia(data.getContenido());
		} catch (EmptyWordException e) {
			return 1;
		}

		if (data.getTipoPublicacion() == null) {
			return 2;
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Optional<UsuarioEditor> usuarioEditorOpt = usuarioEditorRepo.findByNombre(username);

		if (usuarioEditorOpt.isEmpty()) {
			return 3;
		}

		Horoscopo horoscopo = mapper.map(data, Horoscopo.class);
		horoscopo.setUsuarioEditor(usuarioEditorOpt.get());
		horoscopoRepo.save(horoscopo);

		return 0;
	}

	/**
	 * Obtiene todos los horóscopos registrados en el sistema
	 * y los convierte a DTO.
	 */
	@Override
	public List<HoroscopoDTO> getAll() {
		List<HoroscopoDTO> dtoList = new ArrayList<>();
		horoscopoRepo.findAll().forEach(horoscopo -> dtoList.add(toDto(horoscopo)));
		return dtoList;
	}

	/**
	 * Elimina un horóscopo por su ID validando la existencia
	 * y la propiedad del usuario editor autenticado.
	 */
	@Override
	public int deleteById(Long id) {

		Optional<Horoscopo> horoscopoOpt = horoscopoRepo.findById(id);
		if (horoscopoOpt.isEmpty()) {
			return 1;
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Optional<UsuarioEditor> usuarioEditorOpt = usuarioEditorRepo.findByNombre(username);

		if (usuarioEditorOpt.isEmpty()) {
			return 2;
		}

		Horoscopo horoscopo = horoscopoOpt.get();
		if (horoscopo.getUsuarioEditor().getId() != usuarioEditorOpt.get().getId()) {
			return 3;
		}

		horoscopoRepo.delete(horoscopo);
		return 0;
	}

	/**
	 * Actualiza un horóscopo existente validando contenido,
	 * tipo de publicación y permisos del usuario editor.
	 */
	@Override
	public int updateById(Long id, HoroscopoDTO data) {

		Optional<Horoscopo> horoscopoOpt = horoscopoRepo.findById(id);
		if (horoscopoOpt.isEmpty()) {
			return 4;
		}

		try {
			LanzadorDeExcepcion.verificarPalabraVacia(data.getContenido());
		} catch (EmptyWordException e) {
			return 1;
		}

		if (data.getTipoPublicacion() == null) {
			return 2;
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Optional<UsuarioEditor> usuarioEditorOpt = usuarioEditorRepo.findByNombre(username);

		if (usuarioEditorOpt.isEmpty()) {
			return 3;
		}

		Horoscopo horoscopo = horoscopoOpt.get();
		if (horoscopo.getUsuarioEditor().getId() != usuarioEditorOpt.get().getId()) {
			return 5;
		}

		horoscopo.setTipoHoroscopo(data.getTipoHoroscopo());
		horoscopo.setContenido(data.getContenido());
		horoscopo.setTipoPublicacion(data.getTipoPublicacion());
		horoscopo.setUsuarioEditor(usuarioEditorOpt.get());
		horoscopoRepo.save(horoscopo);

		return 0;
	}

	@Override
	public long count() {
		return horoscopoRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return horoscopoRepo.existsById(id);
	}

	/**
	 * Obtiene todos los horóscopos asociados a un usuario editor específico.
	 */
	public List<HoroscopoDTO> getByUsuarioEditor(Long usuarioEditorId) {
		if (!usuarioEditorRepo.existsById(usuarioEditorId)) {
			return null;
		}

		List<HoroscopoDTO> dtoList = new ArrayList<>();
		horoscopoRepo.findByUsuarioEditor_Id(usuarioEditorId)
				.forEach(horoscopo -> dtoList.add(toDto(horoscopo)));
		return dtoList;
	}

	/**
	 * Convierte una entidad Horoscopo a su DTO correspondiente.
	 */
	private HoroscopoDTO toDto(Horoscopo horoscopo) {
		HoroscopoDTO dto = mapper.map(horoscopo, HoroscopoDTO.class);
		if (horoscopo.getUsuarioEditor() != null) {
			dto.setUsuarioEditor(horoscopo.getUsuarioEditor().getNombre());
		}
		return dto;
	}
}