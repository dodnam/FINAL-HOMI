package finalProject.homis.hobbyFarm.notice.model.vo;

import java.sql.Date;

public class Notice {
	private int post_no;
	private String post_fix;
	private String writer;
	private int board_no;
	private String title;
	private String content;
	private int view_count;
	private Date write_date;
	private Date modify_date;
	private String delete_yn;
	
	public Notice() {}

	public Notice(int post_no, String post_fix, String writer, int board_no, String title, String content,
			int view_count, Date write_date, Date modify_date, String delete_yn) {
		super();
		this.post_no = post_no;
		this.post_fix = post_fix;
		this.writer = writer;
		this.board_no = board_no;
		this.title = title;
		this.content = content;
		this.view_count = view_count;
		this.write_date = write_date;
		this.modify_date = modify_date;
		this.delete_yn = delete_yn;
	}

	public int getPost_no() {
		return post_no;
	}

	public void setPost_no(int post_no) {
		this.post_no = post_no;
	}

	public String getPost_fix() {
		return post_fix;
	}

	public void setPost_fix(String post_fix) {
		this.post_fix = post_fix;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	public String getDelete_yn() {
		return delete_yn;
	}

	public void setDelete_yn(String delete_yn) {
		this.delete_yn = delete_yn;
	}

	@Override
	public String toString() {
		return "Notice [post_no=" + post_no + ", post_fix=" + post_fix + ", writer=" + writer + ", board_no=" + board_no
				+ ", title=" + title + ", content=" + content + ", view_count=" + view_count + ", write_date="
				+ write_date + ", modify_date=" + modify_date + ", delete_yn=" + delete_yn + "]";
	}
	
	
	
}