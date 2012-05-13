package org.scms.service;

import java.util.List;

import javax.ejb.Stateless;

import org.scms.model.entity.CItem;
import org.scms.view.filter.search.CItemSearchFilter;

@Stateless
public class CItemService extends SearchFilterService<CItem> {

	@Override
	public List<CItem> findByFilter(CItemSearchFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
