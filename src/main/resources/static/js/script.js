const SYMBOLS = {
    operations: ['/', '*', '+', '-'],
    brackets: ['(', ')']
};
let history = {
    actions: [],
    position: 0
};

let newCalculation = false;

document.addEventListener("DOMContentLoaded", ready);

function ready() {
    resizeFont();
    renewCalculation(true);
}

function addSymbol(symbol) {
    resizeFont();
    symbol = transformIfNecessary(symbol);
    if (isAllowedAdding(symbol, getExp())) {
        let value;
        if (newCalculation) {
            setExp("");
            setMessage("");
            renewCalculation(false);
        }
        value = getExp() + symbol;
        setExpValue(value, value);
    }
}

function clean() {
    audioPlayClear();
    setExpValue("", "");
    setMessage("");
    renewCalculation(true);
}

function renewCalculation(bol) {
    newCalculation = bol;
    if (bol) {
        setDisable("equal");
    } else {
        setEnable("equal");
    }
}

function setDisable(elementId) {
    document.getElementById(elementId).setAttribute('disabled', 'disabled');
}

function setEnable(elementId) {
    document.getElementById(elementId).removeAttribute('disabled');
}

function calculate() {
    renewCalculation(true);
    sendCalculateRequest();
}

function transformIfNecessary(symbol) {
    let last = getExp().substring(getExp().length - 1);

    if (symbol === '.') {
        if (isBracket(last) || isOperator(last) || last === "") {
            setExpValue(getExp() + '0', getValue() + '0');
        }
        return symbol;
    }

    if (symbol === "-") {
        if (last === "+") {
            return "-";
        } else if (last === "-") {
            return "+";
        }
    }

    if (isBracket(symbol)) {
        if (symbol === '(') {
            if (isNumber(last)) {
                setExpValue(getExp() + '*', getValue() + '*');
            }
        }
    }
    return symbol;
}

function isAllowedAdding(symbol, exp) {
    let last = exp.substring(exp.length - 1);

    if (symbol === '.') {
        if (isNumber(last)) {
            for (let i = exp.length - 1; i >= 0; i--) {
                let ch = exp.charAt(i);
                if (isOperator(ch) || isBracket(ch)) {
                    return true;
                } else if (ch === ".") {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    } else if (isOperator(symbol)) {
        if ((symbol === "-" && (last === "" || last === '('))
            || ((last !== '' || symbol === '-') && (isNumber(last) || last === ')'))) {
            return true;
        }
    } else if (isBracket(symbol)) {
        if (symbol === '(') {
            if (isOperator(last) || last === '' || last === '(') {
                return true;
            }
        } else if (symbol === ')') {
            let leftBrakes = exp.split('(').length - 1;
            let rightBrakes = exp.split(')').length - 1;
            if (((leftBrakes - rightBrakes) > 0) && (isNumber(last) || last === ')')) {
                return true;
            }
        }
    } else if (isNumber(symbol)) {
        if (isNumber(last) || isOperator(last) || last === '(' || last === '' || last === '.') {
            return true;
        }
    }
    return false;
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

function isOperator(s) {
    return SYMBOLS.operations.includes(s);
}

function isBracket(s) {
    return SYMBOLS.brackets.includes(s);
}

function isNumber(s) {
    return !isOperator(s) && !isBracket(s) && s !== '.' && s !== '';
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
    audioPlayLoading();
    let request = new XMLHttpRequest();
    request.open('GET', '/calculate?exp=' + encodeURIComponent(getExp()), true);
    request.addEventListener('readystatechange', function () {
        if (request.readyState == 4) {
            let result = JSON.parse(request.response);
            if (request.status == 200) {
                setValue(result.calculation.result);
                addToHistory();
            } else if (request.status == 400) {
                setMessage(result.error);
            }
        }
    });
    request.send();
}

function audioPlayLoading() {
    // audioPlay("/media/clear.mp3");
}

function audioPlayClear() {
    // audioPlay("/media/clear.mp3");
}

function audioPlay(url) {
    let a = new Audio(url);
    a.play();
}