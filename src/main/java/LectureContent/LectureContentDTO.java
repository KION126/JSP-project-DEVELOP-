package LectureContent;

public class LectureContentDTO {
	int contentID;
	int lectureID;
	int week;
	String title;
	String file;
	String realFile;
	String startDate;
	String endDate;
	
	public LectureContentDTO(int contentID, int lectureID, int week, String title, String file, String realFile, String startDate, String endDate) {
		super();
		this.contentID = contentID;
		this.lectureID = lectureID;
		this.week = week;
		this.title = title;
		this.file = file;
		this.realFile = realFile;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public LectureContentDTO(int lectureID, int week, String title, String file, String realFile, String startDate, String endDate) {
		super();
		this.lectureID = lectureID;
		this.week = week;
		this.title = title;
		this.file = file;
		this.realFile = realFile;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public LectureContentDTO(int week, String title, String file, String realFile, String startDate, String endDate) {
		super();
		this.week = week;
		this.title = title;
		this.file = file;
		this.realFile = realFile;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getRealFile() {
		return realFile;
	}
	public void setRealFile(String realFile) {
		this.realFile = realFile;
	}
	
	
}
