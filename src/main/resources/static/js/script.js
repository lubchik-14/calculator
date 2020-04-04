const SYMBOLS = {
    operations: ['/', '*', '+', '-'],
    brackets: ['(', ')']
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
            renewCalculation(false);
        }
        value = getExp() + symbol;
        setValue(value);
        setExp(value);
    }
}

function clean() {
    setExp("");
    setValue("");
    renewCalculation(true);
}

function renewCalculation(bol) {
    newCalculation = bol;
    if (bol) {
        document.getElementById('equal').setAttribute('disabled', 'disabled');
    } else {
        document.getElementById('equal').removeAttribute('disabled');
    }
}

function equal() {
    renewCalculation(true);
}

function transformIfNecessary(symbol) {
    let last = getExp().substring(getExp().length - 1);

    if (symbol === '.') {
        if (isBracket(last) || isOperator(last) || last === "") {
            setExp(getExp() + '0');
            setValue(getValue() + '0');
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
                setExp(getExp() + '*');
                setValue(getValue() + '*');
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