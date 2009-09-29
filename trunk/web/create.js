function toolCancel(did){

    var tel = document.getElementById(did);
    if (tel)
        tel.style.visibility = 'hidden';

}
function callToolCreate(){
    toolCancel('toolDelete');
    toolCancel('toolUpdate');
    toolCancel('toolGoto');
    var tel = document.getElementById('toolCreate');
    if (tel)
        tel.style.visibility = 'visible';

    var fel = document.forms['tool'];
    if (fel){
        var inp = fel.elements['tool_op'];
        if (!inp)
            inp = fel.elements['op'];

        if (inp)
            inp.value = 'Create';
    }
    return false;
}

function toolCreateCancel(){

    var tel = document.getElementById('toolCreate');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}

function toolCreateClose(){

    var tel = document.getElementById('toolCreate');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}
