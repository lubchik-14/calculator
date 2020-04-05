package ml.lubster.calculator.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ml.lubster.calculator.exception.ParseException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SyntacticAnalysisCalculatorService implements CalculatorService {
    private final MessageSource messageSource;
    private Token currentToken;
    private int currentIndex;

    private enum TokenType {
        NONE, DELIMITER, NUMBER
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    private class Token {
        private String value = "";
        private TokenType tokenType = TokenType.NONE;
    }

    public double evaluate(@NotNull(message = "{invalid.no-expression}") String exp) throws ParseException {
        double result;
        currentIndex = 0;
        getNextToken(exp);
        if (isEnd(currentToken))
            throw new ParseException(messageSource.getMessage("invalid.no-expression",null, Locale.getDefault()));
        result = AddOrSub(exp);
        if (!isEnd(currentToken))
            throw new ParseException(messageSource.getMessage("invalid.syntax", null, Locale.getDefault()));
        return result;
    }

    private void getNextToken(String exp) {
        this.currentToken = new Token();
        if (currentIndex == exp.length()) {
            setEnd(currentToken);
            return;
        }

        while (currentIndex < exp.length() && Character.isWhitespace(exp.charAt(currentIndex))) {
            ++currentIndex;
        }

        if (currentIndex == exp.length()) {
            setEnd(currentToken);
            return;
        }

        if (isDelimiter(exp.charAt(currentIndex))) {
            currentToken.setValue(currentToken.getValue() + exp.charAt(currentIndex));
            currentIndex++;
            currentToken.setTokenType(TokenType.DELIMITER);
        } else if (Character.isDigit(exp.charAt(currentIndex))) {
            while (!isDelimiter(exp.charAt(currentIndex))) {
                currentToken.setValue(currentToken.getValue() + exp.charAt(currentIndex));
                currentIndex++;
                if (currentIndex >= exp.length()) {
                    break;
                }
            }
            currentToken.setTokenType(TokenType.NUMBER);
        } else {
            setEnd(currentToken);
        }
    }

    private double AddOrSub(String exp) throws ParseException {
        double result;
        double part;
        result = MulOrDev(exp);
        char at;
        while ((at = currentToken.getValue().charAt(0)) == '+' ||
                at == '-') {
            getNextToken(exp);
            part = MulOrDev(exp);
            switch (at) {
                case '-':
                    result -= part;
                    break;
                case '+':
                    result += part;
            }
        }
        return result;
    }

    private double MulOrDev(String exp) throws ParseException {
        double result;
        double part;
        char at;
        result = defineUnary(exp);
        while ((at = currentToken.getValue().charAt(0)) == '*' || at == '/') {
            getNextToken(exp);
            part = defineUnary(exp);
            switch (at) {
                case '*':
                    result *= part;
                    break;
                case '/':
                    if (part == 0.0)
                        throw new ParseException(messageSource.getMessage("invalid.zero", null, Locale.getDefault()));
                    result /= part;
            }
        }
        return result;
    }

    private double defineUnary(String exp) throws ParseException {
        double result;
        String at = " ";
        if ((currentToken.getTokenType().equals(TokenType.DELIMITER)) && currentToken.getValue().equals("+") ||
                currentToken.getValue().equals("-")) {
            at = currentToken.getValue();
            getNextToken(exp);
        }
        result = processBrackets(exp);
        if (at.equals("-"))
            result = -result;
        return result;
    }

    private double processBrackets(String exp) throws ParseException {
        double result;
        if (currentToken.getValue().equals("(")) {
            getNextToken(exp);
            result = AddOrSub(exp);
            if (!currentToken.getValue().equals(")"))
                throw new ParseException(messageSource.getMessage("invalid.parentheses", null, Locale.getDefault()));
            getNextToken(exp);
        } else
            result = getNumber(exp);
        return result;
    }

    private double getNumber(String exp) throws ParseException {
        double result;
        if (currentToken.getTokenType() == TokenType.NUMBER) {
            try {
                result = Double.parseDouble(currentToken.getValue());
            } catch (NumberFormatException exc) {
                throw new ParseException(messageSource.getMessage("invalid.syntax", null, Locale.getDefault()));
            }
            getNextToken(exp);
        } else {
            throw new ParseException(messageSource.getMessage("invalid.syntax", null, Locale.getDefault()));
        }
        return result;
    }

    private boolean isDelimiter(char charAt) {
        return (" +-/*=()".indexOf(charAt)) != -1;
    }

    private boolean isEnd(Token token) {
        return token.getValue().equals("\0");
    }

    private void setEnd(Token token) {
        token.setValue("\0");
    }
}