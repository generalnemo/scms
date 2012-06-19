package org.scms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.scms.model.entity.CItem;
import org.scms.model.entity.LogEntry;
import org.scms.model.entity.LogEntryEditedProperty;
import org.scms.service.filter.CItemSearchFilter;

@Stateless
public class CItemService extends
		AbstractObjectService<CItemSearchFilter, CItem> {

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
			conditions.add("c.createdBy.userLoginName = :userCreated");
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

	public CItem findById(Object id) {
		CItem object = em.find(CItem.class, id);
		if (object == null)
			return null;
		em.refresh(object);
		object.getRevisions().size();
		object.getRevisions().get(0).getRelationships().size();
		return object;
	}

	public CItem lazyFindById(Object id) {
		return super.findById(id);
	}

	public List<LogEntry> findLogEntriesByCItemId(Long id) {
		CItem object = em.find(CItem.class, id);
		if (object == null)
			return Collections.emptyList();
		em.refresh(object);
		object.getLogEntries().size();
		List<LogEntry> result = object.getLogEntries();
		for (LogEntry entry : result) {
			entry.getEditedProperties().size();
		}
		return result;
	}
}
