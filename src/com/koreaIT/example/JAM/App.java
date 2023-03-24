package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.koreaIT.example.JAM.container.Container;
import com.koreaIT.example.JAM.controller.ArticleController;
import com.koreaIT.example.JAM.controller.MemberController;

public class App {

	public void start() {
		System.out.println("== 프로그램 시작 ==");
		Container.sc = new Scanner(System.in);
		
		Container.init();

		while (true) {
			System.out.print("명령어 ) ");
			String command = Container.sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");
				Container.conn  = conn;
				
				
				int actionResult = action(command);

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
	private int action(String command) {
		
		ArticleController articleController = Container.articleController;
		MemberController memberController = Container.memberController;
		
		/* command 입력값이 없을 때 */
		if (command.length() == 0) {
			System.out.println("명령어를 입력해주세요");
			return 0;
		}
		/* 프로그램 종료 */
		if (command.equals("exit")) {
			return -1;
		}
		/* 회원가입 기능 */
		if (command.equals("member join")) {
			memberController.doJoin(command);
			/* 로그인 기능 */
		} else if (command.equals("member login")) {
			memberController.doLogin();
			/* 로그아웃 기능 */
		} else if (command.equals("member logout")){
			memberController.doLogout();
			/* 회원정보 보기 */
		} else if (command.equals("member profile")){
			memberController.showProfile();
			/* 게시물 작성 */
		} else if (command.equals("article write")) {
			articleController.doWrite();

			/* 게시물 목록 출력 */
		} else if (command.equals("article list")) {
			articleController.showList();

			/* 게시글 수정 */
		} else if (command.startsWith("article modify ")) {
			articleController.doModify(command);

			/* 게시글 상세보기 */
		} else if (command.startsWith("article detail ")) {
			articleController.showDetail(command);

			/* 게시글 삭제 */
		} else if (command.startsWith("article delete ")) {
			articleController.doDelete(command);

		} else {
			System.out.println("존재하지 않는 명령어입니다");
		}
		return 0;
	}

}
