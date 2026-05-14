package co.edu.unbosque.paginanoticia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.entity.Comentario;
import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import co.edu.unbosque.paginanoticia.entity.Noticia;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.repository.ComentarioRepository;
import co.edu.unbosque.paginanoticia.repository.HoroscopoRepository;
import co.edu.unbosque.paginanoticia.repository.NoticiaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;

@Service
public class ComentarioService
        implements CRUDOperation<ComentarioDTO> {

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
        if (data.getContenido() == null
                || data.getContenido().trim().isEmpty()) {
            return 1;
        }

        if (data.getTipoPublicacion() == null) {
            return 2;
        }

        Optional<UsuarioComentarista> comentaristaOpt = comentaristaRepo.findById(data.getComentaristaId());
        if (comentaristaOpt.isEmpty()) {
            return 3;
        }

        
        Comentario comentario = mapper.map(data, Comentario.class);
        comentario.setFecha(LocalDateTime.now());
        comentario.setComentarista(comentaristaOpt.get());

        if (data.getTipoPublicacion() == TipoPublicacion.NOTICIA) {
            if (data.getNoticiaId() == null) {
                return 4;
            }

            Optional<Noticia> noticiaOpt = noticiaRepo.findById(data.getNoticiaId());

            if (noticiaOpt.isEmpty()) {
                return 5;
            }
            comentario.setNoticia(noticiaOpt.get());
            comentario.setHoroscopo(null);
        }


        if (data.getTipoPublicacion() == TipoPublicacion.HOROSCOPO) {
            if (data.getHoroscopoId() == null) {
                return 6;
            }

            Optional<Horoscopo> horoscopoOpt = horoscopoRepo.findById(data.getHoroscopoId());
            if (horoscopoOpt.isEmpty()) {
                return 7;
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
        comentarioRepo.delete(comentarioOpt.get());
        return 0;
    }

    @Override
    public int updateById(Long id, ComentarioDTO data) {

        Optional<Comentario> comentarioOpt = comentarioRepo.findById(id);
        if (comentarioOpt.isEmpty()) {
            return 5;
        }

        if (data.getContenido() == null || data.getContenido().trim().isEmpty()) {
            return 1;
        }

        Optional<UsuarioComentarista> comentaristaOpt = comentaristaRepo.findById(data.getComentaristaId());
        if (comentaristaOpt.isEmpty()) {
            return 2;
        }

        Comentario comentario = comentarioOpt.get();
        comentario.setContenido(data.getContenido());
        comentario.setComentarista(comentaristaOpt.get());

        if(data.getTipoPublicacion() == TipoPublicacion.NOTICIA) {

            Optional<Noticia> noticiaOpt = noticiaRepo.findById(data.getNoticiaId());

            if (noticiaOpt.isEmpty()) {
                return 3;
            }
            comentario.setNoticia(
                    noticiaOpt.get());
        }

        if(data.getTipoPublicacion() == TipoPublicacion.HOROSCOPO) {

            Optional<Horoscopo> horoscopoOpt = horoscopoRepo.findById(data.getHoroscopoId());
            if (horoscopoOpt.isEmpty()) {
                return 4;
            }
            comentario.setHoroscopo(horoscopoOpt.get());
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

            dto.setComentaristaId(comentario.getComentarista().getId());
        }

        if (comentario.getNoticia() != null) {

            dto.setNoticiaId(comentario.getNoticia().getId());
        }

        if (comentario.getHoroscopo() != null) {

            dto.setHoroscopoId(comentario.getHoroscopo().getId());
        }
        dto.setTipoPublicacion(comentario.getTipoPublicacion());

        return dto;
    }
}