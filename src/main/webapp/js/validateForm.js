function validateForm() {
    // 필수 입력 필드 배열
    var requiredFields = ["contentTitle", "contentWeek", "startDate", "endDate"];
    
    // 강의 자료 및 강의 영상 필드 배열
    var fileFields = ["contentFile"];
    var videoFields = ["video01", "video02", "video03"];

    // 각 필드에 대한 이름 매핑
    var fieldNames = {
      "contentTitle": "강의제목",
      "contentWeek": "강의주차",
      "startDate": "수강시작날짜",
      "endDate": "수강종료날짜",
      "contentFile": "강의 자료",
      "video01": "강의 영상"
    };

    // 누락된 필드를 담을 배열
    var missingFields = [];

    // 필수 입력 필드 확인
    for (var i = 0; i < requiredFields.length; i++) {
      var fieldName = requiredFields[i];
      var fieldValue = document.getElementsByName(fieldName)[0].value.trim();

      if (fieldValue === "") {
        missingFields.push(fieldNames[fieldName]);
      }
    }

    // 강의 자료 필드 확인
    var fileFieldsExist = fileFields.some(function(fieldName) {
      return document.getElementsByName(fieldName)[0].value.trim() !== "";
    });

    // 강의 영상 필드 확인
    var videoFieldsExist = videoFields.some(function(fieldName) {
      return document.getElementsByName(fieldName)[0].value.trim() !== "";
    });

	 // 누락된 필드가 있을 경우 알림
    if(missingFields.length > 0) {
      var alertMessage = "다음 필수 입력 필드가 누락되었습니다:\n" + missingFields.join(", ");
      alert(alertMessage);
      return false; // 제출을 막음
    }
 	// 강의 주차 확인
    var contentWeek = parseInt(document.getElementsByName("contentWeek")[0].value, 10);
    if(isNaN(contentWeek) || contentWeek < 1) {
    	alert("강의주차를 설정해주세요.");
    	return false; // 제출을 막음
    }
	 
    // 강의 자료 또는 강의 영상 중 적어도 하나는 입력되어야 함
    if (!fileFieldsExist && !videoFieldsExist) {
    	alert("강의 자료 또는 강의 영상은 하나라도 있어야합니다.");
    	return false; // 제출을 막음
    }
    
    // 누락된 필드가 없으면 폼 제출 허용
    return true;
  }