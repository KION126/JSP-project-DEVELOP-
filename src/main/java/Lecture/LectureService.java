package Lecture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Enrol.EnrolDAO;
import LectureContent.LectureContentDAO;
import LectureContent.LectureContentDTO;
import User.UserDAO;
import Video.VideoDAO;
import Video.VideoDTO;

public class LectureService implements CommandHandler {
	String viewPage = null;
	
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 강의정보, 강의실 페이지 이동
		// 수강신청 수행
		
		String userID = null;
        int classID = 0;
        
        HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
        
        if(Integer.parseInt(request.getParameter("classID")) > 0) {
        	classID = Integer.parseInt(request.getParameter("classID"));
        }
		
		String requestPage = request.getRequestURI().replaceAll("/JSP_DEVELOP/","");
		
		switch (requestPage) {
			case "lectureInfo.do": {
				viewPage = lectureInfo(request, userID, classID);
				break;
			} case "lectureEnrol.do": {
				viewPage = lectureEnrol(request, userID, classID);
				break;
			} case "lectureRoom.do": {
				viewPage = lectureRoom(request, userID, classID);
				break;
			} case "lectureContentFileDownload.do": {
				viewPage = lectureContentFileDownload(request, response, userID, classID);
				break;
			} case "videoPlayer.do": {
				viewPage = videoPlayer(request, response, userID, classID);
				break;
			}
		}
		
