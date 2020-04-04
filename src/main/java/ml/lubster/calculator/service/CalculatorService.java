package ml.lubster.calculator.service;

import ml.lubster.calculator.exception.ParseException;
import ml.lubster.calculator.model.Calculation;

public interface CalculatorService {
    Calculation evaluate(String expression) throws ParseException;
}
