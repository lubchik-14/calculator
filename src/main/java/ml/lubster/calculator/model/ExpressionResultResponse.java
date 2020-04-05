package ml.lubster.calculator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class ExpressionResultResponse {
    private final Calculation calculation;
    @Setter
    private String error;
}
