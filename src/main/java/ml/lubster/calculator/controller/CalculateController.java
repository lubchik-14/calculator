package ml.lubster.calculator.controller;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.exception.ParseException;
import ml.lubster.calculator.model.Calculation;
import ml.lubster.calculator.model.ExpressionResultResponse;
import ml.lubster.calculator.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CalculateController {
    private final Calculation calculation;
    private final CalculatorService syntacticAnalysisCalculatorService;

    @GetMapping("/calculate")
    public ResponseEntity<ExpressionResultResponse> calculate(@RequestParam Map<String, String> allParams) {
        ExpressionResultResponse resultResponse = new ExpressionResultResponse(calculation);
        if (!allParams.isEmpty()) {
            try {
                calculation.setExpression(allParams.get("exp"));
                calculation.setResult(syntacticAnalysisCalculatorService.evaluate(allParams.get("exp")));
                resultResponse.setError("");
                calculation.setStatus(Calculation.CalculationStatus.DONE);
            } catch (ParseException e) {
                calculation.setStatus(Calculation.CalculationStatus.ERROR);
                System.err.println("Exception occurred: " + e);
                e.printStackTrace();
                resultResponse.setError(e.getMessage());
                return new ResponseEntity<>(resultResponse, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(resultResponse, HttpStatus.OK);
    }
}
