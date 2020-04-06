let history = {
    actions: [],
    position: 0
};

document.addEventListener("DOMContentLoaded", ready);

function ready() {
    resizeFont();
}

function sendAddSymbolRequest(symbol) {
    let request = new XMLHttpRequest();
    request.open('GET', '/add?symbol=' + encodeURIComponent(symbol), true);
    request.addEventListener('readystatechange', function () {
        if (request.readyState == 4) {
            let result = JSON.parse(request.response);
            if (request.status == 200) {
                setExpValue(result.calculation.expression, result.calculation.expression);
            } else if (request.status == 400) {
                audioPlayForbidden()
            }
        }
    });
    request.send();
}

function addSymbol(symbol) {
    sendAddSymbolRequest(symbol);
    resizeFont();
}

function sendCleanRequest() {
    let request = new XMLHttpRequest();
    request.open('GET', '/clean', true);
    request.addEventListener('readystatechange', function () {
        if (request.readyState == 4) {
            let result = JSON.parse(request.response);
            if (request.status == 200) {
                setExpValue(result.calculation.expression, result.calculation.expression);
            } else if (request.status == 400) {
                audioPlayForbidden()
            }
        }
    });
    request.send();
}

function cln() {
    audioPlayClear();
    sendCleanRequest();
    setExpValue("", "");
    setMessage("");
}

function calculate() {
    sendCalculateRequest();
}

function resizeFont() {
    let length = document.getElementById('value').value.length;
    let newLength = length;
    if (length > 9 && length < 19) {
        newLength = "27px";
    } else if (length > 18) {
        newLength = "16px";
    } else if (length < 10) {
        newLength = "50px"
    }
    document.getElementById('value').style.fontSize = newLength;
}

function getExp() {
    return document.getElementById('exp').value;
}

function setExp(newText) {
    return document.getElementById('exp').value = newText;
}

function getValue() {
    return document.getElementById('value').value;
}

function setValue(newValue) {
    document.getElementById('value').value = newValue;
}

function setMessage(newMessage) {
    document.getElementById('message').value = newMessage;
}

function setExpValue(newExp, newValue) {
    setExp(newExp);
    setValue(newValue);
}

function setDisable(elementId) {
    document.getElementById(elementId).setAttribute('disabled', 'disabled');
}

function setEnable(elementId) {
    document.getElementById(elementId).removeAttribute('disabled');
}

function addToHistory() {
    history.actions.push({
        exp: getExp(),
        value: getValue()
    });
    history.position = history.actions.length - 1;
    if (history.position > 0) {
        setEnable("undo");
        setDisable("redo");
    }
}

function undoBtn() {
    if (history.position - 1 >= 0) {
        history.position--;
        setExpValue(history.actions[history.position].exp, history.actions[history.position].value);
        setEnable("redo");
        if (history.position - 1 < 0) {
            setDisable("undo");
        }
    }
}

function reBtn() {
    if (history.position + 1 <= history.actions.length - 1) {
        history.position++;
        setExpValue(history.actions[history.position].exp, history.actions[history.position].value);
        setEnable("undo");
        if (history.position + 1 > history.actions.length - 1) {
            setDisable("redo");
        }
    }
}

function sendCalculateRequest() {
    let request = new XMLHttpRequest();
    request.open('GET', '/calculate?exp=' + encodeURIComponent(getExp()), true);
    request.addEventListener('readystatechange', function () {
        if (request.readyState == 4) {
            let result = JSON.parse(request.response);
            if (request.status == 200) {
                audioPlayResult();
                setValue(result.calculation.result);
                setMessage("");
                addToHistory();
            } else if (request.status == 400) {
                if (result.error === "Division by Zero") {
                    audioPlayDivisionBy0();
                } else {
                    audioPlayError();
                }
                setMessage(result.error);
            }
        }
    });
    request.send();
}

function audioPlayResult() {
    audioPlay("/media/result.mp3");
}

function audioPlayClear() {
    audioPlay("/media/clear.mp3");
}

function audioPlayForbidden() {
    audioPlay("/media/forbidden.mp3");
}

function audioPlayError() {
    audioPlay("/media/error.mp3");
}

function audioPlayDivisionBy0() {
    audioPlay("/media/divisionby0.mp3");
}

function audioPlay(url) {
    let audio = new Audio(url);
    audio.play();
}