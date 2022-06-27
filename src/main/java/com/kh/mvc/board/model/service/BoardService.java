package com.kh.mvc.board.model.service;

import static com.kh.mvc.common.JdbcTemplate.*;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.mvc.board.model.dao.BoardDao;
import com.kh.mvc.board.model.dto.Board;
import com.kh.mvc.member.model.dto.Member;

/**
 * DML
 * - Connection 생성
 * - Dao 요청
 * - 트랜잭션 처리
 * - Connection 반환
 * 
 * 
 * DQL
 * - Connection 생성
 * - Dao 요청
 * - Connection 반환
 * 
 * 
 * @author Han
 *
 */
public class BoardService {

	private BoardDao boardDao = new BoardDao();
	
	public List<Board> findAll(Map<String, Object> param) {
		Connection conn = getConnection();
		List<Board> list = boardDao.findAll(conn, param);
		close(conn);
		return list;
	}
	
	//DQL
	public int getTotalContent() {	
		Connection conn = getConnection();
		int totalContent = boardDao.getTotalContent(conn);
		close(conn);
		return totalContent;
	}
	
	

}
