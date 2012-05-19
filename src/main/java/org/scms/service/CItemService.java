package org.scms.service;

import java.util.Map;

import javax.ejb.Stateless;

import org.scms.model.entity.CItem;
import org.scms.service.filter.CItemSearchFilter;

@Stateless
public class CItemService extends ObjectService<CItemSearchFilter, CItem> {

	@Override
	public String completeQueryString(CItemSearchFilter filter, Map<String, Object> parametersMap) {
		return null;
	}
}
