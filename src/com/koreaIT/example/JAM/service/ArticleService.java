package com.koreaIT.example.JAM.service;

import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.container.Container;
import com.koreaIT.example.JAM.dao.ArticleDao;
import com.koreaIT.example.JAM.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;
	
	public ArticleService() {
		this.articleDao = Container.articleDao;
	}

	public int doWrite(int memberId, String title, String body, int hit) {
		return articleDao.doWrite(memberId, title, body, hit);
	}

	public Map<String, Object> getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public int getArticlesCount(int id) {
		return articleDao.getArticlesCount(id);
	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public void doModify(String title, String body, int id) {
		articleDao.doModify(title, body, id);
		
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public void increaseHit(int id) {
		articleDao.increaseHit(id);
	}

}
