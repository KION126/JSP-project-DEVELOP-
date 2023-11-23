function commentModifyAction(classID, boardID, commentID, commentContent) {
	
	var commentContent = $("#commentContent").val();
	$.ajax({
	    type: 'post',
		url: 'commentModifyAction.do',
	    data: {
	        classID: classID,
	        boardID: boardID,
	        commentID: commentID,
	        commentContent: commentContent
	    },
	    dataType: 'html',  // 가져온 데이터의 타입 (HTML로 설정)
	    success: function (data) {
	        // 성공 시, commentSection 부분을 업데이트
	        $('#commentSection').html(data);
	    },
	    error: function (xhr, status, error) {
	        // 에러 처리 부분
	        console.error(xhr.responseText);
	    }
	});
}