		return viewPage;
    }
    
    private String lectureInfo(HttpServletRequest request, String userID, int classID) {
    	// 해당 강좌의 정보 가져오기
        LectureDAO class_dao = new LectureDAO();
        LectureDTO classInfos = class_dao.getClassInfo(classID);

        // 강좌 정보 전달
        request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        request.setAttribute("img", classInfos.getClassImg());
        request.setAttribute("title", classInfos.getClassTitle());
        request.setAttribute("thema", classInfos.getClassThema());
        request.setAttribute("pro", classInfos.getClassProfessor());
        request.setAttribute("con", classInfos.getClassContent());
        
        // 해당 강좌를 수강중인지 확인여부 전달
        EnrolDAO enrol_dao = new EnrolDAO();
		boolean classEnrolChecked = enrol_dao.classEnrolCheck(userID, classID);
		request.setAttribute("classEnrolChecked", classEnrolChecked);

		// 이메일 인증을 한 회원인지 확인여부 전달
		UserDAO user_dao = new UserDAO();
		boolean userEmailChecked = user_dao.getUserEmailChecked(userID);
		request.setAttribute("userEmailChecked", userEmailChecked);
		
    	return "lectureInfo";
    }
    
    private String lectureEnrol(HttpServletRequest request, String userID, int classID) {
    	boolean NewEnrol = true;
    	
    	// enrol테이블에 insert하여 수강신청
    	EnrolDAO enrol_dao = new EnrolDAO();
        enrol_dao.enrolClass(userID, classID);

        // 해당 강좌의 정보 가져오기
        LectureDAO class_dao = new LectureDAO();
        LectureDTO classInfos = class_dao.getClassInfo(classID);

        // 해당 강좌를 수강중인지 확인여부 전달
        boolean classEnrolChecked = enrol_dao.classEnrolCheck(userID, classID);

        // 이메일 인증을 한 회원인지 확인여부 전달
        UserDAO user_dao = new UserDAO();
        boolean userEmailChecked = user_dao.getUserEmailChecked(userID);
        
        request.setAttribute("NewEnrol", NewEnrol);
        
        request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        
        request.setAttribute("img", classInfos.getClassImg());
        request.setAttribute("title", classInfos.getClassTitle());
        request.setAttribute("thema", classInfos.getClassThema());
        request.setAttribute("pro", classInfos.getClassProfessor());
        request.setAttribute("con", classInfos.getClassContent());

        request.setAttribute("classEnrolChecked", classEnrolChecked);
        request.setAttribute("userEmailChecked", userEmailChecked);
    	
    	return "lectureInfo";
    }
    
    private String lectureRoom(HttpServletRequest request, String userID, int classID) {
    	//userType가져오기
        UserDAO DAO = new UserDAO();
		String userType = DAO.getUserType(userID);
        
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
		
		// QA정보 가져오기
		List<BoardDTO> QAInfos = notice_dao.getLists(2, classID,0,3);
		
		// 강의목록 가져오기
		LectureContentDAO lecCon_dao = new LectureContentDAO();
    	List<LectureContentDTO> lecConInfoList = lecCon_dao.getLists(classID);

    	// 강의영상 가져오기
    	VideoDAO video_dao = new VideoDAO();
    	List<VideoDTO> videoInfoList = video_dao.getVideos(classID);
    	
    	request.setAttribute("lecConInfoList", lecConInfoList);
		request.setAttribute("videoInfoList", videoInfoList);
		
        request.setAttribute("userID", userID); 
        request.setAttribute("classID", classID);
        request.setAttribute("userType", userType);
        request.setAttribute("lectuerType", "lecture"+Integer.toString(classID));
        
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        request.setAttribute("noticeInfos", noticeInfos);
        request.setAttribute("QAInfos", QAInfos);
    	
    	return "lectureRoom";
    }
    private String lectureContentFileDownload(HttpServletRequest request, HttpServletResponse response, String userID, int classID) {
    	
    	int contentID = 0;
    	if(request.getParameter("contentID") != null) {
    		contentID = Integer.parseInt(request.getParameter("contentID"));
    	}
    	
    	LectureContentDAO lecCon_dao = new LectureContentDAO();
    	String fileName = lecCon_dao.getFile(contentID);
    	String realFile = lecCon_dao.getRealFile(contentID);
    	
    	String root = request.getSession().getServletContext().getRealPath("/");
        String savePath = root + "upload";
        
    	if(fileName.equals("") || realFile.equals("")) {
        	return null;
        }
    	
    	InputStream in = null;
        OutputStream os =null;
        File file = null;
        boolean skip = false;
        String client = "";
        try {
			try {
				file = new File(savePath, realFile);
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				skip = true;
			}
			client = request.getHeader("User-Agent");
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Date");
			if(!skip) {
				if(client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("KSC5601"), "ISO8859_1"));
				} else {
					fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
				}
				response.setHeader("Content-Length", "" + file.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int)file.length()];
				int leng = 0;
				while((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			} else {
				response.setContentType("text/html; charset=UTF-8");
				//파일을 찾을 수 없습니다.
			}
			in.close();
			os.close();
        } catch (Exception e) {
			e.printStackTrace();
		}
    	
    	lectureRoom(request,userID, classID);
    	return "lectureRoom";
    }
    
    private String videoPlayer(HttpServletRequest request, HttpServletResponse response, String userID, int classID) throws IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
    	
    	int videoID = 0;
    	int contentID = 0;
    	
    	if(request.getParameter("contentID") != null) {
    		contentID = Integer.parseInt(request.getParameter("contentID"));
    	}
    	if(request.getParameter("videoID") != null) {
    		videoID = Integer.parseInt(request.getParameter("videoID"));
    	}
    	
    	VideoDAO video_dao = new VideoDAO();
    	String realVideo = video_dao.getRealVideo(videoID);
    	
    	String savePath = "./video/";
        
    	String videoURL = savePath + realVideo;
    	
    	// lecture 정보 가져오기
        LectureDAO class_dao = new LectureDAO();
		List<LectureDTO> classInfos = class_dao.getClassInfos(classID);
		String lec_title = null;
		String lec_pro = null;
		for (LectureDTO classInfo : classInfos) {
			lec_title = classInfo.getClassTitle();
			lec_pro = classInfo.getClassProfessor();
		}
		
		LectureContentDAO lecCon_dao = new LectureContentDAO();
		String contentTitle = lecCon_dao.getContentTitle(contentID);
		
		// 강의영상 가져오기
    	String videoTitle = video_dao.getVideo(videoID);
    	
    	request.setAttribute("contentTitle", contentTitle);
		request.setAttribute("videoTitle", videoTitle);
		request.setAttribute("classID", classID);
    	request.setAttribute("videoURL", videoURL);
    	request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
    	
    	return "videoPlayer";
    }
}
