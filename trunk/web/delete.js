function toolCancel(did){

    var tel = document.getElementById(did);
    if (tel)
        tel.style.visibility = 'hidden';

}
function callToolDelete(){
    toolCancel('toolCreate');
    toolCancel('toolUpdate');
    toolCancel('toolGoto');
    var tel = document.getElementById('toolDelete');
    if (tel)
        tel.style.visibility = 'visible';

    var fel = document.forms['tool'];
    if (fel){
        var inp = fel.elements['tool_op'];
        if (!inp)
            inp = fel.elements['op'];

        if (inp)
            inp.value = 'Delete';
    }
    return false;
}

function toolDeleteCancel(){

    var tel = document.getElementById('toolDelete');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}

function toolDeleteClose(){

    var tel = document.getElementById('toolDelete');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}
