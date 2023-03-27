package com.koreaIT.example.JAM.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.container.Container;
import com.koreaIT.example.JAM.dto.Article;
import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class ArticleDao {

	public int doWrite(int memberId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", memberId = ?", memberId);
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append(", hit = ?", 0);

		return DBUtil.insert(Container.conn, sql);
	}

	public Article getArticleById(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);
		if (articleMap.isEmpty()) {
			return null;
		}

		return new Article(articleMap);

	}

	public int getArticlesCount(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append(" FROM article");
		sql.append("WHERE id = ?", id);

		return DBUtil.selectRowIntValue(Container.conn, sql);
	}

	public void doDelete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		DBUtil.delete(Container.conn, sql);
	}

	public void doModify(String title, String body, int id) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);

		DBUtil.update(Container.conn, sql);
	}

	public List<Article> getArticles() {
		SecSql sql = new SecSql();

		sql.append("SELECT A.*, M.name AS extra__writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("ORDER BY id DESC;");

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articleListMaps = DBUtil.selectRows(Container.conn, sql);

		for (Map<String, Object> articleMap : articleListMaps) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}

	public void increaseHit(int id) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", id);

		DBUtil.update(Container.conn, sql);
	}

	public List<Article> getForPrintArticles(Map<String, Object> args) {
		SecSql sql = new SecSql();

		String searchKeyword = "";

		if (args.containsKey("searchKeyword")) {
			searchKeyword = (String) args.get("searchKeyword");
		}

		int limitFrom = -1;
		int limitTake = -1;

		if (args.containsKey("limitFrom")) {
			limitFrom = (int) args.get("limitFrom");
		}

		if (args.containsKey("limitFrom")) {
			limitTake = (int) args.get("limitTake");
		}

		sql.append("SELECT A.*, M.name AS extra__writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		if (searchKeyword.length() > 0) {
			sql.append("WHERE A.title LIKE CONCAT('%',?,'%')", searchKeyword);
		}
		sql.append("ORDER BY id DESC");
		if (limitFrom != -1) {
			sql.append("LIMIT ?, ?", limitFrom, limitTake);
		}

		List<Map<String, Object>> articleListMaps = DBUtil.selectRows(Container.conn, sql);

		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> articleMap : articleListMaps) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}

}
