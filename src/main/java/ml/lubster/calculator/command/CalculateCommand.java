package ml.lubster.calculator.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CalculateCommand implements Command {

    @Setter
    private String expression;
    @Getter
    private Map<String, Double> current = new HashMap<>();

    @Override
    public void execute() {
        undoStack.push(current);
    }

    @Override
    public void unExecute() {
        redoStack.push(current);
    }
}
