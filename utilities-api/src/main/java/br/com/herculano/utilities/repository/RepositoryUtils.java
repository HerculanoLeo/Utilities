package br.com.herculano.utilities.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

public class RepositoryUtils {
	
	public static String generateWhere(String where) {
		if(StringUtils.isBlank(where)) {
			where += " WHERE ";
		} else {
			where += " AND ";
		}
		return where;
	}
	
	public static String generateWhere(String where, String clause) {
		String result = generateWhere(where);
		
		result += clause;
		
		return result;
	}
	
	public static String adicionarPaginacao(Pageable page) {
		String queryStr = "";
		
		queryStr += " LIMIT " + page.getPageSize();
		queryStr += " OFFSET " + page.getOffset();
		
		return queryStr;
	}
	
	public static Long totalRegistros(String query, EntityManager em) {
		String queryStr = "SELECT COUNT(count.*) FROM (" + query + ") AS count";
		
		int firstResult = em.createNativeQuery(queryStr).getFirstResult();
		
		return new Long(firstResult);
	}
	
	public static Long totalRegistros(String queryStr, EntityManager em, Map<String, Object> params) {
		queryStr = "SELECT COUNT(count.*) FROM (" + queryStr + ") AS count";
		
		Query query = em.createNativeQuery(queryStr);
		
		for(Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		
		int firstResult = query.getFirstResult();
		
		return new Long(firstResult);
	}
	
	public static Long totalRegistros(EntityManager em, CriteriaBuilder criteriaBuilder, List<Predicate> predicates, Class<?> entityType, Map<String, JoinType> mapJoins) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<?> root = criteriaQuery.from(entityType);
        
        for (Map.Entry<String, JoinType> entry : mapJoins.entrySet()) {
        	root.join(entry.getKey(), entry.getValue());
        }

        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        return em.createQuery(criteriaQuery).getSingleResult();
	}
	
}
