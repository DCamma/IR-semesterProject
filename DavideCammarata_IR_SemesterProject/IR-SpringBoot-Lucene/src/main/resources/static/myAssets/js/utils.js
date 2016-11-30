function setHeader(xhr) {
    xhr.setRequestHeader('Authorization', sessionStorage.getItem("token") != null ? sessionStorage.getItem("token") : localStorage.getItem("token"));
}

function partialRender(err, out) {
    $("#content-window").html(out)
}

function partialAppend(err, out) {
    $("#content-window").append(out)
}

function navbarRender(err, out) {
    $("#nav-xml").html(out)
}

function notify(message, type, timer, from, align) {
    $.growl({
        title: '',
        message: message,
        url: ''
    }, {
        element: 'body',
        type: type || "inverse",
        allow_dismiss: true,
        offset: {
            x: 20,
            y: 85
        },
        spacing: 10,
        z_index: 1031,
        delay: 2500,
        timer: timer || 1000,
        placement: {
            from: from || "bottom",
            align: align || "center"
        },
        url_target: '_blank',
        mouse_over: false,
        icon_type: 'class',
        template: '<div data-growl="container" class="alert" role="alert">' +
        '<button type="button" class="close" data-growl="dismiss">' +
        '<span aria-hidden="true">&times;</span>' +
        '<span class="sr-only">Close</span>' +
        '</button>' +
        '<span data-growl="icon"></span>' +
        '<span data-growl="title"></span>' +
        '<span data-growl="message"></span>' +
        '<a href="#" data-growl="url"></a>' +
        '</div>'
    });
};

function prettyFormatDate(epochDate) {
    var monthNames = [
        "January", "February", "March",
        "April", "May", "June", "July",
        "August", "September", "October",
        "November", "December"
    ];

    var date = new Date(Number(epochDate));
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();

    return day + ' ' + monthNames[monthIndex] + ' ' + year;
}

function prettyFormatDateWithTime(epochDate) {
    var monthNames = [
        "January", "February", "March",
        "April", "May", "June", "July",
        "August", "September", "October",
        "November", "December"
    ];

    var date = new Date(Number(epochDate));
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();
    var hours = date.getHours();
    if (parseInt(hours) < 10) {
        hours = "0" + hours;
    }

    var minutes = date.getMinutes();

    if (parseInt(minutes) < 10) {
        minutes = "0" + minutes;
    }

    return day + ' ' + monthNames[monthIndex] + ' ' + year + ' - ' + hours + ':' + minutes;
}