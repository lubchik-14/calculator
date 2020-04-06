package ml.lubster.calculator.controller;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.model.Calculation;
import ml.lubster.calculator.model.InputResponse;
import ml.lubster.calculator.service.InputService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class InputController {
    private final InputService inputService;
    private final Calculation calculation;

    @GetMapping("/add")
    public ResponseEntity<InputResponse> addSymbol(@RequestParam Map<String, String> allParams) {
        if (calculation.getStatus().equals(Calculation.CalculationStatus.DONE)) {
            calculation.setExpression(calculation.resultAsString());
            calculation.setResult(0);
        }
        InputResponse response = new InputResponse(calculation);
        if (!allParams.isEmpty()) {
            String symbol = allParams.get("symbol");
            if (!inputService.isAllowedAdding(symbol)) {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        response.setAllowed(true);
        calculation.setStatus(Calculation.CalculationStatus.IN_PROGRESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
