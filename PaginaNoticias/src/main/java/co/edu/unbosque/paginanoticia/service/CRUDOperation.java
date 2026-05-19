package co.edu.unbosque.paginanoticia.service;

import java.util.List;

/**
 * Interfaz que define las operaciones básicas de un CRUD genérico.
 * Permite estandarizar la creación, consulta, actualización y eliminación
 * de entidades dentro de los servicios de la aplicación.
 */
public interface CRUDOperation<T> {
	
	public int create(T data);
	public List<T> getAll();
	public int deleteById(Long id);
	public int updateById(Long id, T data);
	public long count();
	public boolean exist(Long id);
	
}