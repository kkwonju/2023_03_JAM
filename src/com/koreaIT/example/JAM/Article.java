package com.koreaIT.example.JAM;

public class Article extends Object {
	int id;
	String regDate;
	String updateDate;
	String title;
	String body;

	public Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
	
	public Article(int id, String regDate, String updateDate, String title, String body) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
	}
}

//@Override
//public String toString() {
//	return "article [id=" + id + ", regDate="
//}