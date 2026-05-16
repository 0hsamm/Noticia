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
            Optional<Horoscopo> horoscopoOpt = horoscopoRepo.findBySigno(data.getSignoHoroscopo());
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
    public List<ComentarioDTO> getAll() {
        List<ComentarioDTO> dtoList = new ArrayList<>();
        comentarioRepo.findAll() .forEach(comentario -> dtoList.add(toDto(comentario)));

        return dtoList;
    }

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

            Optional<Horoscopo> horoscopoOpt = horoscopoRepo.findBySigno(data.getSignoHoroscopo());

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

    public List<ComentarioDTO> getByNoticia(Long noticiaId) {

        List<ComentarioDTO> dtoList = new ArrayList<>();

        comentarioRepo.findByNoticia_Id(noticiaId).forEach(comentario -> dtoList.add(toDto(comentario)));
        return dtoList;
    }


    public List<ComentarioDTO> getByHoroscopo(Long horoscopoId) {

        List<ComentarioDTO> dtoList = new ArrayList<>();

        comentarioRepo.findByHoroscopo_Id(horoscopoId).forEach(comentario -> dtoList.add(toDto(comentario)));
        return dtoList;
    }


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
        }

        dto.setTipoPublicacion(comentario.getTipoPublicacion());

        return dto;
    }
}