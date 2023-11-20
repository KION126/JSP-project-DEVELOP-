function loadComments(userID, classID, boardID, currentPage) {
    // Ajax로 댓글을 가져오는 부분
    $.ajax({
        type: 'post',
		url: 'commentReflash.do',
        data: {
            userID: userID,
            classID: classID,
            boardID: boardID,
            currentPage: currentPage
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

// 페이지 전환 시 댓글을 다시 가져오도록 처리
function comment(userID, classID, boardID, currentPage) {
    loadComments(userID, classID, boardID, currentPage);
}