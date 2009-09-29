function toolCancel(did){

    var tel = document.getElementById(did);
    if (tel)
        tel.style.visibility = 'hidden';

}
function callToolGoto(){
    toolCancel('toolCreate');
    toolCancel('toolDelete');
    toolCancel('toolUpdate');
    var tel = document.getElementById('toolGoto');
    if (tel)
        tel.style.visibility = 'visible';

    var fel = document.forms['tool'];
    if (fel){
        var inp = fel.elements['tool_op'];
        if (!inp)
            inp = fel.elements['op'];

        if (inp)
            inp.value = 'Goto';
    }
    return false;
}

function toolGotoCancel(){

    var tel = document.getElementById('toolGoto');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}

function toolGotoClose(){

    var tel = document.getElementById('toolGoto');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}
