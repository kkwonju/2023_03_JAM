package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		List<Article> articles = null;
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);

		int lastArticleId = 0;

		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}
			/* 게시물 작성 */
			if (command.equals("article write")) {
				System.out.println("== 게시물 작성 ==");
				int id = lastArticleId + 1;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				// String regDate = 
				// articles.add(new Article(id, regDate, updateDate, title, body));
				System.out.println(id + "번 글이 생성되었습니다");
				lastArticleId++;

				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", ""); // root : mysql 기본 계정 , "" : 비밀번호
					System.out.println("연결 성공!");

					String sql = "INSERT INTO article";
					sql += " SET regDate = NOW(),"; // " SET , 문자열 간 공백, 붙으면 하나로 인식
					sql += "updateDate = NOW(),";
					sql += "title = '" + title + "',";
					sql += "`body` = '" + body + "'";

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					int affectedRow = pstmt.executeUpdate();

					System.out.println("affectedRow : " + affectedRow);

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
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
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				
			/* 게시물 목록 출력 */
			} else if (command.equals("article list")) {
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				articles = new ArrayList<>();
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "SELECT *";
					sql += " FROM article";
//					sql += " ORDER BY id DESC;";

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					rs = pstmt.executeQuery();

					while (rs.next()) {// next() ?
						int id = rs.getInt("id");
						String regDate = rs.getString("regDate");
						String updateDate = rs.getString("updateDate");
						String title = rs.getString("title");
						String body = rs.getString("body");
						Article article = new Article(id, regDate, updateDate, title, body);
						articles.add(article);
					}

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
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
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (articles.size() == 0) {
					System.out.println("게시글이 존재하지 않습니다");
					continue;
				}
				System.out.println(" 번호  /  제목  ");
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("  %d    /    %s \n", article.id, article.title);
				}

			} else if(command.startsWith("article modify")){ 
				String[] comDiv = command.split(" ");
				if(comDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					continue;
				}
				int id = Integer.parseInt(comDiv[2]);
				Article foundArticle = null;
				System.out.println("== 게시물 수정 ==");
				for(Article article : articles) {
					if(article.id == id) {
						foundArticle = article;
					}
				}
				if(foundArticle == null) {
					System.out.println("일치하는 게시물이 없습니다");
					continue;
				}
				System.out.print("새 제목 : ");
				foundArticle.title = sc.nextLine();
				System.out.print("새 내용 : ");
				foundArticle.body = sc.nextLine();
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				
				// try catch (finally) 예외처리
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");
					
					String sql = "UPDATE article";
					sql += " SET title = '" + foundArticle.title + "', ";
					sql += "`body` = '" + foundArticle.body + "',";
					sql += "updateDate = NOW()";
					sql += " WHERE id = " + foundArticle.id + ";";

					System.out.println(sql);
					
					pstmt = conn.prepareStatement(sql);
					
					int affectedRow = pstmt.executeUpdate();
					System.out.println("affectedRow : " + affectedRow);
					
				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러 : " + e);

				} finally {
					// 끄는 순서는 반대로
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				System.out.println(id + "번 게시물이 수정되었습니다");
			} else if(command.startsWith("article delete")){
				
			}else {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
		}System.out.println("== 프로그램 종료 ==");
		sc.close();
	}
}