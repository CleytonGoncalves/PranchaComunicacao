/* ##### Scripts de uso geral ##### */

$.fn.scrollView = function () {
    return this.each(function () {
        $('html, body').animate({
            scrollTop: $(this).offset().top - 20
        }, 500);
    });
};

String.prototype.format = String.prototype.f = function() {
    var s = this,
        i = arguments.length;
    
    while (i--) {
        s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return s;
};
