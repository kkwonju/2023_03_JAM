package com.koreaIT.example.JAM.controller;

import java.util.List;
import java.util.Map;

import javax.naming.directory.SearchControls;

import com.koreaIT.example.JAM.container.Container;
import com.koreaIT.example.JAM.dto.Article;
import com.koreaIT.example.JAM.dto.Member;
import com.koreaIT.example.JAM.service.ArticleService;
import com.koreaIT.example.JAM.service.MemberService;
import com.koreaIT.example.JAM.util.Util;

public class ArticleController extends Controller {
	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;
	}

	/** 게시글 작성 */
	public void doWrite() {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}

		System.out.println("== 게시물 작성 ==");
		System.out.print("제목 : ");
		String title = Container.sc.nextLine();
		System.out.print("내용 : ");
		String body = Container.sc.nextLine();

		int memberId = Container.session.loginedMemberId;

		int id = articleService.doWrite(memberId, title, body);

		System.out.println(id + "번 글이 생성되었습니다");
	}

	/** 게시글 목록 출력 */
	public void showList(String command) {
		String[] cmdBits = command.split(" ");

		int page = 1;
		String searchKeyword = null;

		// 몇 페이지
		if (cmdBits.length >= 3) {
			page = Integer.parseInt(cmdBits[2]);
		}
		// 검색어
		if (cmdBits.length >= 4) {
			searchKeyword = cmdBits[3];
		}
		// 한 페이지에 5개씩
		int itemsInAPage = 5;

		List<Article> articles = articleService.getForPrintArticles(page, itemsInAPage, searchKeyword);

		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		System.out.println("== 게시물 목록 ==");
		System.out.println(" 번호  /  작성자  /   제목   /   조회");
		for (Article article : articles) {
			System.out.printf("  %d   /   %s   /   %s   /   %d   \n", article.id, article.extra__writer, article.title,
					article.hit);
		}
	}

	/** 게시글 상세보기 */
	public void showDetail(String command) {
		String[] comDiv = command.split(" ");
		if (comDiv.length < 3) {
			System.out.println("게시글 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(comDiv[2]);

		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.println(id + "번 글은 존재하지 않습니다");
			return;
		}

		articleService.increaseHit(id);

		System.out.println("== 게시글 상세보기 ==");
		System.out.println("번호 : " + article.id);
		System.out.println("작성자 : " + article.memberId);
		System.out.println("작성일 : " + Util.getNotDateTimeStr(article.regDate));
		System.out.println("수정일 : " + Util.getNotDateTimeStr(article.updateDate));
		System.out.println("제목 : " + article.title);
		System.out.println("내용 : " + article.body);
		System.out.println("조회 : " + article.hit);
	}

	/** 게시글 수정 */
	public void doModify(String command) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		String[] comDiv = command.split(" ");
		if (comDiv.length < 3) {
			System.out.println("게시글 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(comDiv[2]);

		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.println(id + "번 글은 존재하지 않습니다");
			return;
		}

		if (article.memberId != Container.session.loginedMemberId) {
			System.out.println("게시글에 대한 권한이 없습니다");
			return;
		}

		System.out.println("== 게시물 수정 ==");
		System.out.print("새 제목 : ");
		String title = Container.sc.nextLine();
		System.out.print("새 내용 : ");
		String body = Container.sc.nextLine();

		articleService.doModify(title, body, id);

		System.out.println(id + "번 글이 수정되었습니다");

	}

	/** 게시글 삭제 */
	public void doDelete(String command) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		String[] comDiv = command.split(" ");
		if (comDiv.length < 3) {
			System.out.println("게시글 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(comDiv[2]);

		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.println(id + "번 글은 존재하지 않습니다");
			return;
		}

		if (article.memberId != Container.session.loginedMemberId) {
			System.out.println("게시글에 대한 권한이 없습니다");
			return;
		}

		articleService.doDelete(id);

		System.out.println("== 게시물 삭제 ==");
		System.out.println(id + "번 게시물이 삭제되었습니다");
	}
}
