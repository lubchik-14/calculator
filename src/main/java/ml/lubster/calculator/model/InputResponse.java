package ml.lubster.calculator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class InputResponse {
    private final Calculation calculation;
    private boolean allowed;
}