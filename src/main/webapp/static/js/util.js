function formOnEnter(ev,form){
    form.addEventListener("keydown", function (ev) {
        if (ev.keyCode === 13) {
            ev.preventDefault()
            let current = $(ev.target);
            let index = parseFloat(current.attr('data-index'));
            $('[data-index="' + (index + 1).toString() + '"]').focus();
        }
    })
}