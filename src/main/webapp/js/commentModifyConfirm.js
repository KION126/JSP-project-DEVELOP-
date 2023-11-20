function commentModifyConfirm(userID, classID, boardID, currentPage, commentID, commentContent) {
		$.ajax({
        type: 'post',
		url: 'commentModifyConfirm.do',
        data: {
            userID: userID,
            classID: classID,
            boardID: boardID,
            currentPage: currentPage,
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