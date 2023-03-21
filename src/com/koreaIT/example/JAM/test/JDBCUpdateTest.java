package com.koreaIT.example.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreaIT.example.JAM.Article;

public class JDBCUpdateTest {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// try catch (finally) 예외처리
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");

			int id = 1;
			String title = "제목수정됌";
			String body = "내용됌";
			
			String sql = "UPDATE article";
			sql += " SET title = '" + title + "', ";
			sql += "`body` = '" + body + "'";
			sql += " WHERE id = " + id + ";";

			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);
			
			int affectedRow = pstmt.executeUpdate();
			System.out.println("affectedRow : " + affectedRow);
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러 : " + e);

		} finally {
			// 끄는 순서는 반대로
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
