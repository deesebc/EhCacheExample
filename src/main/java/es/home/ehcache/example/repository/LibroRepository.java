/**
 * 
 */
package es.home.ehcache.example.repository;

import javax.persistence.EntityManagerFactory;

import es.home.ehcache.example.domain.Libro;
import es.home.recetario.generic.JpaDAO;

/**
 * @author daniel
 * 
 */
public interface LibroRepository extends JpaDAO<Integer, Libro> {

	EntityManagerFactory getEntityManagerFactoryLocal();
}
