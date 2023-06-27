function chkid(){
	let idElements = document.getElementsByClassName("idval");
    let isid = idElements[0].value;
	
	if(isid == "null"){
		alert("이 기능은 로그인 후 이용하실 수 있습니다");
		return false;
	}
	return true;
}

function re() {
        var checkboxes = document.getElementsByName("deleteWord");
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                return true;
            }
        }
        alert("적어도 하나의 단어를 선택해주세요.");
        return false;
    }