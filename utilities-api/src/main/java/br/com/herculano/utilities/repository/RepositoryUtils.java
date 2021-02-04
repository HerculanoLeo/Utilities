package br.com.herculano.utilities.repository;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

public class RepositoryUtils {

	public static String generateWhere(String where) {
		if (StringUtils.isBlank(where)) {
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

		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		int firstResult = query.getFirstResult();

		return new Long(firstResult);
	}

}
