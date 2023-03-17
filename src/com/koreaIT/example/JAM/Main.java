package com.koreaIT.example.JAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		List<Article> articles = new ArrayList<>();

		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		int lastArticleId = 0;

		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}

			if (command.equals("article write")) {
				int id = lastArticleId + 1;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				articles.add(new Article(id, title, body));
				System.out.println(id + "번 글이 생성되었습니다");
				lastArticleId++;

			} else if (command.equals("article list")) {
				Article forPrintArticle = null;
				if (articles.size() > 0) {
					System.out.println("  번호  /  제목  ");
					for (int i = articles.size() - 1; i >= 0; i--) {
						forPrintArticle = articles.get(i);
						System.out.printf("  %d    /    %s \n", forPrintArticle.id, forPrintArticle.title);
					}
				}
				if(forPrintArticle == null) {
					System.out.println("게시글이 존재하지 않습니다");
					continue;
				}
			} else {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
		}
		System.out.println("== 프로그램 종료 ==");
		sc.close();
	}
}