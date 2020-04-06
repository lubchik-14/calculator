package ml.lubster.calculator.validator;

import static ml.lubster.calculator.util.SymbolAnalyzer.*;

public class InputSymbolValidator {

    public static boolean isAllowed(String expression, String symbol) {
        String last = getLast(expression);

        if (isDelimiter(symbol)) {
            if (isNumber(last)) {
                for (int i = expression.toCharArray().length - 1; i >= 0; i--) {
                    String ch = String.valueOf(expression.charAt(i));
                    if (isOperator(ch) || isBracket(ch)) {
                        return true;
                    } else if (isDelimiter(ch)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else if (isOperator(symbol)) {
            if (isNumber(last) || isRightBracket(last)) {
                return true;
            } else
            return (isMinus(symbol) && (last.equals("") || isLeftBracket(last))) ||
                    ((isMinus(symbol) || isPlus(symbol)) && last.equals(""));
        } else if (isBracket(symbol)) {
            if (isLeftBracket(symbol)) {
                return isLeftBracket(last) || isOperator(last) || last.equals("");
            } else if (isRightBracket(symbol)) {
                int leftBrakes = expression.split("\\(").length - 1;
                int rightBrakes = expression.split("\\)").length - 1;
                return ((leftBrakes - rightBrakes) > 0) && (isNumber(last) || isRightBracket(last));
            }
        } else if (isNumber(symbol)) {
            return isNumber(last) || isOperator(last) || isLeftBracket(last) || isDelimiter(last) || last.equals("");
        }
        return false;
    }
}
