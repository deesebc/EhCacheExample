/**
 * 
 */
package es.home.ehcache.test;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import es.home.ehcache.example.domain.Libro;
import es.home.ehcache.example.repository.LibroRepository;

/**
 * @author dsblanco
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:repository.xml")
public class LibroRepositoryTest {

	private static final Logger LOGGER = Logger.getLogger(LibroRepositoryTest.class);

	@Autowired
	private LibroRepository repository;

	@After
	public void cleanup() {
		LOGGER.debug("-- FIN --");
	}

	private javax.persistence.Cache getCache() {
		return repository.getEntityManagerFactory().getCache();
	}

	@Test
	public void findById() {
		LOGGER.debug("findById - init");

		LOGGER.debug("!!!!! VALORES INICIALES ");
		LOGGER.debug("Contains libro 25: " + getCache().contains(Libro.class, 25));
		LOGGER.debug("EntityFetchCount: " + getStatics().getEntityFetchCount());
		for (int i = 0; i < 2; i++) {
			long init = System.currentTimeMillis();
			repository.findById(25);
			long end = System.currentTimeMillis();
			LOGGER.debug("time findById(25): " + (end - init));
			LOGGER.debug("EntityFetchCount: " + getStatics().getEntityFetchCount());
			LOGGER.debug("Contains libro 25: " + getCache().contains(Libro.class, 25));
			LOGGER.debug(String.format("Second level stats = %1$d, %2$d, %3$d", getStatics()
					.getSecondLevelCachePutCount(), getStatics().getSecondLevelCacheHitCount(),
					getStatics().getSecondLevelCacheMissCount()));
		}

		LOGGER.debug("findById");
	}

	@Test
	public void findByIdRandom() {
		LOGGER.debug("findByIdRandom - init");
		Random random = new Random(450);
		for (int i = 0; i < 10; i++) {
			long init = System.currentTimeMillis();
			Libro objeto = repository.findById(random.nextInt(450));
			LOGGER.debug("Libro " + objeto.getIdent() + " name: " + objeto.getNombre());
			long end = System.currentTimeMillis();
			LOGGER.debug("time: " + (end - init));
			LOGGER.debug(getStatics().getEntityFetchCount());
			getSession().getCache().evictEntityRegion(Libro.class);
			LOGGER.debug(String.format("Second level stats = %1$d, %2$d, %3$d", getStatics()
					.getSecondLevelCachePutCount(), getStatics().getSecondLevelCacheHitCount(),
					getStatics().getSecondLevelCacheMissCount()));
		}

		LOGGER.debug("statics: " + getStatics());
		LOGGER.debug("findByIdRandom");
	}

	@Test
	public void findAll() {
		LOGGER.debug("findAll - init");

		List<Libro> lista = null;
		for (int i = 0; i < 10; i++) {
			long init = System.currentTimeMillis();
			lista = repository.findAll();
			long end = System.currentTimeMillis();
			LOGGER.debug("time: " + (end - init));
		}
		Statistics statistics = getStatics();
		LOGGER.debug(String.format("Second level stats = %1$d, %2$d, %3$d",
				statistics.getSecondLevelCachePutCount(), statistics.getSecondLevelCacheHitCount(),
				statistics.getSecondLevelCacheMissCount()));

		LOGGER.debug("statics: " + getStatics());

		LOGGER.debug("lista size: " + lista.size());
		LOGGER.debug("findAll - end");
		Assert.isTrue(lista.size() > 0);
	}

	private SessionFactory getSession() {
		return ((HibernateEntityManagerFactory) repository.getEntityManagerFactoryLocal())
				.getSessionFactory();
	}

	private Statistics getStatics() {
		final SessionFactory sessionFactory = ((HibernateEntityManagerFactory) repository
				.getEntityManagerFactoryLocal()).getSessionFactory();

		return sessionFactory.getStatistics();
	}

	@Before
	public void init() {
		LOGGER.debug("-- INICIO --");
	}

	public LibroRepository getRepository() {
		return repository;
	}

	public void setRepository(final LibroRepository repository) {
		this.repository = repository;
	}

}
