package LectureContent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Lecture.LectureDAO;
import Lecture.LectureDTO;
import Lecture.LectureService;
import Video.VideoDAO;
import Video.VideoDTO;

public class LectureContentService implements CommandHandler {
	String viewPage = null;

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 강의목록관리 페이지 이동
		
		String userID = null;
		int classID = 0;
        
        HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
    	if(request.getParameter("classID") != null) {
    		classID = Integer.parseInt(request.getParameter("classID"));
    	}
    	
    	// lecture 정보 가져오기
        LectureDAO class_dao = new LectureDAO();
		List<LectureDTO> classInfos = class_dao.getClassInfos(classID);
		String lec_title = null;
		String lec_pro = null;
		for (LectureDTO classInfo : classInfos) {
			lec_title = classInfo.getClassTitle();
			lec_pro = classInfo.getClassProfessor();
		}
		// notice정보 가져오기
		BoardDAO notice_dao = new BoardDAO();
		List<BoardDTO> noticeInfos = notice_dao.getLists(1, classID,0,3);
    	
		request.setAttribute("noticeInfos", noticeInfos);
    	request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
    	request.setAttribute("classID", classID);
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
			case "lectureContentWrite.do": {
				viewPage = lectureContentWrite(request, userID);
				break;
			} case "lectureContentWriteAction.do": {
				viewPage = lectureContentWriteAction(request, userID);
				break;
			} case "lectureContentModify.do": {
				viewPage = lectureContentModify(request, userID);
				break;
			} case "lectureContentDelete.do": {
				viewPage = lectureContentDelete(request, userID, classID);
				break;
			} 
		}
		
		return viewPage;
    }
    
    private String lectureContentWrite(HttpServletRequest request, String userID) {
    	
        return "lectureContentWrtie";
    }
    
    private String lectureContentWriteAction(HttpServletRequest request, String userID) throws IOException {
    	
    	String contentTitle = null;
    	int contentWeek = 0;
    	String startDate = null;
    	String endDate = null;
    	String contentFile = null;
    	String contentRealFile = null;
    	int classID = 0;
    	
    	String savepath = request.getRealPath("/upload").replace("\\\\", "/");
    	String videoSavepath = "C:/Users/kg200/Desktop/JSP/JSP_DEVELOP/src/main/webapp/video/";
    	// 강의 영상같은 경우는 웹서버를 사용하지 않기 때문에
    	//video로 불러와야하기 때문에 어쩔수 없이 리소스파일쪽에 절대경로로 설정해서 저장해야합니다..
    	
    	File uploadDir = new File(savepath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 디렉토리가 없으면 만듭니다
        }
        
        MultipartRequest multi = null;
        int videoMaxSize = 1024 * 1024 * 1024; // 1GB

        try {
            multi = new MultipartRequest(request, savepath, videoMaxSize, "UTF-8", new DefaultFileRenamePolicy());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
    	
        File file = multi.getFile("contentFile");
        
        if (file != null) {
        	contentFile = multi.getOriginalFileName("contentFile");
        	contentRealFile = file.getName();
        }
        if(multi.getParameter("userID") != null || multi.getParameter("userID") != "") {
        	userID = multi.getParameter("userID");
        }
        String classIDParameter = multi.getParameter("classID");
        if(classIDParameter != null && !classIDParameter.isEmpty()) {
            classID = Integer.parseInt(classIDParameter);
        }
        if(multi.getParameter("contentTitle") != null || multi.getParameter("contentTitle") != "") {
        	contentTitle = multi.getParameter("contentTitle");
        }
        if(multi.getParameter("contentWeek") != null || multi.getParameter("contentWeek") != "") {
        	contentWeek = Integer.parseInt(multi.getParameter("contentWeek"));
        }
        if(multi.getParameter("startDate") != null || multi.getParameter("startDate") != "") {
        	startDate = multi.getParameter("startDate");
        }
        if(multi.getParameter("endDate") != null || multi.getParameter("endDate") != "") {
        	endDate = multi.getParameter("endDate");
        }
		
		LectureContentDAO lecCon_dao = new LectureContentDAO();
		LectureContentDTO lecCon = new LectureContentDTO(classID, contentWeek, contentTitle, contentFile, contentRealFile, startDate, endDate);
		int contentID = lecCon_dao.insertData(lecCon);
 		
 		VideoDAO video_dao = new VideoDAO();
		String[] videoParameters = {"video01", "video02", "video03"};
		for (String videoParameter : videoParameters) {
		    File videoFile = multi.getFile(videoParameter);

		    if (videoFile != null) {
		    	// 파일 정보 추출
		        String originalFileName = multi.getOriginalFileName(videoParameter);

		        // UUID를 사용하여 무작위의 영어 문자열 생성
		        String randomFileName = UUID.randomUUID().toString();

		        // 확장자 추출
		        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

		        // 새로운 파일 이름 생성 (UUID + 확장자)
		        String realFileName = randomFileName + fileExtension;


		        // VideoDTO 객체 생성
		        VideoDTO videoDTO = new VideoDTO(classID, contentID, 0, originalFileName, realFileName);

		        // video 파일 저장
		        File destFile = new File(videoSavepath, realFileName);
		        videoFile.renameTo(destFile);
		        
		        video_dao.insertData(videoDTO);
		    }
		}
		
    	List<LectureContentDTO> lecConInfoList = lecCon_dao.getLists(classID);
    	List<VideoDTO> videoInfoList = video_dao.getVideos(classID);
    	
    	// lecture 정보 가져오기
        LectureDAO class_dao = new LectureDAO();
		List<LectureDTO> classInfos = class_dao.getClassInfos(classID);
		String lec_title = null;
		String lec_pro = null;
		for (LectureDTO classInfo : classInfos) {
			lec_title = classInfo.getClassTitle();
			lec_pro = classInfo.getClassProfessor();
		}
		// notice정보 가져오기
		BoardDAO notice_dao = new BoardDAO();
		List<BoardDTO> noticeInfos = notice_dao.getLists(1, classID,0,3);
		
		request.setAttribute("lecConInfoList", lecConInfoList);
		request.setAttribute("videoInfoList", videoInfoList);
		request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
		request.setAttribute("classID", classID);
		request.setAttribute("noticeInfos", noticeInfos);

		return "lectureRoom";
    }
    private String lectureContentDelete(HttpServletRequest request, String userID, int classID) throws IOException {
    	int contentID = 0;
    	
    	if(request.getParameter("contentID") != null) {
    		contentID = Integer.parseInt(request.getParameter("contentID"));
    	}
		
		VideoDAO video_dao = new VideoDAO();
		LectureContentDAO lecCon_dao = new LectureContentDAO();
		String savepath = "C:/Users/kg200/Desktop/JSP/JSP_DEVELOP/src/main/webapp/video/";
        List<String> prev = video_dao.getRealVideos(contentID);
        for(String realVideo : prev) {
        	File prevFile = new File(savepath + realVideo);
        	if(prevFile.exists()) {
        		prevFile.delete();
        	}           
        }
        lecCon_dao.deleteContent(contentID);
		video_dao.deleteVideo(contentID);
		
    	List<LectureContentDTO> lecConInfoList = lecCon_dao.getLists(classID);
    	List<VideoDTO> videoInfoList = video_dao.getVideos(classID);
    	
    	// notice정보 가져오기
		BoardDAO notice_dao = new BoardDAO();
		List<BoardDTO> noticeInfos = notice_dao.getLists(1, classID,0,3);
    			
		request.setAttribute("lecConInfoList", lecConInfoList);
		request.setAttribute("videoInfoList", videoInfoList);
		request.setAttribute("classID", classID);
		request.setAttribute("noticeInfos", noticeInfos);
		
    	return "lectureRoom";
    }
    
    private String lectureContentModify(HttpServletRequest request, String userID) throws IOException {
    	
    	return "lectureRoom";
    }
}