package com.koreaIT.example.JAM.session;

import com.koreaIT.example.JAM.dto.Member;

public class Session {
	public Member loginedMember;
	public int loginedMemberId;
	
	public Session() {
		loginedMemberId = -1;
	}

	public void logout() {
		loginedMember = null;
		loginedMemberId = -1;
	}
	
	public void login(Member member) {
		loginedMember = member;
		loginedMemberId = member.id;
	}

	public boolean isLogined() {
		return loginedMemberId != -1;
	}
}
