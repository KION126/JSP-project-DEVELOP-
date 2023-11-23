function lectureRoomNoticeSearch(classID, currentPage, searchOption, searchKeyword) {
		// 동적으로 form 생성
	    var form = document.createElement('form');
	    form.method = 'post';
	    form.action = 'lectureRoomNoticeSearch.do';

	    // classID를 전달하는 hidden input 추가
	    var classIDInput = document.createElement('input');
	    classIDInput.type = 'hidden';
	    classIDInput.name = 'classID';
	    classIDInput.value = classID;
	    form.appendChild(classIDInput);
	    
	    // boardID를 전달하는 hidden input 추가
	    var currentPageInput = document.createElement('input');
	    currentPageInput.type = 'hidden';
	    currentPageInput.name = 'currentPage';
	    currentPageInput.value = currentPage;
	    form.appendChild(currentPageInput);
	    
	    // boardID를 전달하는 hidden input 추가
	    var searchOptionInput = document.createElement('input');
	    searchOptionInput.type = 'hidden';
	    searchOptionInput.name = 'searchOption';
	    searchOptionInput.value = searchOption;
	    form.appendChild(searchOptionInput);
	    
	    // boardID를 전달하는 hidden input 추가
	    var searchKeywordInput = document.createElement('input');
	    searchKeywordInput.type = 'searchKeywordInput';
	    searchKeywordInput.name = 'searchKeyword';
	    searchKeywordInput.value = searchKeyword;
	    form.appendChild(searchKeywordInput);

	    // form을 문서에 추가하고 submit
	    document.body.appendChild(form);
	    form.submit();

	    // 필요하면 form을 제거
	    document.body.removeChild(form);
}