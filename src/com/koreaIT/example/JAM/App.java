package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.koreaIT.example.JAM.controller.ArticleController;
import com.koreaIT.example.JAM.controller.MemberController;

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

				int actionResult = action(conn, sc, command);

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

	/* 실제 기능을 실행하는 메서드가 아니므로 do 빼줌 */
	private int action(Connection conn, Scanner sc, String command) {

		/* command 입력값이 없을 때 */
		if (command.length() == 0) {
			System.out.println("명령어를 입력해주세요");
			return 0;
		}
		/* 프로그램 종료 */
		if (command.equals("exit")) {
			return -1;
		}

		ArticleController articleController = new ArticleController(conn, sc);
		MemberController memberController = new MemberController(conn, sc);

		/* 회원가입 기능 */
		if (command.equals("member join")) {
			memberController.doJoin(command);

			/* 게시물 작성 */
		} else if (command.equals("article write")) {
			articleController.doWrite();

			/* 게시물 목록 출력 */
		} else if (command.equals("article list")) {
			articleController.showList();

			/* 게시글 수정 */
		} else if (command.startsWith("article modify")) {
			articleController.doModify(command);

			/* 게시글 상세보기 */
		} else if (command.startsWith("article detail")) {
			articleController.showDetail(command);

			/* 게시글 삭제 */
		} else if (command.startsWith("article delete")) {
			articleController.doDelete(command);

		} else {
			System.out.println("명령어를 확인해주세요");
		}
		return 0;
	}

}
