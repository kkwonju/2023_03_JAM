package com.koreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class MemberController {
	private Connection conn;
	private Scanner sc;

	
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void setScanner(Scanner sc) {
		this.sc = sc;
	}

	public void doJoin(String command) {
		String loginId = null;
		String loginPw = null;
		String name = null;
		
		System.out.println("== 회원가입 ==");
		
		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if(loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			
			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", loginId);

			boolean isLoginIdDup= DBUtil.selectRowBooleanValue(conn, sql);
			
			if(isLoginIdDup) {
				System.out.println(loginId + "는(은) 이미 사용중인 아이디입니다");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if(loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			
			System.out.print("비밀번호 확인 : ");
			String loginPwConfirm = sc.nextLine();
			
			if(loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			if(loginPw.equals(loginPwConfirm) == false) {
				System.out.println("비밀번호를 확인해주세요");
				continue;
			}
			break;
		}
		while(true) {
			System.out.print("이름 : ");
			name = sc.nextLine().trim();
			
			if(name.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			break;
		}
		
		SecSql sql = new SecSql();

		sql.append("INSERT INTO member");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);

		int id = DBUtil.insert(conn, sql);

		System.out.println(id + "번 회원 가입");

	}
}
