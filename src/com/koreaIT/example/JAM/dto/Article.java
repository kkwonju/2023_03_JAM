package com.koreaIT.example.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Object {
	public int id; // column과 1:1 매칭
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public int memberId;
	public String title;
	public String body;
	public int hit;
	
	public String extra__writer; // 매칭되지 않음 extra

	public Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
	
//	public Article(int id, LocalDateTime regDate, LocalDateTime updateDate, String title, String body) {
//		this.id = id;
//		this.regDate = regDate;
//		this.updateDate = updateDate;
//		this.title = title;
//		this.body = body;
//	}

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.memberId = (int) articleMap.get("memberId");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.hit = (int) articleMap.get("hit");
		
		if(articleMap.get("extra__writer") != null) {
			this.extra__writer = (String) articleMap.get("extra__writer");
		}
	}
	
	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", memberId=" + memberId
				+ ", title=" + title + ", body=" + body + ", hit=" + hit + ", extra__writer=" + extra__writer + "]";
	}
}
