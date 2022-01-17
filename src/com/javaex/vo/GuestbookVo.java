package com.javaex.vo;

public class GuestbookVo {

	private int no;
	private String name;
	private String pw;
	private String content;
	private String regDate; // 오라글의 date는 보통 string으로 처리

	public GuestbookVo() {

	}

	public GuestbookVo(String name, String pw, String content) {

		this.name = name;
		this.pw = pw;
		this.content = content;
	}

	public GuestbookVo(int no, String name, String pw, String content, String regDate) {
		this.no = no;
		this.name = name;
		this.pw = pw;
		this.content = content;
		this.regDate = regDate;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "GuestbookVo [no=" + no + ", name=" + name + ", pw=" + pw + ", content=" + content + ", regDate="
				+ regDate + "]";
	}

}
