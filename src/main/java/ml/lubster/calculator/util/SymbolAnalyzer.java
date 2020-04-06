package ml.lubster.calculator.util;

import ml.lubster.calculator.model.Calculation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class SymbolAnalyzer {
    public static final Map<String, Map<String, String>> SYMBOLS = new HashMap<>(Map.of(
            "OPERATORS", Map.of("division", "/", "multiplication", "*",
                    "plus", "+", "minus", "-"),
            "BRACKETS", Map.of("left", "(", "right", ")"),
            "DELIMITER", Map.of("dot", ".", "comma", ",")));

    public static void transformCalculationExpression(Calculation calculation, String symbol) {
        String last = getLast(calculation.getExpression());

        if (calculation.getStatus().equals(Calculation.CalculationStatus.DONE)) {
            if (!isOperator(symbol)) {
                calculation.reset();
            }
        }

        if (isDelimiter(symbol) && (isBracket(last) || isOperator(last) || last.equals(""))) {
            calculation.setExpression(calculation.getExpression().concat("0"));
        } else if (!isNumber(symbol) && !isLeftBracket(symbol) && isOperator(last)) {
            calculation.setExpression(calculation.getExpression().substring(0, calculation.getExpression().length() - 1));
        } else if (isLeftBracket(symbol) && isNumber(last)) {
            calculation.setExpression(calculation.getExpression().concat("*"));
        }
    }

    public static boolean isDivision(String symbol) {
        return symbol.equals(SYMBOLS.get("OPERATORS").get("division"));
    }

    public static boolean isMultiplication(String symbol) {
        return symbol.equals(SYMBOLS.get("OPERATORS").get("multiplication"));
    }

    public static boolean isPlus(String symbol) {
        return symbol.equals(SYMBOLS.get("OPERATORS").get("plus"));
    }

    public static boolean isMinus(String symbol) {
        return symbol.equals(SYMBOLS.get("OPERATORS").get("minus"));
    }

    public static boolean isLeftBracket(String symbol) {
        return symbol.equals(SYMBOLS.get("BRACKETS").get("left"));
    }

    public static boolean isRightBracket(String symbol) {
        return symbol.equals(SYMBOLS.get("BRACKETS").get("right"));
    }

    public static boolean isOperator(String symbol) {
        return SYMBOLS.get("OPERATORS").values().stream()
                .anyMatch(s -> s.equals(symbol));
    }

    public static boolean isBracket(String symbol) {
        return SYMBOLS.get("BRACKETS").values().stream()
                .anyMatch(s -> s.equals(symbol));
    }

    public static boolean isDelimiter(String symbol) {
        return SYMBOLS.get("DELIMITER").values().stream()
                .anyMatch(s -> s.equals(symbol));
    }

    public static boolean isNumber(String symbol) throws NumberFormatException {
        if (isOperator(symbol) || isBracket(symbol) || isDelimiter(symbol) || symbol.equals("")) {
            return false;
        } else {
            try {
                Double.parseDouble(symbol);
            } catch (NumberFormatException e) {
                throw new NumberFormatException();
            }
            return true;
        }
    }

    public static String getLast(String expression) {
        if (expression.isEmpty()) {
            return "";
        } else if (expression.length() == 1) {
            return expression;
        } else {
            return expression.substring(expression.length() - 1);
        }
    }

    public static String getFirst(String expression) {
        return expression.substring(0, 1);
    }
}