package LectureContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Board.BoardDAO;
import Board.BoardDTO;
import CommandHandler.CommandHandler;
import Lecture.LectureDAO;
import Lecture.LectureDTO;
import User.UserDAO;

public class LectureRoomBoardDownload implements CommandHandler {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
    	
    	String userID = null;
        int classID = 0;
        int boardID = 0;
        
        HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
        if(request.getParameter("classID") != null) {
        	classID = Integer.parseInt(request.getParameter("classID"));
        }
        if(request.getParameter("boardID") != null) {
        	boardID = Integer.parseInt(request.getParameter("boardID"));
        } 
        
        // userType가져오기
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
		
		// 해당 Notice 정보 가져오기
		BoardDAO notice_dao = new BoardDAO();
		List<BoardDTO> noticeInfoList = notice_dao.getDate(boardID);
		
		String not_Title = null;
		String not_userID = null;
		String not_userName = null;
		String not_Date = null;
		String not_Con = null;
		String not_File = null;
		
		for (BoardDTO noticeInfo : noticeInfoList) {
            not_Title = noticeInfo.getboardTitle();
			not_userID = noticeInfo.getUserID();
			not_userName = DAO.getUserName(not_userID);
			not_Date = noticeInfo.getboardDate();
			not_Con = noticeInfo.getboardContent();
			not_File = noticeInfo.getboardFile();
        }
		
		boolean userIDeqboardID = false;
		if(not_userID.equals(userID)) {
			userIDeqboardID = true;
		}
		
		String root = request.getSession().getServletContext().getRealPath("/");
        String savePath = root + "upload";
        String fileName = null;
        String realFile = null;
        
        fileName = notice_dao.getFile(boardID);
        realFile = notice_dao.getRealFile(boardID);

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
        
        
		request.setAttribute("userID", userID);
        request.setAttribute("classID", classID);
        request.setAttribute("userType", userType);
        request.setAttribute("userIDeqboardID", userIDeqboardID);
		
        request.setAttribute("lec_title", lec_title);
        request.setAttribute("lec_pro", lec_pro);
        
        request.setAttribute("boardID", boardID);
        request.setAttribute("not_Title", not_Title);
        request.setAttribute("not_userName", not_userName);
        request.setAttribute("not_Date", not_Date);
        request.setAttribute("not_Con", not_Con);
        request.setAttribute("not_File", not_File);
        
        return "lectureRoomNoticeInfo";
    }
}
