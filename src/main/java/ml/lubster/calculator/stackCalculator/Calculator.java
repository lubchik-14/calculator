package ml.lubster.calculator.stackCalculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator {

    public static Map<String, Integer> OPERATORS = new HashMap<>(Map.of(
            "+", 2,
            "-", 2,
            "*", 3,
            "/", 3,
            "(", 1,
            ")", -1));

    public static String[] getTokens(String string) {
        return Arrays.stream(string.split("(?<=[-+() */])|(?=[-+() */])"))
                .filter(s -> !s.equals(" "))
                .toArray(String[]::new);
    }

    public static String expressionToRpn(String exp) {
        String rpn = "";
        String[] tokens = getTokens(exp);
        Stack<String> stack = new Stack<>();
        int priority;
        for (String token : tokens) {
            priority = getPriority(token);

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
                    if (getPriority(stack.peek()) >= priority) {
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
                while (getPriority(stack.peek()) != 1) {
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

    public static double RpnToResult(String rpn) {
        String operand = "";
        String[] tokens = getTokens(rpn);
        Stack<Double> stack = new Stack<>();
        int priority;
        for (String token : tokens) {
            priority = getPriority(token);
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
                        result = add(a, b);
                        break;
                    }
                    case "-": {
                        result = sub(a, b);
                        break;
                    }
                    case "*": {
                        result = mul(a, b);
                        break;
                    }
                    case "/": {
                        result = dev(a, b);
                        break;
                    }
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    private static double add(double a, double b) {
        return a + b;
    }


    private static double sub(double a, double b) {
        return a - b;
    }


    private static double mul(double a, double b) {
        return a * b;
    }

    private static double dev(double a, double b) {
        return a / b;
    }

    private static int getPriority(String token) {
        if (!isNumber(token)) {
            return OPERATORS.get(token);
        }
        return 0;
    }

    private static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
