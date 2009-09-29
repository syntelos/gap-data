function toolCancel(did){

    var tel = document.getElementById(did);
    if (tel)
        tel.style.visibility = 'hidden';

}
function callToolUpdate(){
    toolCancel('toolCreate');
    toolCancel('toolDelete');
    toolCancel('toolGoto');
    var tel = document.getElementById('toolUpdate');
    if (tel)
        tel.style.visibility = 'visible';

    var fel = document.forms['tool'];
    if (fel){
        var inp = fel.elements['tool_op'];
        if (!inp)
            inp = fel.elements['op'];

        if (inp)
            inp.value = 'Update';
    }
    return false;
}
function toolUpdateCancel(){

    var tel = document.getElementById('toolUpdate');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}

function toolUpdateClose(){

    var tel = document.getElementById('toolUpdate');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}
