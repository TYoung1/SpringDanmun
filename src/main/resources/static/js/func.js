// function color(evt){
//     var row = document.getElementsByClassName("word_wrap");
//     for(let i=0; i<row.length;i++){
//         row[i].className = row[i].className.replace(" active","");
//     }
//     evt.currentTarget.className += " active";
// }
let topbtn = document.getElementById("goup");
topbtn.addEventListener('click',function(){
    window.scrollTo({top:0,behavior:"smooth"});
})

window.onscroll = ()=>window.scrollY > 400 ? topbtn.style.opacity=1 : topbtn.style.opacity=0;
