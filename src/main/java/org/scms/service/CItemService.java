package org.scms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.scms.model.entity.CItem;
import org.scms.service.filter.CItemSearchFilter;

@Stateless
public class CItemService extends AbstractObjectService<CItemSearchFilter, CItem> {

	@Override
	public String completeTypedQueryString(CItemSearchFilter filter,
			Map<String, Object> parametersMap) {
		StringBuffer query = new StringBuffer("SELECT c FROM CItem c");
		List<String> conditions = new ArrayList<String>();
		if (filter.getId() != null) {
			conditions.add("c.id = :cItemId");
			parametersMap.put("cItemId", filter.getId());
		}
		if (filter.getCreatedAt() != null) {
			conditions.add("c.createdAt <= :createdAt");
			parametersMap.put("createdAt", filter.getCreatedAt());
		}
		if (filter.getType() != null) {
			conditions.add("c.type = :type");
			parametersMap.put("type", filter.getType());
		}
		if (filter.getUserName() != null) {
			conditions.add("c.createdBy.userName = :userCreated");
			parametersMap.put("userCreated", filter.getUserName());
		}
		if (conditions.isEmpty())
			return query.toString();
		StringBuffer wherePart = new StringBuffer(" WHERE ");
		for (String condition : conditions) {
			if (conditions.indexOf(condition) > 0)
				wherePart.append(" AND ");
			wherePart.append(condition);
		}
		return query.append(wherePart).toString();
	}
}
