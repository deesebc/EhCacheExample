package es.home.ehcache.example.repository.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.home.ehcache.example.domain.Libro;
import es.home.ehcache.example.repository.LibroRepository;
import es.home.recetario.generic.JpaDAOImpl;

@Repository("libroRepositoryImpl")
@Transactional(value = "transactionManagerLocal")
public class LibroRepositoryImpl extends JpaDAOImpl<Integer, Libro> implements LibroRepository {

	@Autowired
	EntityManagerFactory entityManagerFactoryLocal;

	public static final long serialVersionUID = 6287801657971944454L;

	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactoryLocal);
	}

	public EntityManagerFactory getEntityManagerFactoryLocal() {
		return entityManagerFactoryLocal;
	}

	public void setEntityManagerFactoryLocal(final EntityManagerFactory entityManagerFactoryLocal) {
		this.entityManagerFactoryLocal = entityManagerFactoryLocal;
	}

}