package Lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DatabaseUtill;

public class LectureDAO {
	public List<LectureDTO> getTop6Classes() {
        List<LectureDTO> top6Classes = new ArrayList<>();
        String sql = "SELECT classID, classImg, classTitle, classProfessor, classPersonnel FROM class " +
                     "WHERE classPersonnel IS NOT NULL ORDER BY classPersonnel DESC LIMIT 6";
        Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try {
        	conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                LectureDTO classInfo = new LectureDTO(
                    rs.getInt("classID"),
                    rs.getString("classImg"),
                    rs.getString("classTitle"),
                    rs.getString("classProfessor"),
                    rs.getInt("classPersonnel")
                );
                top6Classes.add(classInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
		}

        return top6Classes;
    }
	public LectureDTO getClassInfo(int classID) {
	    LectureDTO myClassInfo = null;
	    String sql = "SELECT * FROM class WHERE classID = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, classID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            myClassInfo = new LectureDTO(
	                rs.getInt("classID"),
	                rs.getString("classThema"),
	                rs.getString("classTitle"),
	                rs.getString("classProfessor"),
	                rs.getString("classContent"),
	                rs.getString("classImg"),
	                rs.getInt("classVideoNum"),
	                rs.getInt("classPersonnel")
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
	        try { if (pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
	        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
	    }

	    return myClassInfo;
	}
	
	public List<LectureDTO> getClassInfos(int classID) {
        List<LectureDTO> myClassInfo = new ArrayList<>();
        String sql = "SELECT * FROM class WHERE classID = ?";
        Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try {
        	conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, classID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	LectureDTO classInfo = new LectureDTO(
            		rs.getInt("classID"),
            		rs.getString("classThema"),
                    rs.getString("classTitle"),
                    rs.getString("classProfessor"),
                    rs.getString("classContent"),
                    rs.getString("classImg"),
                    rs.getInt("classVideoNum"),
                    rs.getInt("classPersonnel")
                );
                myClassInfo.add(classInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
		}

        return myClassInfo;
    }
	
	public List<LectureDTO> searchClassesByKeyword(String keyword) {
        List<LectureDTO> classes = new ArrayList<>();
        String sql = "SELECT * FROM class WHERE classTitle LIKE ? OR ClassThema LIKE ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	conn = DatabaseUtill.getConnection();
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, "%" + keyword + "%");
        	pstmt.setString(2, "%" + keyword + "%");

            // SQL 실행
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	LectureDTO classInfo = new LectureDTO(
            		rs.getInt("classID"),
                    rs.getString("classImg"),
                    rs.getString("classTitle"),
                    rs.getString("classThema"),
                    rs.getString("classProfessor")
                );
                classes.add(classInfo);
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        } finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
		}

        return classes;
    }
}
