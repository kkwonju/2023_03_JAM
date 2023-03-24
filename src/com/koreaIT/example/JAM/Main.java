package com.koreaIT.example.JAM;

import com.koreaIT.example.JAM.exception.SQLErrorException;

public class Main {
	public static void main(String[] args) {
		try {
			new App().start();
		} catch (SQLErrorException e) { // error 발생 후 f6 -> 확인 가능
			System.out.println(e.getMessage()); // 문제 발생한 쿼리 메세지만 출력 :: out (일반) , err (에러메세지)
			e.getOrigin().printStackTrace(); // 왜 안되는지
		}
	}
}