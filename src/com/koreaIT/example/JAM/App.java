package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class App {
	public void start() {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");

				int actionResult = doAction(conn, sc, command);

				if (actionResult == -1) {
					System.out.println("프로그램 종료");
					break;
				}
			} catch (SQLException e) {
				System.out.println("에러 : " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int doAction(Connection conn, Scanner sc, String command) {
		/* command 입력값이 없을 때 */
		if (command.length() == 0) {
			System.out.println("명령어를 입력해주세요");
			return 0;
		}
		/* 프로그램 종료 */
		if (command.equals("exit")) {
			return -1;
		}
		/* 게시물 작성 */
		if (command.equals("article write")) {
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

			/* 게시물 목록 출력 */
		} else if (command.equals("article list")) {
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
				return 0;
			}

			System.out.println(" 번호  /  제목  ");

			for (Article article : articles) {
				System.out.printf("  %d    /    %s \n", article.id, article.title);
			}

			/* 게시글 수정 */
		} else if (command.startsWith("article modify")) {
			int id = Integer.parseInt(command.split(" ")[2]);

			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*)");
			sql.append(" FROM article");
			sql.append("WHERE id = ?", id);

			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.println(id + "번 글은 존재하지 않습니다");
				return 0;
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

		} else if (command.startsWith("article detail")) {
			int id = Integer.parseInt(command.split(" ")[2]);

			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*)");
			sql.append(" FROM article");
			sql.append("WHERE id = ?", id);

			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.println(id + "번 글은 존재하지 않습니다");
				return 0;
			}

			sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
			Article article = new Article(articleMap);

			System.out.println("번호 : " + article.id);
			System.out.println("작성일 : " + article.regDate);
			System.out.println("수정일 : " + article.updateDate);
			System.out.println("제목 : " + article.title);
			System.out.println("내용 : " + article.body);

		} else if (command.startsWith("article delete")) {
			int id = Integer.parseInt(command.split(" ")[2]);

			System.out.println("== 게시물 삭제 ==");

			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*)");
			sql.append(" FROM article");
			sql.append("WHERE id = ?", id);

			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.println(id + "번 글은 존재하지 않습니다");
				return 0;
			}

			sql = new SecSql();

			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);

			DBUtil.delete(conn, sql);

			System.out.println(id + "번 게시물이 삭제되었습니다");

		} else {
			System.out.println("명령어를 확인해주세요");
		}
		return 0;
	}
}
