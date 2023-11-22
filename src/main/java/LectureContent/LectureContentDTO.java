package LectureContent;

public class LectureContentDTO {
	int contentID;
	int lectureID;
	int week;
	String title;
	String date;
	String video;
	String file;
	String realFile;
	
	public LectureContentDTO(int week, String title, String date, String video, String file, String realFile) {
		super();
		this.week = week;
		this.title = title;
		this.date = date;
		this.video = video;
		this.file = file;
		this.realFile = realFile;
	}
	public int getContentID() {
		return contentID;
	}
	public void setContentID(int contentID) {
		this.contentID = contentID;
	}
	public int getLectureID() {
		return lectureID;
	}
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getRealFile() {
		return realFile;
	}
	public void setRealFile(String realFile) {
		this.realFile = realFile;
	}
	
	
}
