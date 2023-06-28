//로그인

function chk1(){
    let warn1 = document.getElementById("warnid");
    let id = document.getElementById("user_id").value;
    if(!id){
        warn1.innerHTML="아이디를 입력해주세요"
    }
    else{
        warn1.innerHTML=""
    }
}
function chk2(){
let warn2 = document.getElementById("warnpw");
let pw = document.getElementById("user_pw").value;
    if(!pw){
        warn2.innerHTML="패스워드를 입력해주세요"
    }
    else{
        warn2.innerHTML=""
    }
}

//중복체크

// 회원가입
let idcheck = /^[a-z0-9]+$/
let pwcheck = /^[a-zA-Z0-9]+$/
function chkall(){
	let idval = document.getElementById("idval").value;
	let pwval = document.getElementById("pwval").value;
	let chkval = document.getElementById("chkpwval").value;
	let nameval = document.getElementById("nameval").value;
	let ageval = document.getElementById("ageval").value;
	let genval = document.getElementById("genval").value;
	let exist = document.getElementById("existsResult").value;
	let id = document.getElementById("id_inner");
	let pw = document.getElementById("pw_inner");
	let chk = document.getElementById("chk_inner");
	let name = document.getElementById("name_inner");
	let age = document.getElementById("age_inner");
	let gender = document.getElementById("gen_inner");
	if (!idcheck.test(idval) || idval.length<8){
		id.innerHTML="아이디는 영소문자,숫자로 구성된 8글자 이상으로 작성하세요";
		return false;
	}
	else{
		id.innerHTML="";
	}
	if (!pwcheck.test(pwval) || pwval.length<8){
		pw.innerHTML="비밀번호는 영소문자,숫자로 구성된 8글자 이상으로 작성하세요";
		return false;
	}else{
		pw.innerHTML="";
	}
	if(pwval !=chkval){
		chk.innerHTML="비밀번호가 일치하지 않습니다";
		return false;
	}else{
		chk.innerHTML="";
	}
	if(!nameval){
		name.innerHTML="이름을 입력하세요";
		return false;
	}else{
		name.innerHTML="";
	}
	if(!ageval){
		age.innerHTML="나이";
		return false;
	}else{
		age.innerHTML="";
	}if(genval == "0"){
		gender.innerHTML="성별을 선택하세요";
		return false;
	}else{
		gender.innerHTML="";
	}
	if(exist != "false"){
	    alert("중복체크를 해주세요");
	    return false;
	}

	return true;
}



function re() {
        var checkboxes = document.getElementsByName("save");
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                return true;
            }
        }
        alert("적어도 하나의 단어를 선택해주세요.");
        return false;
    }