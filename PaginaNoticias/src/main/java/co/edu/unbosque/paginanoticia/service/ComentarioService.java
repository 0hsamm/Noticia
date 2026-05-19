package co.edu.unbosque.paginanoticia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.entity.Comentario;
import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import co.edu.unbosque.paginanoticia.entity.Noticia;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.exception.EmptyWordException;
import co.edu.unbosque.paginanoticia.exception.LanzadorDeExcepcion;
import co.edu.unbosque.paginanoticia.repository.ComentarioRepository;
import co.edu.unbosque.paginanoticia.repository.HoroscopoRepository;
import co.edu.unbosque.paginanoticia.repository.NoticiaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;

/**
 * Servicio encargado de la gestión de comentarios.
 * Permite crear, actualizar, eliminar y consultar comentarios asociados a noticias u horóscopos,
 * validando reglas de negocio como la autenticación del usuario y la consistencia de datos.
 */
@Service
public class ComentarioService implements CRUDOperation<ComentarioDTO> {

    @Autowired
    private ComentarioRepository comentarioRepo;

    @Autowired
    private UsuarioComentaristaRepository comentaristaRepo;

    @Autowired
    private NoticiaRepository noticiaRepo;

    @Autowired
    private HoroscopoRepository horoscopoRepo;

    @Autowired
    private ModelMapper mapper;

