package ml.lubster.calculator.service;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.model.Calculation;
import ml.lubster.calculator.util.SymbolAnalyzer;
import org.springframework.stereotype.Service;

import static ml.lubster.calculator.validator.InputSymbolValidator.isAllowed;

@Service
@RequiredArgsConstructor
public class InputService {
    private final Calculation calculation;

    public boolean isAllowedAdding(String symbol) {
        String oldExpression = calculation.getExpression();
        SymbolAnalyzer.transformCalculationExpression(calculation, symbol);
        if (isAllowed(calculation.getExpression(), symbol)) {
            calculation.setExpression(calculation.getExpression() + symbol);
            return true;
        } else {
            calculation.setExpression(oldExpression);
            return false;
        }
    }
}