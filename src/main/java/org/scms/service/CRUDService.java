package org.scms.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.solder.logging.Logger;
import org.scms.model.entity.AbstractTemporalModel;
import org.scms.model.exception.EntityAlreadyExistsException;
import org.scms.util.ReflectionUtil;

public abstract class CRUDService<T extends AbstractTemporalModel> {

	@Inject
	protected EntityManager em;

	@Inject
	protected Logger logger;

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		return ReflectionUtil.getGenericParameterClass(this.getClass(),
				CRUDService.class, 0);
	}

	public List<T> findAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());
		Root<T> from = criteriaQuery.from(getEntityClass());
		CriteriaQuery<T> select = criteriaQuery.select(from);
		TypedQuery<T> typedQuery = em.createQuery(select);
		return typedQuery.getResultList();
	}

	public T findById(Object id) {
		T t = em.find(getEntityClass(), id);
		em.refresh(t);
		return t;
	}

	public void add(T object) throws Exception {
		try {
			em.persist(em.merge(object));
		} catch (PersistenceException e) {
			if (e.getMessage().contains("ConstraintViolationException")) {
				logger.error(e.getMessage(), e);
				throw new EntityAlreadyExistsException(e.getMessage(), e);
			}
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new EntityAlreadyExistsException(e.getMessage(), e);
		}
	}

	public T update(T object) throws Exception {
		try {
			object = em.merge(object);
			em.flush();
			return object;
		} catch (PersistenceException e) {
			if (e.getMessage().contains("ConstraintViolationException")) {
				throw new EntityAlreadyExistsException(e.getMessage(), e);
			}
			throw e;
		} catch (Exception e) {
			throw new EntityAlreadyExistsException(e.getMessage(), e);
		}
	}

	public void remove(T object) throws Exception {
		em.remove(em.merge(object));
	}

}
