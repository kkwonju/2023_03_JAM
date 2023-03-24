package com.koreaIT.example.JAM.controller;

import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.container.Container;
import com.koreaIT.example.JAM.dto.Article;
import com.koreaIT.example.JAM.service.ArticleService;
import com.koreaIT.example.JAM.util.Util;

public class ArticleController extends Controller {
	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;
	}

	/** 게시글 작성 */
	public void doWrite() {
		System.out.println("== 게시물 작성 ==");
		
		System.out.print("제목 : ");
		String title = Container.sc.nextLine();
		
		System.out.print("내용 : ");
		String body = Container.sc.nextLine();

		int id = articleService.doWrite(title, body);

		System.out.println(id + "번 글이 생성되었습니다");
	}

	/** 게시글 목록 출력 */
	public void showList() {

		List<Article> articles = articleService.getArticles();

		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		System.out.println("== 게시물 목록 ==");
		System.out.println(" 번호  /  제목  ");

		for (Article article : articles) {
			System.out.printf("  %d    /    %s \n", article.id, article.title);
		}
	}

	/** 게시글 상세보기 */
	public void showDetail(String command) {
		String[] comDiv = command.split(" ");
		if(comDiv.length < 3) {
			System.out.println("게시글 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(comDiv[2]);

		Map<String, Object> articleMap = articleService.getArticle(id);

		if (articleMap.isEmpty()) {
			System.out.println(id + "번 글은 존재하지 않습니다");
			return;
		}

		Article article = new Article(articleMap);
		System.out.println("== 게시글 상세보기 ==");
		System.out.println("번호 : " + article.id);
		System.out.println("작성일 : " + Util.getNotDateTimeStr(article.regDate));
		System.out.println("수정일 : " + Util.getNotDateTimeStr(article.updateDate));
		System.out.println("제목 : " + article.title);
		System.out.println("내용 : " + article.body);
	}

	/** 게시글 수정 */
	public void doModify(String command) {
		String[] comDiv = command.split(" ");
		if(comDiv.length < 3) {
			System.out.println("게시글 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(comDiv[2]);

		int articlesCount = articleService.getArticlesCount(id);

		if (articlesCount == 0) {
			System.out.println(id + "번 글은 존재하지 않습니다");
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
		String[] comDiv = command.split(" ");
		if(comDiv.length < 3) {
			System.out.println("게시글 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(comDiv[2]);


		int articlesCount = articleService.getArticlesCount(id);

		if (articlesCount == 0) {
			System.out.println(id + "번 글은 존재하지 않습니다");
			return;
		}

		articleService.doDelete(id);

		System.out.println("== 게시물 삭제 ==");
		System.out.println(id + "번 게시물이 삭제되었습니다");
	}
}
