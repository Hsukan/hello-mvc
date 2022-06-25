package com.kh.mvc.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class HelloMvcUtils {
	
	/**
	 * 1. 암호화
	 * 2. 인코딩처리
	 * 
	 * @param rawPassword
	 * @return
	 */
	public static String getEncryptedPassword(String rawPassword, String salt) {
		String encryptedPassword = null;
		
		try {
			// 1. 암호화
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] input = rawPassword.getBytes("utf-8"); //멀티캐치
			byte[] saltBytes = salt.getBytes("utf-8");
			md.update(saltBytes); // salt 전달 
			byte[] encryptedBytes = md.digest(input); //암호화
			System.out.println(new String(encryptedBytes));

			//2. 인코딩
			Encoder encoder = Base64.getEncoder();
			encryptedPassword = encoder.encodeToString(encryptedBytes);
			
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		return encryptedPassword;
	}
	
	/**
	 * 
	 * 
	 * @param cPage
	 * @param numPerPage
	 * @param totalContent
	 * @param url
	 * @return
	 * 
	 * totalPage 전체페이지 수
	 * pagebarSize 한 페이지에 표시할 페이지 번호 갯수
	 * pagebarStart ~ pagebarEnd
	 * pageNo 증감변수
	 * 
	 * 1. 이전영역
	 * 2. pageNo영역
	 * 3. 다음영역
	 * 
	 */
	public static String getPagebar(int cPage, int numPerPage, int totalContent, String url) {
		StringBuilder pagebar = new StringBuilder();
		url += (url.indexOf("?") < 0) ? "?cPage=" : "&cPage=";
		int totalPage = (int) Math.ceil((double) totalContent / numPerPage);
		int pagebarSize = 5;
		int pagebarStart = ((cPage - 1) / pagebarSize * pagebarSize) + 1;
		int pagebarEnd = pagebarStart + pagebarSize - 1;
		int pageNo = pagebarStart; // 초기값 -> max:pagebarEnd
		
		
		//이전영역
		if(pageNo == 1) {
			
		}
		else {
			pagebar.append("<a href='"+ url + (pageNo - 1) +"'>이전</a>\n");
			
		}
		
		//pageNo
		while(pageNo <= pagebarEnd && pageNo <= totalPage) {
			//현재페이지
			if(pageNo == cPage) {
				pagebar.append("<span class='cPage'>" + pageNo + "</span>\n");
			}
			//현재페이지가 아닌 경우
			else {
				pagebar.append("<a href='" + url + pageNo + "'>" + pageNo + "</a>\n");
				
			}
			
			pageNo++;
		}
		
		//다음영역
		if(pageNo > totalPage) {
			
		}
		else {
			//while문을 탈출할때 +1 상태로 나오기 때문에 그대로 쓸 수 있다.
			pagebar.append("<a href='" + url + pageNo + "'>다음</a>\n");
		}
		
		return pagebar.toString();
	}
	
}
