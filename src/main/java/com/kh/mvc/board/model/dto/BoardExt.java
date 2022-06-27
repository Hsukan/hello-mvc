package com.kh.mvc.board.model.dto;

import java.sql.Timestamp;

public class BoardExt extends Board {
	
	private int attachCount;

	public BoardExt() {
		super();
		// TODO 
	}

	public BoardExt(int no, String title, String writer, String content, int readCount, Timestamp regDate,
			int attachCount) {
		super(no, title, writer, content, readCount, regDate);
		this.attachCount = attachCount;
	}

	public int getAttachCount() {
		return attachCount;
	}

	public void setAttachCount(int attachCount) {
		this.attachCount = attachCount;
	}

	@Override
	public String toString() {
		return "BoardExt [attachCount=" + attachCount + ", toString()=" + super.toString() + "]";
	}
	
	
}
