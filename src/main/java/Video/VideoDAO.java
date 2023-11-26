package Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DatabaseUtill;

public class VideoDAO {
	public int insertData(VideoDTO dto) {
        String sql = "INSERT INTO video (lectureID ,contentID, videoLength, video, realVideo) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
		PreparedStatement pstmt = null;
        try {
        	conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getLectureID());
        	pstmt.setInt(2, dto.getContetentID());
        	pstmt.setInt(3, dto.getVideoLenth());
        	pstmt.setString(4, dto.getVideo());
        	pstmt.setString(5, dto.getRealVideo());
        	return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
		}
        return -1;
    }
	public List<VideoDTO> getVideos(int lectureID) {
        List<VideoDTO> videos = new ArrayList<>();
        String sql = "SELECT * FROM video WHERE lectureID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lectureID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                VideoDTO videoInfo = new VideoDTO(
                		rs.getInt("videoID"),
                        rs.getInt("contentID"),
                        rs.getInt("lectureID"),
                        rs.getInt("videoLength"),
                        rs.getString("video"),
                        rs.getString("realVideo")
                );
                videos.add(videoInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
        }
        return videos;
    }
	public String getRealVideo(int videoID) {
        String realVideo = null;
        String sql = "SELECT realVideo FROM video WHERE videoID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, videoID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
            	realVideo = rs.getString("realVideo");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
        }
        return realVideo;
    }
	
	public List<String> getRealVideos(int contentID) {
	    List<String> videos = new ArrayList<>();
	    String sql = "SELECT realVideo FROM video WHERE contentID = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, contentID);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            String video = rs.getString("realVideo");
	            videos.add(video);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } finally {
	    	try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
	    }
	    return videos;
	}

	
	public String getVideo(int videoID) {
        String video = null;
        String sql = "SELECT video FROM video WHERE videoID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, videoID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
            	video = rs.getString("video");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
        }
        return video;
    }
	public int deleteVideo(int contentID) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = DatabaseUtill.getConnection();
	        conn.setAutoCommit(false);  // 트랜잭션 시작

	        String sql = "DELETE FROM video WHERE contentID = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, contentID);

	        int result = pstmt.executeUpdate();

	        if (result > 0) {
	            conn.commit();  // 트랜잭션 커밋
	        } else {
	            conn.rollback();  // 트랜잭션 롤백
	        }

	        return result;
	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            if (conn != null) conn.rollback();  // 예외 발생 시 롤백
	        } catch (Exception rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return -1;
	}
}
