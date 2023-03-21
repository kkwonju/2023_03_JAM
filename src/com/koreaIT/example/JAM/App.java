package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	List<Article> articles = null;

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
		int lastArticleId = 0;
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
			int id = lastArticleId + 1;
			System.out.print("제목 : ");
			String title = sc.nextLine();
			System.out.print("내용 : ");
			String body = sc.nextLine();

			PreparedStatement pstmt = null;

			try {
				String sql = "INSERT INTO article";
				sql += " SET regDate = NOW(),";
				sql += "updateDate = NOW(),";
				sql += "title = '" + title + "',";
				sql += "`body` = '" + body + "'";

				System.out.println(sql);

				pstmt = conn.prepareStatement(sql);

				int affectedRow = pstmt.executeUpdate();

				System.out.println("affectedRow : " + affectedRow);

			} catch (SQLException e) {
				System.out.println("에러 : " + e);
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			System.out.println(id + "번 글이 생성되었습니다");
			lastArticleId++;
			
			/* 게시물 목록 출력 */
		} else if (command.equals("article list")) {
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			articles = new ArrayList<>();
			
			try {
				String sql = "SELECT *";
				sql += " FROM article";
				sql += " ORDER BY id ASC;";

				System.out.println(sql);

				pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					int id = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");
					String title = rs.getString("title");
					String body = rs.getString("body");
					Article article = new Article(id, regDate, updateDate, title, body);
					articles.add(article);
				}

			} catch (SQLException e) {
				System.out.println("에러 : " + e);
			} finally {
				try {
					if (rs != null && !rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (articles.size() == 0) {
				System.out.println("게시글이 존재하지 않습니다");
				return 0;
			}
			
			System.out.println(" 번호  /  제목  ");
			
			for (int i = articles.size() - 1; i >= 0; i--) {
				Article article = articles.get(i);
				System.out.printf("  %d    /    %s \n", article.id, article.title);
			}

			/* 게시글 수정 */
		} else if (command.startsWith("article modify")) {
			String[] comDiv = command.split(" ");
			if (comDiv.length < 3) {
				System.out.println("명령어를 확인해주세요");
				return 0;
			}
			int id = Integer.parseInt(comDiv[2]);
			
			Article foundArticle = null;
			
			System.out.println("== 게시물 수정 ==");
			for (Article article : articles) {
				if (article.id == id) {
					foundArticle = article;
				}
			}
			if (foundArticle == null) {
				System.out.println("일치하는 게시물이 없습니다");
				return 0;
			}
			System.out.print("새 제목 : ");
			foundArticle.title = sc.nextLine();
			System.out.print("새 내용 : ");
			foundArticle.body = sc.nextLine();

			PreparedStatement pstmt = null;

			try {
				String sql = "UPDATE article";
				sql += " SET title = '" + foundArticle.title + "', ";
				sql += "`body` = '" + foundArticle.body + "',";
				sql += "updateDate = NOW()";
				sql += " WHERE id = " + foundArticle.id + ";";

				System.out.println(sql);

				pstmt = conn.prepareStatement(sql);

				int affectedRow = pstmt.executeUpdate();
				System.out.println("affectedRow : " + affectedRow);

			} catch (SQLException e) {
				System.out.println("에러 : " + e);

			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println(id + "번 게시물이 수정되었습니다");
			
		} else if (command.startsWith("article delete")) {
			
		} else {
			System.out.println("명령어를 확인해주세요");
		}
		return 0;
	}
}
