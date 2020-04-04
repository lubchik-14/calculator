package ml.lubster.calculator.service;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class CalculatorService {
    @Setter
    @Getter
    private String expression;
    public final static Map<String, Integer> OPERATORS = new HashMap<>(Map.of(
            "+", 2,
            "-", 2,
            "*", 3,
            "/", 3,
            "(", 1,
            ")", -1));

    public double calculate() {
        return rpnToResult(expressionToRpn());
    }

    private String expressionToRpn() {
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

    private double rpnToResult(@NonNull String rpn) {
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
                        result = a + b;
                        break;
                    }
                    case "-": {
                        result = a - b;
                        break;
                    }
                    case "*": {
                        result = a * b;
                        break;
                    }
                    case "/": {
                        result = a / b;
                        break;
                    }
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    private String[] getTokens(@NonNull String string) {
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

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}