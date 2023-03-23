package com.koreaIT.example.JAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.dto.Article;
import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;
import com.koreaIT.example.JAM.util.Util;

public class ArticleController extends Controller{

	/** 게시글 작성 */
	public void doWrite() {
		System.out.println("== 게시물 작성 ==");
		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);

		int id = DBUtil.insert(conn, sql);

		System.out.println(id + "번 글이 생성되었습니다");
	}
	/** 게시글 목록 출력*/
	public void showList() {
		System.out.println("== 게시물 목록 ==");

		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC;");

		List<Map<String, Object>> articleListMaps = DBUtil.selectRows(conn, sql);

		for (Map<String, Object> articleMap : articleListMaps) {
			articles.add(new Article(articleMap));
		}

		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		System.out.println(" 번호  /  제목  ");

		for (Article article : articles) {
			System.out.printf("  %d    /    %s \n", article.id, article.title);
		}
	}
	/** 게시글 상세 출력 */
	public void showDetail(String command) {
		int id = Integer.parseInt(command.split(" ")[2]);
		
		SecSql sql = new SecSql();
		
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		
		if (articleMap.isEmpty()) {
			System.out.println(id + "번 글은 존재하지 않습니다");
			return;
		}
		
		Article article = new Article(articleMap);
		
		System.out.println("번호 : " + article.id);
		System.out.println("작성일 : " + Util.getNotDateTimeStr(article.regDate));
		System.out.println("수정일 : " + Util.getNotDateTimeStr(article.updateDate));
		System.out.println("제목 : " + article.title);
		System.out.println("내용 : " + article.body);
	}
	/** 게시글 수정 */
	public void doModify(String command) {
		int id = Integer.parseInt(command.split(" ")[2]);

		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append(" FROM article");
		sql.append("WHERE id = ?", id);

		int articlesCount = DBUtil.selectRowIntValue(conn, sql);

		if (articlesCount == 0) {
			System.out.println(id + "번 글은 존재하지 않습니다");
			return;
		}

		System.out.println("== 게시물 수정 ==");
		System.out.print("새 제목 : ");
		String title = sc.nextLine();
		System.out.print("새 내용 : ");
		String body = sc.nextLine();

		sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);

		DBUtil.update(conn, sql);

		System.out.println(id + "번 글이 수정되었습니다");
		
	}
	/** 게시글 삭제 */
	public void doDelete(String command) {
		int id = Integer.parseInt(command.split(" ")[2]);

		System.out.println("== 게시물 삭제 ==");

		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append(" FROM article");
		sql.append("WHERE id = ?", id);

		int articlesCount = DBUtil.selectRowIntValue(conn, sql);

		if (articlesCount == 0) {
			System.out.println(id + "번 글은 존재하지 않습니다");
			return;
		}

		sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		DBUtil.delete(conn, sql);

		System.out.println(id + "번 게시물이 삭제되었습니다");
	}
}
