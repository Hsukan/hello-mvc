package com.kh.mvc.board.model.dao;
import static com.kh.mvc.common.JdbcTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.mvc.board.model.dto.Board;
import com.kh.mvc.board.model.dto.BoardExt;
import com.kh.mvc.board.model.exception.BoardException;

/**
 * 
 * Properties 쿼리관리객체 - /sql/board/board-query.properties load!
 * 
 * DML
 * 
 * DQL
 * - PreparedStatement객체 
 * - 미완성쿼리/값대입
 * - 실행
 * - ResultSet처리 (Board객체)
 * - PreparedStatement/ResultSet 반환
 *
 */
public class BoardDao {

	private Properties prop = new Properties();

	public BoardDao() {
		String filename = BoardDao.class.getResource("/sql/board/board-query.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Board> findAll(Connection conn, Map<String, Object> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> list = new ArrayList<>();
		String sql = prop.getProperty("findAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) param.get("start"));
			pstmt.setInt(2, (int) param.get("end"));
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				BoardExt board = handleBoardResultSet(rset);
				board.setAttachCount(rset.getInt("attach_count"));
				list.add(board);
			}
			
		} catch (SQLException e) {
			throw new BoardException("게시글 목록 조회 오류!", e);
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return list;
	}

	private BoardExt handleBoardResultSet(ResultSet rset) throws SQLException {
		int no = rset.getInt("no");
		String title = rset.getString("title");
		String writer = rset.getString("writer");
		String content = rset.getString("content");
		int readCount = rset.getInt("read_count");
		Timestamp regDate = rset.getTimestamp("reg_date");
		return new BoardExt(no, title, writer, content, readCount, regDate, 0);
	}

	public int getTotalContent(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContent = 0;
		String sql = prop.getProperty("getTotalContent");
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next())
				totalContent = rset.getInt(1);
		} catch (SQLException e) {
			throw new BoardException("총 게시물수 조회 오류!", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContent;
	}
	
	
	
	
}
