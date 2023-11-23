function lectureRoom(classID) {
	var form = document.createElement('form');
    form.method = 'post';
    form.action = 'lectureRoom.do';

    // classID를 전달하는 hidden input 추가
    var classIDInput = document.createElement('input');
    classIDInput.type = 'hidden';
    classIDInput.name = 'classID';
    classIDInput.value = classID;
    form.appendChild(classIDInput);

    // form을 문서에 추가하고 submit
    document.body.appendChild(form);
    form.submit();

    // form을 제거
    document.body.removeChild(form);
}