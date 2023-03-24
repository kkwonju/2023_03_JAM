package com.koreaIT.example.JAM.controller;

import com.koreaIT.example.JAM.container.Container;
import com.koreaIT.example.JAM.dto.Member;
import com.koreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
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
			loginId = Container.sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup) {
				System.out.println(loginId + "는(은) 이미 사용중인 아이디입니다");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = Container.sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			System.out.print("비밀번호 확인 : ");
			loginPwConfirm = Container.sc.nextLine();

			if (loginPw.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			if (loginPw.equals(loginPwConfirm) == false) {
				System.out.println("비밀번호를 확인해주세요");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("이름 : ");
			name = Container.sc.nextLine().trim();

			if (name.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}
			break;
		}

		memberService.doJoin(loginId, loginPw, name);

		System.out.println(name + "님, 가입되었습니다");
	}

	/** 로그인 기능*/
	public void doLogin() {
		String loginId = null;
		String loginPw = null;
		
		while (true) {
			System.out.print("아이디 : ");
			loginId = Container.sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("필수 입력란입니다");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
			
			if (isLoginIdDup == false) {
				System.out.println(loginId + "일치하는 회원이 없습니다");
				continue;
			}
			break;
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		int maxTryCount = 3;
		int tryCount = 0;
		while (true) {
			if (tryCount >= maxTryCount) {
				System.out.println("비밀번호를 확인하고 다시 입력해주세요");
				break;
			}
			System.out.print("비밀번호 : ");
			loginPw = Container.sc.nextLine().trim();

			if (loginPw.length() == 0) {
				tryCount++;
				System.out.println("필수 입력란입니다");
				continue;
			}
			
			if(member.loginPw.equals(loginPw) == false) {
				tryCount++;
				System.out.println("비밀번호가 틀렸습니다");
				continue;
			}
			break;
		}
		Container.session.loginedMember = member;
		Container.session.loginedMemberId = member.id;
		
		System.out.println(member.name + "님, 반갑습니다");
	}

	public void showProfile() {
		if(Container.session.loginedMemberId == -1) {
			System.out.println("로그인 상태가 아닙니다");
		} else {
			System.out.println(Container.session.loginedMember);
		}
	}
}