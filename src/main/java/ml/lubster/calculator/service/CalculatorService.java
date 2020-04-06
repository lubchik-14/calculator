package ml.lubster.calculator.service;

import ml.lubster.calculator.exception.ParseException;
import org.springframework.stereotype.Service;

@Service
public interface CalculatorService {
    double evaluate(String expression) throws ParseException;
}
