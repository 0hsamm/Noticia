package co.edu.unbosque.paginanoticia.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;

public class ComentarioDTO {

	 private Long id;

	    private String contenido;

	    private LocalDateTime fecha;

	    private Long comentaristaId;

	    private Long noticiaId;

	    private Long horoscopoId;

	    private TipoPublicacion tipoPublicacion;

	    
	    public ComentarioDTO() {
			// TODO Auto-generated constructor stub
		}


		public ComentarioDTO(String contenido, LocalDateTime fecha, Long comentaristaId, Long noticiaId,
				Long horoscopoId, TipoPublicacion tipoPublicacion) {
			super();
			this.contenido = contenido;
			this.fecha = fecha;
			this.comentaristaId = comentaristaId;
			this.noticiaId = noticiaId;
			this.horoscopoId = horoscopoId;
			this.tipoPublicacion = tipoPublicacion;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getContenido() {
			return contenido;
		}


		public void setContenido(String contenido) {
			this.contenido = contenido;
		}


		public LocalDateTime getFecha() {
			return fecha;
		}


		public void setFecha(LocalDateTime fecha) {
			this.fecha = fecha;
		}


		public Long getComentaristaId() {
			return comentaristaId;
		}


		public void setComentaristaId(Long comentaristaId) {
			this.comentaristaId = comentaristaId;
		}


		public Long getNoticiaId() {
			return noticiaId;
		}


		public void setNoticiaId(Long noticiaId) {
			this.noticiaId = noticiaId;
		}


		public Long getHoroscopoId() {
			return horoscopoId;
		}


		public void setHoroscopoId(Long horoscopoId) {
			this.horoscopoId = horoscopoId;
		}


		public TipoPublicacion getTipoPublicacion() {
			return tipoPublicacion;
		}


		public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
			this.tipoPublicacion = tipoPublicacion;
		}


		@Override
		public int hashCode() {
			return Objects.hash(comentaristaId, contenido, fecha, horoscopoId, id, noticiaId, tipoPublicacion);
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ComentarioDTO other = (ComentarioDTO) obj;
			return Objects.equals(comentaristaId, other.comentaristaId) && Objects.equals(contenido, other.contenido)
					&& Objects.equals(fecha, other.fecha) && Objects.equals(horoscopoId, other.horoscopoId)
					&& Objects.equals(id, other.id) && Objects.equals(noticiaId, other.noticiaId)
					&& tipoPublicacion == other.tipoPublicacion;
		}


		@Override
		public String toString() {
			return "ComentarioDTO [id=" + id + ", contenido=" + contenido + ", fecha=" + fecha + ", comentaristaId="
					+ comentaristaId + ", noticiaId=" + noticiaId + ", horoscopoId=" + horoscopoId
					+ ", tipoPublicacion=" + tipoPublicacion + "]";
		}
	    
	    
	    
	    
	    
}
