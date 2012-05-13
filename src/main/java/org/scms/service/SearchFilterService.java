package org.scms.service;

import java.util.List;

import org.scms.model.entity.AbstractTemporalModel;
import org.scms.model.entity.CItem;
import org.scms.view.filter.search.CItemSearchFilter;

public abstract class SearchFilterService<T extends AbstractTemporalModel>
		extends CRUDService<T> {

	public abstract List<CItem> findByFilter(CItemSearchFilter filter);

}
