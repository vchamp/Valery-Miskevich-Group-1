/**
 * Created by Ilya_Shknai on 06-Nov-14.
 */
function login() {
    var login = $('.login_field').val();
    post('/LoginServlet/', {name: login});
    console.log(login);
    if (isValidLogin(login)) {
        alert("success login=" + login);
    } else {
        alert("Not valid login. Try again");
    }
}

function isValidLogin(login) {
    var nameRegex = /^[a-zA-Z\-]+$/;
    return nameRegex.test(login);
}

function exchangeMoney(evt, amount) {
    var from = $(".from option:selected").text();
    var to = $(".to option:selected").text()
    var amount = $(".amount ").val()

    var usdRate = 10700;
    var eurRate = 13500;

    var result = '1';
    console.log(amount);

    if (from == 'BYR') {
        if (to == 'USD') {
            result = amount / usdRate;
        } else if (to == 'EUR') {
            result = amount / eurRate;
        }
    } else if (from == 'USD') {
        if (to == 'BYR') {
            result = amount * usdRate;
        } else if (to == 'EUR') {
            result = amount * usdRate / eurRate;
        }
    } else if(from == 'EUR'){
        if (to == 'BYR') {
            result = amount * eurRate;
        } else if (to == 'USD') {
            result = amount * eurRate / usdRate;
        }
    }


    var resultField = $(".calculate_result");
    resultField.text(Math.round(result * 100) / 100);
}


function isValidNumber(event) {
    var key = window.event ? event.keyCode : event.which;

    if (event.keyCode == 8 || event.keyCode == 46
        || event.keyCode == 37 || event.keyCode == 39) {
        return true;
    }
    else if (key < 48 || key > 57) {
        var theEvent = event || window.event;
        if (theEvent.preventDefault) {
            theEvent.preventDefault();
        }
        return false;
    }
    else return true;
}

function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
         }
    }

    document.body.appendChild(form);
    form.submit();
}
