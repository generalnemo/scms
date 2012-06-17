package org.scms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.scms.model.entity.CItemRevision;
import org.scms.service.filter.CItemRevisionSearchFilter;

@Stateless
public class CItemRevisionService extends
		AbstractObjectService<CItemRevisionSearchFilter, CItemRevision> {

	@Override
	public String completeTypedQueryString(CItemRevisionSearchFilter filter,
			Map<String, Object> parametersMap) {
		StringBuffer query = new StringBuffer("SELECT c FROM CItemRevision c");
		List<String> conditions = new ArrayList<String>();
		if (filter.getId() != null) {
			conditions.add("c.id = :cItemRevisionId");
			parametersMap.put("cItemRevisionId", filter.getId());
		}
		if (filter.getCreatedAt() != null) {
			conditions.add("c.createdAt <= :createdAt");
			parametersMap.put("createdAt", filter.getCreatedAt());
		}
		if (filter.getType() != null) {
			conditions.add("c.cItem.type = :type");
			parametersMap.put("type", filter.getType());
		}
		if (filter.getUserName() != null && !filter.getUserName().equals("")) {
			conditions.add("c.cItem.createdBy.userLoginName = :userCreated");
			parametersMap.put("userCreated", filter.getUserName());
		}
		if (filter.getcItemId() != null) {
			conditions.add("c.cItem.id = :cItemId");
			parametersMap.put("cItemId", filter.getcItemId());
		}
		if (filter.getcItemName() != null && !filter.getcItemName().equals("")) {
			conditions.add("c.cItem.name = :cItemName");
			parametersMap.put("cItemName", filter.getcItemName());
		}
		if (conditions.isEmpty())
			return query.toString();
		StringBuffer wherePart = new StringBuffer(" WHERE ");
		for (String condition : conditions) {
			if (conditions.indexOf(condition) > 0)
				wherePart.append(" AND ");
			wherePart.append(condition);
		}
		return query.append(wherePart + " ORDER BY c.cItem.id").toString();
	}

}
