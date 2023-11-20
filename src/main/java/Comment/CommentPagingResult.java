// CommentPagingResult.java

package Comment;

import java.util.List;

public class CommentPagingResult {
    private List<CommentDTO> commentInfoList;
    private int totalPages;

    public CommentPagingResult(List<CommentDTO> commentInfoList, int totalPages) {
        this.commentInfoList = commentInfoList;
        this.totalPages = totalPages;
    }

    public List<CommentDTO> getCommentInfoList() {
        return commentInfoList;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
