package com.koreaIT.example.JAM.session;

import com.koreaIT.example.JAM.dto.Member;

public class Session {
	public Member loginedMember;
	public int loginedMemberId;
	
	public Session() {
		loginedMemberId = -1;
	}
}
