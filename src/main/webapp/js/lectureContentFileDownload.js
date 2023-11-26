function lectureContentFileDownload(classID, contentID) {
    // 동적으로 form 생성
    var form = document.createElement('form');
    form.method = 'post';
    form.action = 'lectureContentFileDownload.do';

	    // classID를 전달하는 hidden input 추가
	    var classIDInput = document.createElement('input');
	    classIDInput.type = 'hidden';
	    classIDInput.name = 'classID';
	    classIDInput.value = classID;
	    form.appendChild(classIDInput);
	    
		// classID를 전달하는 hidden input 추가
	    var contentIDInput = document.createElement('input');
	    contentIDInput.type = 'hidden';
	    contentIDInput.name = 'contentID';
	    contentIDInput.value = contentID;
	    form.appendChild(contentIDInput);

	    // form을 문서에 추가하고 submit
	    document.body.appendChild(form);
	    form.submit();

	    // 필요하면 form을 제거
	    document.body.removeChild(form);
}