package com.koreaIT.example.JAM.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.container.Container;
import com.koreaIT.example.JAM.dto.Article;
import com.koreaIT.example.JAM.dto.Member;
import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class MemberDao {

	public boolean isLoginIdDup(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowBooleanValue(Container.conn, sql);
	}

	public int doJoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO member");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);

		return DBUtil.insert(Container.conn, sql);
	}

	public Member getMemberByLoginId(String loginId) {

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);
		if (memberMap.isEmpty()) {
			return null;
		}

		return new Member(memberMap);
	}

	public static List<Member> getMembers() {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("ORDER BY id DESC;");

		List<Member> members = new ArrayList<>();
		List<Map<String, Object>> memberListMap = DBUtil.selectRows(Container.conn, sql);

		for (Map<String, Object> memberMap : memberListMap) {
			members.add(new Member(memberMap));
		}
		return members;
	}
}
