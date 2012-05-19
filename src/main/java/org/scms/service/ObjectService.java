package org.scms.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.scms.model.entity.AbstractIdentityModel;
import org.scms.service.filter.AbstractSearchFilter;

public abstract class ObjectService<F extends AbstractSearchFilter, T extends AbstractIdentityModel>
		extends CRUDService<T> {

	public abstract String completeTypedQueryString(F filter, Map<String, Object> parametersMap);

	public void initQueryParameters(Query query, Map<String, Object> parametersMap) {
		if (parametersMap.isEmpty())
			return;
		for (Map.Entry<String, Object> el : parametersMap.entrySet()) {
			if (el.getValue() instanceof Date) {
				query.setParameter(el.getKey(), (Date) el.getValue(),
						TemporalType.DATE);
			} else {
				query.setParameter(el.getKey(), el.getValue());
			}
		}
	}

	public TypedQuery<T> createTypedQuery(F filter) {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String queryString = completeTypedQueryString(filter, parametersMap);
		TypedQuery<T> query = em.createQuery(queryString, getEntityClass());
		initQueryParameters(query, parametersMap);
		return query;
	}

	public T executeUnique(F filter) {
		try {
			return createTypedQuery(filter).getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public List<T> execute(F filter) {
		try {
			return createTypedQuery(filter).getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Collections.emptyList();
		}
	}

}
