window.onload = function(){

    var inputList = document.getElementsByClassName("info");
    for(let i = 0; i<inputList.length; i++){

        /* --------------------------------------입력 요소 포커스시 설정 시작-------------------------------------- */
        inputList[i].addEventListener("focus", function(){
            /* 입력창 green */
            this.parentElement.setAttribute('class', 'labelFocus');
        });
        /* --------------------------------------입력 요소 포커스시 설정 끝--------------------------------------- */

        /* -----------------------------------------------------submit시 입력요소 에러 설정 시작------------------------------------------------------ */
        document.getElementById("joinForm").onsubmit = function(e){
            let needInfo = document.querySelectorAll(".needInfo"); //필수 입력요소 input, select 태그
            let isErr = document.querySelectorAll(".labelError");
            /* ---------------------------------성별 입력 요소 설정 시작--------------------------------- */
            let genderChk = document.getElementsByName('ugd');
            genderChk.forEach(function(item){
                //둘 다 체크가 안되었을 시
                if(!(genderChk[0].checked) && !(genderChk[1].checked)){
                    item.parentElement.classList.add('genderLabelErr');
                    document.getElementById("liGd").setAttribute('style', 'display : list-item');
                    e.preventDefault();
                }
                /* radio의 change 이벤트 관련 설정 */   
                item.addEventListener("change", function(){
                    if(genderChk[0].checked){
                        item.parentElement.nextElementSibling.classList.remove('genderLabelErr');
                        document.getElementById("liGd").setAttribute('style', 'display : none');
                    }else if(genderChk[1].checked){
                        item.parentElement.previousElementSibling.classList.remove('genderLabelErr');
                        document.getElementById("liGd").setAttribute('style', 'display : none');
                    }
                });
            });
            /* ---------------------------------성별 입력 요소 설정 끝--------------------------------- */
            needInfo.forEach(function(obj){
                let needInfoVal = obj.value.trim() //필수 입력요소 input, select의 value값
                
                if(needInfoVal === ""){
                    obj.parentElement.setAttribute('class', 'labelError');
                    switch(obj.parentElement.id){
                        case "labelID" : {
                            document.getElementById("liId1").setAttribute('style', 'display : list-item');
                            break;
                        }
                        case "labelPW" : {
                            document.getElementById("liPw1").setAttribute('style', 'display : list-item');
                            break;
                        }
                        case "labelNM" : {
                            document.getElementById("liNm1").setAttribute('style', 'display : list-item');
                            break;
                        }
                        case "labelDT" : {
                            document.getElementById("liDt1").setAttribute('style', 'display : list-item');
                            break;
                        }
                        case "labelTS" : {
                            document.getElementById("liTc").setAttribute('style', 'display : list-item');
                            break;
                        }
                        case "labelT" : {
                            document.getElementById("liT1").setAttribute('style', 'display : list-item');
                            break;
                        }
                    }
                    e.preventDefault();
                }

            });
            isErr.forEach(function(errObj){
                if(errObj.className == "labelError"){
                    e.preventDefault();
                }
            })
        }
        /* -----------------------------------------------------submit시 입력요소 에러 설정 끝------------------------------------------------------ */

        /* --------------------------------------------------------입력 요소 블러시 설정 시작-------------------------------------------------------- */
        inputList[i].addEventListener("blur", function(){
            let val = this.value.trim();/* 공백 제거해서 받기 */
            var labelType = this.parentElement.id;
            console.log(labelType);
            switch(labelType){
                /* ---------------------------------아이디 입력 요소 설정 시작--------------------------------- */
                /*
                1. 중복값 입력 후 blur시 데이터베이스와 연동되어 중복값 확인하는 기능 필요
                */
                case "labelID" : {
                    /* 아이디 특수기호 제한 */
                    var checkId = false;
                    /* 보안을 위해 const로 */
                    const forbiddenChars1 = /^([a-z0-9_-]){5,20}$/;
                    const forbiddenChars2 = /^([0-9_-]){5,20}$/;
                    /* 문자제한 : 영문자(소문자) + 숫자 + '-', '_' 허용 */
                    if(!(forbiddenChars1.test(val))){
                        checkId = true;
                    }
                    /* 만약 영문자 없이 숫자, 특수문자만 썼을 경우 */
                    if(forbiddenChars2.test(val)){
                        checkId = true;
                    }
                    if(val == ''){

                        /* 입력창 red */
                        this.parentElement.setAttribute('class', 'labelError');

                        // li 태그는 기본적으로 display: list-item 속성을 가져야 ●, ○, 숫자 같은 마커(marker)**가 보임
                        document.getElementById("liId1").setAttribute('style', 'display : list-item');
                        document.getElementById("liId2").setAttribute('style', 'display : none');
                        // document.getElementById("liId3").setAttribute('style', 'display : none');

                    /* 아이디: 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능 */
                    }else if(checkId){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liId1").setAttribute('style', 'display : none');
                        document.getElementById("liId2").setAttribute('style', 'display : list-item');
                        // document.getElementById("liId3").setAttribute('style', 'display : none');
                    }else{
                        this.parentElement.setAttribute('class', 'labelDefault');

                        document.getElementById("liId1").setAttribute('style', 'display : none');
                        document.getElementById("liId2").setAttribute('style', 'display : none');
                        // document.getElementById("liId3").setAttribute('style', 'display : none');
                    }
                    break;
                }
                /* ---------------------------------아이디 입력 요소 설정 끝--------------------------------- */

                /* ---------------------------------비밀번호 입력 요소 설정 끝--------------------------------- */
                /*
                1. 비밀번호 안정성 검사 기능 없음
                */
                case "labelPW" : {
                    var checkPw = false;

                    if(isSequentialChar(val)){
                        checkPw = true;
                    }
                    if(isRepeatedChar(val)){
                        checkPw = true;
                    }
                    /* 연속 문자 제한 함수 */
                    function isSequentialChar(str){
                        /* 매개변수로 받은 비밀번호를 첫번째 글자부터 마지막까지 글자까지 유니코드로 변환 */
                        for(let i = 0; i < str.length-3; i++){
                            const str1 = str.charCodeAt(i);
                            const str2 = str.charCodeAt(i + 1);
                            const str3 = str.charCodeAt(i + 2);
                            const str4 = str.charCodeAt(i + 3);
                            /* 연속되는 글자 있으면 true 반환 */
                            if(str2 == str1 + 1 && str3 == str2 + 1 && str4 == str3 + 1){ /* 오름차순 */
                                return true;
                            }
                            if(str2 == str1 - 1 && str3 == str2 - 1 && str4 == str3 - 1){ /* 내림차순 */
                                return true;
                            }
                        }
                    }
                    /* 반복 문자 제한 함수 */
                    function isRepeatedChar(str){
                        /* 매개변수로 받은 비밀번호를 첫번째 글자부터 마지막까지 글자까지 유니코드로 변환 */
                        for(let i = 0; i < str.length-3; i++){
                            const str1 = str.charCodeAt(i);
                            const str2 = str.charCodeAt(i + 1);
                            const str3 = str.charCodeAt(i + 2);
                            const str4 = str.charCodeAt(i + 3);
                            /* 연속되는 글자 있으면 true 반환 */
                            if(str2 == str1 && str3 == str2 && str4 == str3){ /* 오름차순 */
                                return true;
                            }
                        }
                    }
                    if(val == ''){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liPw1").setAttribute('style', 'display : list-item');
                        document.getElementById("liPw2").setAttribute('style', 'display : none');
                        document.getElementById("liPw3").setAttribute('style', 'display : none');

                    /* 비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자만 사용 가능 */
                    }else if(val.length < 8 || val.length > 16){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liPw1").setAttribute('style', 'display : none');
                        document.getElementById("liPw2").setAttribute('style', 'display : list-item');
                        document.getElementById("liPw3").setAttribute('style', 'display : none');

                    }else if(checkPw){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liPw1").setAttribute('style', 'display : none');
                        document.getElementById("liPw2").setAttribute('style', 'display : none');
                        document.getElementById("liPw3").setAttribute('style', 'display : list-item');


                    }else{
                        this.parentElement.setAttribute('class', 'labelDefault');

                        document.getElementById("liPw1").setAttribute('style', 'display : none');
                        document.getElementById("liPw2").setAttribute('style', 'display : none');
                        document.getElementById("liPw3").setAttribute('style', 'display : none');
                    }
                    break;
                }
                /* ---------------------------------비밀번호 입력 요소 설정 끝--------------------------------- */

                /* ---------------------------------email 입력 요소 설정 시작--------------------------------- */
                case "labelEM" : {
                    /* 예제 10_string01, 02 참조 */
                    const arrUrl = [".co.kr", ".com", ".net", ".or.kr", ".go.kr"];
                    var checkEm1 = false;
                    var checkEm2 = false;
                    var checkEm3 = true;
                    var checkEm4 = false;
                    var checkEm5 = false;

                    /* 만약 @가 없거나 @기호가 처음에 오면 true 반환 */
                    if(val.indexOf("@") <= 0){checkEm1 = true;}

                    /* 만약 첫 @ 다음부터 탐색했을 때 @이 있으면 true 반환 */
                    if(val.indexOf("@", val.indexOf("@")+1) > 0){
                        checkEm2 = true;
                    }
                    for(let i = 0; i < arrUrl.length; i++){
                        if(val.indexOf(arrUrl[i]) > 0){console.log(val.indexOf(arrUrl[i])); checkEm3 = false;}

                        if(val.indexOf(arrUrl[i]) > -1 && val.indexOf(arrUrl[i]) < val.indexOf("@")){/* 만약 도메인이 @보다 앞에 있으면 true 반환 */
                            checkEm4 = true;
                            break;
                        }
                        if(val.indexOf(arrUrl[i]) == val.indexOf("@")+1){/* 만약 도메인이 @ 바로 뒤에 있으면 true 반환 */
                            checkEm5 = true;
                            break;
                        }

                    }
                    console.log(checkEm1, checkEm2, checkEm3, checkEm4, checkEm5);
                    if(val == ''){
                        this.parentElement.setAttribute('class', 'labelDefault');
                        document.getElementById("liEm1").setAttribute('style', 'display : none');

                    }else if(checkEm1 || checkEm2 || checkEm3 || checkEm4 || checkEm5){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liEm1").setAttribute('style', 'display : list-item');

                    }else{
                        this.parentElement.setAttribute('class', 'labelDefault');

                        document.getElementById("liEm1").setAttribute('style', 'display : none');
                    }
                    break;
                }
                /* ---------------------------------email 입력 요소 설정 끝--------------------------------- */

                /* ---------------------------------이름 입력 요소 설정 시작--------------------------------- */
                case "labelNM" : {
                    var checkNm = false;
                    const forbiddenChars = /^([A-Za-z0-9가-힣]{1,50})$/;
                    /* 문자제한 : 특수기호, 공백 불가 한글 자음 또는 모음만 쓰기 불가 */
                    if(!(forbiddenChars.test(val))){
                        checkNm = true;
                    }
                    if(val == ''){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liNm1").setAttribute('style', 'display : list-item');
                        document.getElementById("liNm2").setAttribute('style', 'display : none');

                    }else if(checkNm){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liNm1").setAttribute('style', 'display : none');
                        document.getElementById("liNm2").setAttribute('style', 'display : list-item');

                    }else{
                        this.parentElement.setAttribute('class', 'labelDefault');

                        document.getElementById("liNm1").setAttribute('style', 'display : none');
                        document.getElementById("liNm2").setAttribute('style', 'display : none');
                    }
                    break;
                }
                /* ---------------------------------이름 입력 요소 설정 끝--------------------------------- */

                /* ---------------------------------생년월일 입력 요소 설정 시작--------------------------------- */
                case "labelDT" : {
                    var checkDt = false;
                    let str1 = val.substr(0, 4); // 입력값 연도
                    let str2; // 입력값 월
                    let str3; // 입력값 일
                    const forbiddenChars = /^(19|20)\d{2}.?(0[1-9]|1[0-2]).?(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/;
                    if(val.indexOf(".") == -1){
                        str2 = val.substr(4, 2);
                        str3 = val.substr(6, 2);
                    }else{
                        str2 = val.substr(5, 2);
                        str3 = val.substr(8, 2);
                    }
                    /* 현재 날짜 가져오기 */
                    const d = new Date();
                    console.log(d);

                    /*
                    1월 : 0
                    2월 : 1
                    ...
                    11월 : 10
                    12월 : 11
                    */
                    var mon = d.getMonth() + 1 > 9 ? d.getMonth() + 1 : "0" + (d.getMonth() + 1);
                    var dd = d.getDate() > 9 ? d.getDate() : "0" + d.getDate();

                    /* 현재날짜 8자리로 표현하기 */
                    var dt = d.getFullYear()+mon+dd;
                    console.log(dt);

                    /* 날짜형식 체크 */
                    if(!(forbiddenChars.test(val))){
                        checkDt = true;
                    }
                    /* 날짜가 현재 날짜보다 전인지 체크 */
                    if((str1+str2+str3) > dt){
                        checkDt = true;
                    }
                    /* 해당 월에 맞는 일수 and 윤년 체크 */
                    switch(str2){

                        case "02" : {
                            if(str1 % 4 == 0){
                                if(str3 > 29)checkDt = true;
                            }else{
                                if(str3 > 28)checkDt = true;
                            }
                            break;
                        }
                        case "04", "06", "09", "11" : {
                            if(str3 > 30){
                                checkDt = true;
                            }
                            break;
                        }
                        /* 날짜형식 체크에서 31을 초과하는 숫자는 못오기 때문에 따로 함수 필요없음 */
                        default : {
                            break;
                        }
                    }
                    if(val == ''){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liDt1").setAttribute('style', 'display : list-item');
                        document.getElementById("liDt2").setAttribute('style', 'display : none');

                    }else if(checkDt){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liDt1").setAttribute('style', 'display : none');
                        document.getElementById("liDt2").setAttribute('style', 'display : list-item');

                    }else{
                        this.parentElement.setAttribute('class', 'labelDefault');

                        this.value = str1 + '.' + str2 + '.' + str3;/* setAttribute는 보여주는 것 까진 바꿔주지 않음!! */

                        document.getElementById("liDt1").setAttribute('style', 'display : none');
                        document.getElementById("liDt2").setAttribute('style', 'display : none');
                    }
                    break;
                }
                /* ---------------------------------생년월일 입력 요소 설정 끝--------------------------------- */

                /* ---------------------------------통신사 선택 요소 설정 시작--------------------------------- */
                case "labelTS" : {
                    if(val == ''){
                        this.parentElement.setAttribute('class', 'labelError');
                        document.getElementById("liTc").setAttribute('style', 'display : list-item');
                    }else{
                        this.parentElement.setAttribute('class', 'labelDefault');
                        document.getElementById("liTc").setAttribute('style', 'display : none');
                    }
                    break;
                }
                /* ---------------------------------통신사 선택 요소 설정 끝--------------------------------- */

                /* ---------------------------------휴대전화 번호 입력 요소 설정 시작--------------------------------- */
                case "labelT" : {
                    const forbiddenChars = /^(010)-?\d{4}-?\d{4}$/
                    let tNum1 = val.substr(0, 3);
                    let tNum2;
                    let tNum3;
                    if(val.indexOf("-") == -1){
                        tNum2 = val.substr(3, 4);
                        tNum3 = val.substr(7, 4);
                    }else{
                        tNum2 = val.substr(4, 4);
                        tNum3 = val.substr(9, 4);
                    }
                    if(val == ''){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liT1").setAttribute('style', 'display : list-item');
                        document.getElementById("liT2").setAttribute('style', 'display : none');

                    }else if(!(forbiddenChars.test(val))){
                        this.parentElement.setAttribute('class', 'labelError');

                        document.getElementById("liT1").setAttribute('style', 'display : none');
                        document.getElementById("liT2").setAttribute('style', 'display : list-item');

                    }else{
                        this.parentElement.setAttribute('class', 'labelDefault');

                        this.value = tNum1 + '-' + tNum2 + '-' + tNum3;

                        document.getElementById("liT1").setAttribute('style', 'display : none');
                        document.getElementById("liT2").setAttribute('style', 'display : none');
                    }
                }
                /* ---------------------------------휴대전화 번호 입력 요소 설정 끝--------------------------------- */

            }
        });
        /* --------------------------------------------------------입력 요소 블러시 설정 끝------------------------------------------------------------ */   
    }
}
