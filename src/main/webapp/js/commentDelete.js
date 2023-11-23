function commentDelete(classID, boardID, commentID) {
    // confirm으로 확인 메시지 표시
    var result = confirm("정말로 삭제하시겠습니까?");

    // 확인을 눌렀을 때만 삭제 진행
    if (result) {
       // Ajax로 댓글을 가져오는 부분
	    $.ajax({
	        type: 'post',
			url: 'commentDelete.do',
	        data: {
	            classID: classID,
	            boardID: boardID,
	            commentID: commentID
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
}