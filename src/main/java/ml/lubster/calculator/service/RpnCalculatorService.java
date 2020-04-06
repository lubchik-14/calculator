package ml.lubster.calculator.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static ml.lubster.calculator.util.SymbolAnalyzer.SYMBOLS;
import static ml.lubster.calculator.util.SymbolAnalyzer.isNumber;

@Service
@Validated
public class RpnCalculatorService implements CalculatorService {
    public final static Map<String, Integer> OPERATORS = new HashMap<>(Map.of(
            SYMBOLS.get("OPERATORS").get("plus"), 2,
            SYMBOLS.get("OPERATORS").get("minus"), 2,
            SYMBOLS.get("OPERATORS").get("multiplication"), 3,
            SYMBOLS.get("OPERATORS").get("division"), 3,
            SYMBOLS.get("BRACKETS").get("left"), 1,
            SYMBOLS.get("BRACKETS").get("right"), -1));

    public double evaluate(@NotNull(message = "{invalid.no-expression}") String expression) {
        return rpnToResult(expressionToRpn(expression));
    }

    private String expressionToRpn(String expression) {
        String rpn = "";
        String[] tokens = getTokens(expression);
        Stack<String> stack = new Stack<>();
        int priority;
        for (String token : tokens) {
            priority = getTokenPriority(token);

            if (priority == 0) {
                rpn = rpn + token;
                continue;
            }

            if (priority == 1) {
                stack.push(token);
                continue;
            }

            if (priority > 1) {
                rpn = rpn + " ";
                while (!stack.empty()) {
                    if (getTokenPriority(stack.peek()) >= priority) {
                        rpn = rpn + stack.pop();
                    } else {
                        break;
                    }
                }
                stack.push(token);
                continue;
            }
            if (priority == -1) {
                rpn = rpn + " ";
                while (getTokenPriority(stack.peek()) != 1) {
                    rpn = rpn + stack.pop();
                }
                stack.pop();
            }
        }
        while (!stack.empty()) {
            rpn = rpn + stack.pop();
        }
        return rpn;
    }

    private double rpnToResult(@NotNull(message = "{invalid.no-expression}") String rpn) {
        String[] tokens = getTokens(rpn);
        Stack<Double> stack = new Stack<>();
        int priority;
        for (String token : tokens) {
            priority = getTokenPriority(token);
            if (priority == 0) {
                stack.push(Double.parseDouble(token));
                continue;
            }

            if (priority > 1) {
                double a = stack.pop();
                double b = stack.pop();
                double result = 0;
                switch (token) {
                    case "+": {
                        result = b + a;
                        break;
                    }
                    case "-": {
                        result = b - a;
                        break;
                    }
                    case "*": {
                        result = b * a;
                        break;
                    }
                    case "/": {
                        result = b / a;
                        break;
                    }
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    private String[] getTokens(@NotNull(message = "{invalid.no-expression}")
                               @NotEmpty(message = "{invalid.no-expression}") String string) {
        return Arrays.stream(string.split("(?<=[-+() */])|(?=[-+() */])"))
                .filter(s -> !s.equals(" "))
                .toArray(String[]::new);
    }

    private int getTokenPriority(String token) {
        if (!isNumber(token)) {
            return OPERATORS.get(token);
        }
        return 0;
    }
}