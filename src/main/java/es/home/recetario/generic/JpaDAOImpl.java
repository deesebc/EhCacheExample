package es.home.recetario.generic;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public abstract class JpaDAOImpl<K, E> extends JpaDaoSupport implements JpaDAO<K, E> {
	protected Class<E> entityClass;

	public EntityManager getEntityManager() {
		return getJpaTemplate().getEntityManager();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return getJpaTemplate().getEntityManagerFactory();
	}

	@SuppressWarnings("unchecked")
	public JpaDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public Long countAll() {
		return Long.valueOf(findAll().size());
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		Object res = getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(final EntityManager em) throws PersistenceException {
				Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h");
				return q.getResultList();
			}

		});

		return (List<E>) res;
	}

	public List<E> findAllPaginate(final int first, final int pageSize, final String orderBy) {
		Object res = getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(final EntityManager em) throws PersistenceException {
				Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h ORDER BY "
						+ orderBy + " ASC");
				q.setFirstResult(first);
				q.setMaxResults(pageSize);
				return q.getResultList();
			}

		});

		return (List<E>) res;
	}

	public E findById(final K id) {
		return getJpaTemplate().find(entityClass, id);
	}

	public E flush(final E entity) {
		getJpaTemplate().flush();
		return entity;
	}

	public E merge(final E entity) {
		return getJpaTemplate().merge(entity);
	}

	public void persist(final E entity) {
		getJpaTemplate().persist(entity);
	}

	public void refresh(final E entity) {
		getJpaTemplate().refresh(entity);
	}

	public void remove(final E entity) {
		// getJpaTemplate().remove(entity);
		getJpaTemplate().remove(getJpaTemplate().merge(entity));
	}

	@SuppressWarnings("unchecked")
	public Integer removeAll() {
		return (Integer) getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(final EntityManager em) throws PersistenceException {
				Query q = em.createQuery("DELETE FROM " + entityClass.getName() + " h");
				return q.executeUpdate();
			}

		});
	}

}