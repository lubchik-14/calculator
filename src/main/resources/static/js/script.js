const operations = ['/', '*', '+', '-'];
const brackets = ['(', ')'];
let newCalculation = false;

function addSymbol(symbol) {
    resizeFont();
    if (isAllowedAdding(symbol, getExp())) {
        let value;
        if (newCalculation) {
            setExp("");
            newCalculation = false;
        }
        value = getExp() + symbol;
        setValue(calculate(value));
        setExp(value);
    }
}

function clean() {
    setExp("");
    setValue("");
}

function calculate(exp) {
    try {
        return eval(exp);
    } catch (e) {
        return getValue();
    }
}

function equal() {
    newCalculation = true;
    setExp(getValue());
}

function isAllowedAdding(symbol, exp) {
    let last = exp.substring(exp.length - 1);

    if (symbol === '.') {
        if (isNumber(last)) {
            return true;
        }
    } else if (isOperator(symbol)) {
        if ((last !== '' || symbol === '-') && (isNumber(last) || last === ')')) {
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
    return operations.includes(s);
}

function isBracket(s) {
    return brackets.includes(s);
}

function isNumber(s) {
    return !isOperator(s) && !isBracket(s) && s !== '.';
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