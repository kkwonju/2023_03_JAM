package com.koreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller{
	private MemberService memberService;
	
	public MemberController(Connection conn, Scanner sc) {
		super(sc);
		memberService = new MemberService(conn);
	}
	
	/** 회원가입 */
	public void doJoin(String command) {
		String loginId = null;
		String loginPw = null;
		String loginPwConfirm = null;
		String name = null;
		
		System.out.println("== 회원가입 ==");
		
		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if(loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			
			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
			
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
			loginPwConfirm = sc.nextLine();
			
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
		
		memberService.doJoin(loginId, loginPw, name);

		System.out.println(name + "님, 가입되었습니다");
	}
}