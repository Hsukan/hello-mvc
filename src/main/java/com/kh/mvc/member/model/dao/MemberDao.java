package com.kh.mvc.member.model.dao;

import static com.kh.mvc.common.JdbcTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.mvc.member.model.dto.Gender;
import com.kh.mvc.member.model.dto.Member;
import com.kh.mvc.member.model.dto.MemberRole;
import com.kh.mvc.member.model.exception.MemberException;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		String filename = MemberDao.class.getResource("/sql/member/member-query.properties").getPath();
		System.out.println("filename@MemberDao = " + filename);
		try {
			prop.load(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * DQL요청 - dao
	 * 1. PreparedStatement객체 생성(sql전달) & 값 대입
	 * 2. 쿼리실행 executeQuery:ResultSet 반환
	 * 3. ResultSet 처리 - dto객체 변환
	 * 4. ResultSet, PreparedStatement객체 반환(close)
	 * 
	 * @param conn
	 * @param memberId
	 * @return
	 */
	public Member findById(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null;
		String sql = prop.getProperty("findById");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rset = pstmt.executeQuery(); //0행이 반환되어도 null은 아니다
			
			while(rset.next()) {
				member = handleMemberResultset(rset);
			}
			
			
		} catch (SQLException e) {
			throw new MemberException("회원 아이디 조회 오류!", e);
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return member;
	}

	private Member handleMemberResultset(ResultSet rset) throws SQLException {
		String memberId = rset.getString("member_id");
		String password = rset.getString("password");
		String memberName = rset.getString("member_name");
		MemberRole memberRole = MemberRole.valueOf(rset.getString("member_role"));
		
		// Enum에 null이 전달되지 않게
		String _gender = rset.getString("gender");
		Gender gender = _gender != null ? Gender.valueOf(_gender) : null;
		
		Date birthday = rset.getDate("birthday");
		String email = rset.getString("email");
		String phone = rset.getString("phone");
		String hobby = rset.getString("hobby");
		int point = rset.getInt("point");
		Timestamp enrollDate = rset.getTimestamp("enroll_date");
		return new Member(memberId, password, memberName, memberRole, gender, 
				birthday, email, phone, hobby, point, enrollDate);
	}

	/**
	 * 
	 * DML요청 - dao
	 * 1. PreparedStatement객체 생성(sql전달) & 값 대입
	 * 2. 쿼리실행 executeUpdate:int 반환 (insert는 1)
	 * 3. PreparedStatement객체 반환(close)
	 * 
	 */
	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result= 0;
		String sql = prop.getProperty("insertMember");
		//insert into member values (?, ?, ?, default, ?, ?, ?, ?, ?, default, default)
		try {
			//1.
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender() != null ? member.getGender().name() : null); //Gender.M
			pstmt.setDate(5, member.getBirthday());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getHobby());
			
			
			//2.
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			//service에 예외 던짐(unchecked, 비지니스를 설명가능한 구체적 커스텀 예외로 전환해서 던짐)
			throw new MemberException("회원가입 오류", e);
		}finally {
			//3.
			close(pstmt);
		}
		return result;
	}

	public int updateMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateMember");
		//updateMember = update member set member_name = ?, birthday = ?, email = ?, 
		//phone = ?, gender = ?, hobby = ? where member_id = ?
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberName());
			pstmt.setDate(2, member.getBirthday());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getGender() != null ? member.getGender().name() : null);
			pstmt.setString(6, member.getHobby());
			pstmt.setString(7, member.getMemberId());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("정보 수정 오류!", e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updatePassword(Connection conn, String memberId, String newPassword) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updatePassowrd");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newPassword);
			pstmt.setString(2, memberId);
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new MemberException("비밀번호 변경 오류!", e);
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	//조회된 행이 없어도 비어있는 리스트가 넘어간다.
	//그래서 기본값으로 null을 일단 넣어주고 선언해준다.
	public List<Member> findAll(Connection conn, Map<String, Object> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> list = new ArrayList<>();
		String sql = prop.getProperty("findAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) param.get("start"));
			pstmt.setInt(2, (int) param.get("end"));
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Member member = handleMemberResultset(rset); //member객체 반환
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int deleteMember(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MemberException("회원 정보 삭제 오류!", e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int getTotalContent(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContent = 0;
		String sql = prop.getProperty("getTotalContent");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) { //1행짜리 결과일때는 if사용 가능
				totalContent = rset.getInt(1); // 컬럼 인덱스 사용 -> db에서 인덱스는 1부터
			}
			
		} catch (SQLException e) {
			throw new MemberException("전체회원수 조회 오류!", e);
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContent;
	}

	public List<Member> findMemberLike(Connection conn, Map<String, Object> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> list = new ArrayList<>();
		String sql = prop.getProperty("findMemberLike");
		// select * from member where #(컬럼명) like ?
		
		String col = (String) param.get("searchType");
		String val = (String) param.get("searchKeyword");
		int start = (int) param.get("start");
		int end = (int) param.get("end");
		sql = sql.replace("#", col);
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + val + "%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				list.add(handleMemberResultset(rset));
			}
			
		} catch (SQLException e) {
			throw new MemberException("관리자 회원 검색 오류!", e);
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return list;
	}

	public int getTotalContentLike(Connection conn, Map<String, Object> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContent = 0;
		String sql = prop.getProperty("getTotalContentLike");
		String col = (String) param.get("searchType");
		String val = (String) param.get("searchKeyword");
		sql = sql.replace("#", col);
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + val + "%");
			rset = pstmt.executeQuery();
			if(rset.next()) {
				totalContent = rset.getInt(1); //ResultSet에서 1열의 값을 가져와주세요
			}
			
		} catch (SQLException e) {
			throw new MemberException("관리자 검색된 회원수 조회 오류!", e);
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		
		return totalContent;
	}

	public int updateMemberRole(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateMemberRole");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberRole().name()); //ENUM이라 마지막에 name을 붙여주면된다.
			pstmt.setString(2, member.getMemberId());
			result = pstmt.executeUpdate();
		}
		catch(Exception e) {
			throw new MemberException("회원 권한 수정 오류!", e);
		}
		finally {
			close(pstmt);
		}
		
		return result;
	}
}
