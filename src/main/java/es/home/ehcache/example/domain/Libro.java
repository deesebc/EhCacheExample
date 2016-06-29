package es.home.ehcache.example.domain;

// Generated 01-mar-2013 9:51:56 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Disco generated by hbm2java
 */
@Entity
@Table(name = "libro", catalog = "almacen")
// necesario
// @Cacheable
public class Libro implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ident;
	private String nombre;
	private String autor;
	private String serie;
	private String argumento;
	private Integer paginas;
	private Integer volumen;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date publicacion;

	public Libro() {
	}

	public Libro(final Integer ident) {
		this.ident = ident;
	}

	public Libro(final Integer ident, final String nombre, final String autor, final String serie) {
		super();
		this.ident = ident;
		this.nombre = nombre;
		this.autor = autor;
		this.serie = serie;
	}

	public Libro(final Integer ident, final String nombre, final String autor, final String serie,
			final String argumento, final Integer paginas, final Integer volumen) {
		super();
		this.ident = ident;
		this.nombre = nombre;
		this.autor = autor;
		this.serie = serie;
		this.argumento = argumento;
		this.paginas = paginas;
		this.volumen = volumen;
	}

	@Column(name = "PUBLICACION")
	public Date getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(final Date publicacion) {
		this.publicacion = publicacion;
	}

	@Column(name = "ARGUMENTO", length = 400)
	public String getArgumento() {
		return argumento;
	}

	@Column(name = "AUTOR", length = 45)
	public String getAutor() {
		return autor;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IDENT", unique = true, nullable = false)
	public Integer getIdent() {
		return ident;
	}

	@Column(name = "NOMBRE", length = 45)
	public String getNombre() {
		return nombre;
	}

	@Column(name = "PAGINAS", length = 11)
	public Integer getPaginas() {
		return paginas;
	}

	@Column(name = "SERIE", length = 45)
	public String getSerie() {
		return serie;
	}

	@Column(name = "VOLUMEN", length = 11)
	public Integer getVolumen() {
		return volumen;
	}

	public void setArgumento(final String argumento) {
		this.argumento = argumento;
	}

	public void setAutor(final String autor) {
		this.autor = autor;
	}

	public void setIdent(final Integer ident) {
		this.ident = ident;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public void setPaginas(final Integer paginas) {
		this.paginas = paginas;
	}

	public void setSerie(final String serie) {
		this.serie = serie;
	}

	public void setVolumen(final Integer volumen) {
		this.volumen = volumen;
	}

}