
function handleOnInput(e)  {
  e.value = e.value.replace(/[^A-Za-z]/ig, '')
}

function result(){
let ans = document.querySelector(".answer").value;
let answer = ans.toUpperCase();
let input = document.querySelectorAll(".input");

let chk = 0;
for(let i=0; i<5; i++){
	if(input[i].value.toUpperCase() == answer[i]){
	input[i].style.background = "green";
	chk +=1;
}else if(answer.includes(input[i].value.toUpperCase())){
	input[i].style.background = "yellow";
}else{
	input[i].style.background="#888";
}
input[i].classList.remove("input");	
}
if(chk ==5){
alert("정답입니다\n"+answer);
}else{
let template = `
	<input type="text" class="input"  maxlength="1" oninput="handleOnInput(this)">
	<input type="text" class="input"  maxlength="1" oninput="handleOnInput(this)">
	<input type="text" class="input"  maxlength="1" oninput="handleOnInput(this)">
	<input type="text" class="input"  maxlength="1" oninput="handleOnInput(this)">
	<input type="text" class="input"  maxlength="1" oninput="handleOnInput(this)">
`;
document.querySelector(".inputbox").insertAdjacentHTML('beforeend',template);
}
}

function focus(){
	let key = document.getElementByClassName("input");
	if(this.value.length == this.maxlength){
		
	}
}
