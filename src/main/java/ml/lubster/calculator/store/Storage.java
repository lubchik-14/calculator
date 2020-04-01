package ml.lubster.calculator.store;

import lombok.Getter;

import java.util.Stack;

public class Storage {
    @Getter
    private Stack<Integer> stack;

    public Storage() {
        this.stack = new Stack<>();
    }
}
