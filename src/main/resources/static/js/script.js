const operations = ['/', '*', '+', '-'];
const brackets = ['(', ')'];

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
    return document.getElementById('exp').innerText;
}

function setExp(newText) {
    return document.getElementById('exp').innerText = newText;
}

function getValue() {
    return document.getElementById('value').value;
}

function setValue(newValue) {
    document.getElementById('value').value = newValue;
}

function addSymbol(symbol) {
    let exp = getExp();
    if (isAllowedAdding(symbol, exp)) {
        let value = exp + symbol;
        setValue(calculate(value));
        setExp(value);
    }
}

function isAllowedAdding(symbol, exp) {
    let last = exp.substring(exp.length - 1);

    if (symbol === '.') {
        if (isNumber(last)) {
            return true;
        }
    } else if (isOperator(symbol)) {
        if (isNumber(last) || last === ')') {
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
        if (isNumber(last) || isOperator(last) || last === '(' || last === '') {
            return true;
        }
    }
    return false;
}

function clean() {
    if (getExp() === null || getValue() === "") {
        setExp("");
    }
    setValue("");
}

function calculate(exp) {
    try {
        return eval(exp);
    } catch (e) {
        return getValue();
    }
}

