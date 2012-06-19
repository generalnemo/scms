package org.scms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.scms.enumerate.citem.CItemRelationshipType;
import org.scms.model.entity.CItem;
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
			conditions.add("LOWER(c.cItem.name) LIKE :cItemName");
			parametersMap.put("cItemName", "%"+filter.getcItemName().toLowerCase()+"%");
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

	public int updateRelationships(CItemRelationshipType type,
			CItemRevision revisionFrom) {
		em.merge(revisionFrom);
		Query q = em
				.createQuery("UPDATE CItemsRelationship SET cItemRevisionFrom.id = :revisionId WHERE type = :type AND cItemRevisionFrom IN (SELECT c FROM CItemRevision c WHERE c.cItem.id = :cItemId) ");
		q.setParameter("cItemId", revisionFrom.getcItem().getId());
		q.setParameter("revisionId", revisionFrom.getId());
		q.setParameter("type", type);
		return q.executeUpdate();
	}

	public CItemRevision getCurrentRevision(CItem item) {
		TypedQuery<CItemRevision> q = em
				.createQuery(
						"SELECT c FROM CItemRevision c WHERE c.cItem.id = :cItemId AND c.currentRevision = :flag",
						CItemRevision.class);
		q.setParameter("cItemId", item.getId());
		q.setParameter("flag", true);
		return q.getSingleResult();
	}

}
