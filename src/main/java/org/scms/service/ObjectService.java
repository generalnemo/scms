package org.scms.service;

import java.util.List;

import javax.persistence.TypedQuery;

import org.scms.model.entity.AbstractTemporalModel;

public abstract class ObjectService<F, T extends AbstractTemporalModel> extends
		CRUDService<T> {

	public List<T> execute(F filter) {
		try {
			/*String queryString = completeQueryString(filter);
			TypedQuery<T> query = em.createQuery(queryString, getEntityClass());
			initQueryParameters(query, filter);
			return query.getResultList();*/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public abstract String completeQueryString(F filter);

	public abstract void initQueryParameters(TypedQuery<T> query, F filter);

}
