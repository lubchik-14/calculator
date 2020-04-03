package ml.lubster.calculator.service;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.command.Command;
import ml.lubster.calculator.store.Storage;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Stack;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private final Command calculateCommand;
    private Stack<Map<String, Double>> undoStack;
    private Stack<Map<String, Double>> redoStack;

    public void calculateWasPushed() {
        calculateCommand.execute();
        undoStack.push()
    }

    public void undoButtonWasPushed() {
        undoCommand.undo();
    }

    public void redoButtonWasPushed() {
        redoCommand.undo();
    }

}