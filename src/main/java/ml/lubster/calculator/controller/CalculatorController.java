package ml.lubster.calculator.controller;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.exception.ParseException;
import ml.lubster.calculator.model.Calculation;
import ml.lubster.calculator.model.ExpressionResultResponse;
import ml.lubster.calculator.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
public class CalculatorController {
    private final CalculatorService syntacticAnalysisCalculatorService;
    private final Calculation calculation;

    @GetMapping(value = "/calculator")
    @ResponseBody
    public ModelAndView getCalculator(@RequestParam Map<String, String> allParams) {
        return new ModelAndView("calculator", HttpStatus.OK);
    }

    @GetMapping("/calculate")
    public ResponseEntity<ExpressionResultResponse> calculate(@RequestParam Map<String, String> allParams) {
        ExpressionResultResponse response = new ExpressionResultResponse(calculation);
        if (!allParams.isEmpty()) {
            try {
                calculation.setExpression(allParams.get("exp"));
                calculation.setResult(syntacticAnalysisCalculatorService.evaluate(allParams.get("exp")));
            } catch (ParseException e) {
                System.err.println("Exception occurred: " + e);
                e.printStackTrace();
                response.setError(e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}