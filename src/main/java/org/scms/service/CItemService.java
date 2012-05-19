package org.scms.service;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.scms.model.entity.CItem;
import org.scms.service.filter.CItemSearchFilter;

@Stateless
public class CItemService extends ObjectService<CItemSearchFilter, CItem> {

	@Override
	public String completeQueryString(CItemSearchFilter filter) {
		return null;
	}

	@Override
	public void initQueryParameters(TypedQuery<CItem> query, CItemSearchFilter filter) {
		// TODO Auto-generated method stub

	}

}
