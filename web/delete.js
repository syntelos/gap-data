
function callToolDelete(){
    var tel = document.getElementById('tool');
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

function toolCancel(){

    var tel = document.getElementById('tool');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}

function toolClose(){

    var tel = document.getElementById('tool');
    if (tel)
        tel.style.visibility = 'hidden';

    return false;
}
