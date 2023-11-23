function lectureSearch(keyword) {
    // 동적으로 form 생성
    var form = document.createElement('form');
    form.method = 'post';
    form.action = 'lectureSearch.do';

    // classID를 전달하는 hidden input 추가
    var keywordInput = document.createElement('input');
    keywordInput.type = 'hidden';
    keywordInput.name = 'keyword';
    keywordInput.value = keyword;
    form.appendChild(keywordInput);

    // form을 문서에 추가하고 submit
    document.body.appendChild(form);
    form.submit();

    // 필요하면 form을 제거
    document.body.removeChild(form);
}