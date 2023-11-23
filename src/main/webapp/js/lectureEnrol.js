function lectureEnrol(userID, classID, userEmailChecked) {
	
    if (userID == null || userID == "" || userID == "null") {
        alert("로그인 후 이용 가능한 서비스입니다.");
        return;
	}

    if (!userEmailChecked) {
        if (confirm("이메일 인증 후 이용 가능한 서비스입니다.\n인증 메일을 다시 받으시겠습니까?")) {
            location.href = '#';
        }
        return; // 이메일이 확인되지 않았을 때 실행 중지
    }

    var confirmEnrollment = confirm("수강신청 하시겠습니까?");
    if (confirmEnrollment) {
    	var form = document.createElement('form');
        form.method = 'post';
        form.action = 'lectureEnrol.do';

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
    } else {
        alert("수강신청이 취소되었습니다.");
    }
}