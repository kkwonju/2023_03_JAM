package com.koreaIT.example.JAM.container;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.example.JAM.controller.ArticleController;
import com.koreaIT.example.JAM.controller.MemberController;
import com.koreaIT.example.JAM.dao.ArticleDao;
import com.koreaIT.example.JAM.dao.MemberDao;
import com.koreaIT.example.JAM.service.ArticleService;
import com.koreaIT.example.JAM.service.MemberService;
import com.koreaIT.example.JAM.session.Session;

public class Container {
	
	public static ArticleController articleController;
	public static MemberController memberController;
	
	public static ArticleService articleService;
	public static MemberService memberService;
		
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	
	public static Session session;

	public static Scanner sc;
	
	public static Connection conn;
	
	public static void init() {
		session = new Session();
		
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		
		articleService = new ArticleService();
		memberService = new MemberService();
		
		articleController = new ArticleController();
		memberController = new MemberController();
	}
	
	
}
