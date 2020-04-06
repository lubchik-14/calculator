package ml.lubster.calculator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Calculation {
    private String expression = "";
    private double result;
    private CalculationStatus status = CalculationStatus.NEW;

    public enum CalculationStatus {
        NEW, IN_PROGRESS, DONE, ERROR
    }

    public String resultAsString() {
        if (result == (long) result)
            return String.format("%d", (long) result);
        else
            return String.format("%s", result);
    }
}