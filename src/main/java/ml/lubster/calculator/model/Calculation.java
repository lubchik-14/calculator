package ml.lubster.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Calculation {
    private String expression;
    private double result;
}

