package Lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DatabaseUtill;

public class ClassDAO {
	public List<ClassDTO> getTop6Classes() {
        List<ClassDTO> top6Classes = new ArrayList<>();
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
                ClassDTO classInfo = new ClassDTO(
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
	public ClassDTO getClassInfo(int classID) {
	    ClassDTO myClassInfo = null;
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
	            myClassInfo = new ClassDTO(
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
	
	public List<ClassDTO> getClassInfos(int classID) {
        List<ClassDTO> myClassInfo = new ArrayList<>();
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
            	ClassDTO classInfo = new ClassDTO(
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
	
	public List<ClassDTO> searchClassesByKeyword(String keyword) {
        List<ClassDTO> classes = new ArrayList<>();
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
            	ClassDTO classInfo = new ClassDTO(
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
