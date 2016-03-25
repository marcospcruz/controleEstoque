package br.com.marcospcruz.controleestoque.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class CrudDao<T> implements Crud<T> {

	private static final String PERSISTENCE_UNITY = "controlePU";

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public CrudDao() {

		super();

		inicializaEntityManager();

	}

	/**
	 * Método responsável pela inicialização do EntityManagerFactory e
	 * EntityManager
	 */
	private void inicializaEntityManager() {

		if (entityManager == null || !entityManager.isOpen()) {

			// if (entityManagerFactory == null)

			entityManagerFactory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNITY);

			entityManager = entityManagerFactory.createEntityManager();

		}

	}

	@Override
	public T select(int id) {

		return null;
	}

	@Override
	public T update(T entity) {

		inicializaEntityManager();

		try {

			entityManager.getTransaction().begin();

			entity = entityManager.merge(entity);

			entityManager.getTransaction().commit();

		} catch (PersistenceException e) {

			e.printStackTrace();

		} finally {

			entityManager.close();

		}

		return entity;

	}

	@Override
	public void delete(T entity) {

		entityManager.getTransaction().begin();

		entityManager.remove(entity);

		entityManager.getTransaction().commit();

		entityManager.close();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public T busca(Class clazz, int id) {

		T entity = (T) entityManager.find(clazz, id);

		return entity;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscaList(String namedQuery, String parametro, String valor) {

		inicializaEntityManager();

		Query query = entityManager.createNamedQuery(namedQuery);

		query.setParameter(parametro, valor);

		List<T> entities = query.getResultList();

		return entities;

	}

	@SuppressWarnings("unchecked")
	@Override
	public T busca(String namedQuery, String parametro, String valor) {

		inicializaEntityManager();

		Query query = entityManager.createNamedQuery(namedQuery);

		query.setParameter(parametro, valor);

		T entity = (T) query.getSingleResult();

		return entity;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> busca(String namedQuery) {

		inicializaEntityManager();

		Query query = entityManager.createNamedQuery(namedQuery);

		List<T> objetos = query.getResultList();

		return objetos;

	}

}
