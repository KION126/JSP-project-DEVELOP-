package Video;

public class VideoDTO {
	int videoID;
	int contentID;
	int lectureID;
	int videoLenth;
	String video;
	String realVideo;
	
	public VideoDTO(int videoID, int contetentID, int lectureID, int videoLength, String video, String realVideo) {
		super();
		this.videoID = videoID;
		this.contentID = contetentID;
		this.lectureID = lectureID;
		this.videoLenth = videoLength;
		this.video = video;
		this.realVideo = realVideo;
	}
	
	public VideoDTO(int lectureID, int contetentID, int videoLength, String video, String realVideo) {
		super();
		this.contentID = contetentID;
		this.lectureID = lectureID;
		this.videoLenth = videoLength;
		this.video = video;
		this.realVideo = realVideo;
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

	public int getVideoID() {
		return videoID;
	}
	public void setVideoID(int videoID) {
		this.videoID = videoID;
	}
	public int getContetentID() {
		return contentID;
	}
	public void setContetentID(int contetentID) {
		this.contentID = contetentID;
	}
	public int getVideoLenth() {
		return videoLenth;
	}
	public void setVideoLenth(int videoLenth) {
		this.videoLenth = videoLenth;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getRealVideo() {
		return realVideo;
	}
	public void setRealVideo(String realVideo) {
		this.realVideo = realVideo;
	}
}
