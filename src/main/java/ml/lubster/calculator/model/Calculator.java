package ml.lubster.calculator.model;

import java.util.Map;
import java.util.Stack;

public class Calculator {
    private final Stack<Map<String, Double>> undoStack = new Stack<>();
    private final Stack<Map<String, Double>> redoStack = new Stack<>();
    private Map<String, Double> current;
}
