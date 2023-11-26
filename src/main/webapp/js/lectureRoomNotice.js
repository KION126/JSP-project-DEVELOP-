function lectureRoomNotice(classID, currentPage, boardType) {
		// 동적으로 form 생성
	    var form = document.createElement('form');
	    form.method = 'post';
	    form.action = 'lectureRoomNotice.do';

	    // classID를 전달하는 hidden input 추가
	    var classIDInput = document.createElement('input');
	    classIDInput.type = 'hidden';
	    classIDInput.name = 'classID';
	    classIDInput.value = classID;
	    form.appendChild(classIDInput);
	    
		// currentPage를 전달하는 hidden input 추가
	    var currentPageInput = document.createElement('input');
	    currentPageInput.type = 'hidden';
	    currentPageInput.name = 'currentPage';
	    currentPageInput.value = currentPage;
	    form.appendChild(currentPageInput);
	    
	    // boardType를 전달하는 hidden input 추가
	    var boardTypeInput = document.createElement('input');
	    boardTypeInput.type = 'hidden';
	    boardTypeInput.name = 'boardType';
	    boardTypeInput.value = boardType;
	    form.appendChild(boardTypeInput);

	    // form을 문서에 추가하고 submit
	    document.body.appendChild(form);
	    form.submit();

	    // 필요하면 form을 제거
	    document.body.removeChild(form);
    }