    /**
     * Crea un nuevo comentario asociado a una noticia o un horóscopo.
     * Valida contenido vacío y la existencia del usuario comentarista autenticado.
     */
    @Override
    public int create(ComentarioDTO data) {

        try {

            LanzadorDeExcepcion.verificarPalabraVacia(data.getContenido());

        } catch (EmptyWordException e) {
            return 1;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<UsuarioComentarista> comentaristaOpt = comentaristaRepo.findByNombre(username);

        if (comentaristaOpt.isEmpty()) {
            return 2;
        }

        Comentario comentario = new Comentario();
        comentario.setContenido(data.getContenido());
        comentario.setFecha(LocalDateTime.now());
        comentario.setTipoPublicacion(data.getTipoPublicacion());
        comentario.setComentarista(comentaristaOpt.get());

        if (data.getTipoPublicacion() == TipoPublicacion.NOTICIA) {

            Optional<Noticia> noticiaOpt = noticiaRepo.findByTitulo(data.getTituloNoticia());

            if (noticiaOpt.isEmpty()) {
                return 3;
            }

            comentario.setNoticia(noticiaOpt.get());
            comentario.setHoroscopo(null);
        }

        if (data.getTipoPublicacion() == TipoPublicacion.HOROSCOPO) {
            Optional<Horoscopo> horoscopoOpt = findHoroscopoForComment(data);
            if (horoscopoOpt.isEmpty()) {
                return 4;
            }

            comentario.setHoroscopo(horoscopoOpt.get());
            comentario.setNoticia(null);
        }

        comentarioRepo.save(comentario);

        return 0;
    }

    /**
     * Obtiene la lista de todos los comentarios registrados en el sistema
     * y los convierte a DTO para su transporte.
     */
    @Override
    public List<ComentarioDTO> getAll() {
        List<ComentarioDTO> dtoList = new ArrayList<>();
        comentarioRepo.findAll().forEach(comentario -> dtoList.add(toDto(comentario)));

        return dtoList;
    }

    /**
     * Elimina un comentario por su ID, validando que el usuario autenticado
     * sea el propietario del comentario.
     */
    @Override
    public int deleteById(Long id) {

        Optional<Comentario> comentarioOpt = comentarioRepo.findById(id);
        if (comentarioOpt.isEmpty()) {
            return 1;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<UsuarioComentarista> usuarioOpt = comentaristaRepo.findByNombre(username);

        if (usuarioOpt.isEmpty()) {
            return 2;
        }

        Comentario comentario = comentarioOpt.get();
        if (comentario.getComentarista().getId() != usuarioOpt.get().getId()) {
            return 3;
        }

        comentarioRepo.delete(comentario);
        return 0;
    }

    /**
     * Actualiza un comentario existente validando permisos del usuario
     * y consistencia de la información.
     */
    @Override
    public int updateById(Long id, ComentarioDTO data) {

        Optional<Comentario> comentarioOpt = comentarioRepo.findById(id);

        if (comentarioOpt.isEmpty()) {
            return 5;
        }

        try {

            LanzadorDeExcepcion.verificarPalabraVacia(data.getContenido());

        } catch (EmptyWordException e) {

            return 1;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<UsuarioComentarista> comentaristaOpt = comentaristaRepo.findByNombre(username);

        if (comentaristaOpt.isEmpty()) {
            return 2;
        }

        Comentario comentario = comentarioOpt.get();
        if (comentario.getComentarista().getId() != comentaristaOpt.get().getId()) {
            return 6;
        }

        comentario.setContenido(data.getContenido());
        comentario.setTipoPublicacion(data.getTipoPublicacion());
        comentario.setComentarista(comentaristaOpt.get());

        if (data.getTipoPublicacion() == TipoPublicacion.NOTICIA) {

            Optional<Noticia> noticiaOpt = noticiaRepo.findByTitulo(data.getTituloNoticia());

            if (noticiaOpt.isEmpty()) {
                return 3;
            }

            comentario.setNoticia(noticiaOpt.get());
            comentario.setHoroscopo(null);
        }

        if (data.getTipoPublicacion() == TipoPublicacion.HOROSCOPO) {

            Optional<Horoscopo> horoscopoOpt = findHoroscopoForComment(data);

            if (horoscopoOpt.isEmpty()) {
                return 4;
            }

            comentario.setHoroscopo(horoscopoOpt.get());
            comentario.setNoticia(null);
        }

        comentarioRepo.save(comentario);

        return 0;
    }

    @Override
    public long count() {
        return comentarioRepo.count();
    }

    @Override
    public boolean exist(Long id) {
        return comentarioRepo.existsById(id);
    }

    /**
     * Obtiene los comentarios asociados a una noticia específica.
     */
    public List<ComentarioDTO> getByNoticia(Long noticiaId) {

        List<ComentarioDTO> dtoList = new ArrayList<>();

        comentarioRepo.findByNoticia_Id(noticiaId).forEach(comentario -> dtoList.add(toDto(comentario)));
        return dtoList;
    }

    /**
     * Obtiene los comentarios asociados a un horóscopo específico.
     */
    public List<ComentarioDTO> getByHoroscopo(Long horoscopoId) {

        List<ComentarioDTO> dtoList = new ArrayList<>();

        comentarioRepo.findByHoroscopo_Id(horoscopoId).forEach(comentario -> dtoList.add(toDto(comentario)));
        return dtoList;
    }

    /**
     * Convierte una entidad Comentario a su DTO correspondiente.
     */
    private ComentarioDTO toDto(Comentario comentario) {

        ComentarioDTO dto = mapper.map(comentario, ComentarioDTO.class);

        if (comentario.getComentarista() != null) {
            dto.setNombreComentarista(comentario.getComentarista().getNombre());
        }

        if (comentario.getNoticia() != null) {
            dto.setTituloNoticia(comentario.getNoticia().getTitulo());
        }

        if (comentario.getHoroscopo() != null) {

            dto.setSignoHoroscopo(comentario.getHoroscopo().getTipoHoroscopo());
            dto.setHoroscopoId(comentario.getHoroscopo().getId());
        }

        dto.setTipoPublicacion(comentario.getTipoPublicacion());

        return dto;
    }

    /**
     * Busca el horóscopo asociado al comentario según el DTO recibido.
     */
    private Optional<Horoscopo> findHoroscopoForComment(ComentarioDTO data) {
        if (data.getHoroscopoId() != null) {
            return horoscopoRepo.findById(data.getHoroscopoId());
        }

        if (data.getSignoHoroscopo() == null) {
            return Optional.empty();
        }

        return horoscopoRepo.findFirstByTipoHoroscopoOrderByIdDesc(data.getSignoHoroscopo());
    }
}