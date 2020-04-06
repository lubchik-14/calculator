package ml.lubster.calculator.controller;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.model.Calculation;
import ml.lubster.calculator.model.ExpressionResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
public class CalculatorController {
    private final Calculation calculation;

    @GetMapping(value = "/calculator")
    @ResponseBody
    public ModelAndView getCalculator() {
        return new ModelAndView("calculator",
                Map.of("result", new ExpressionResultResponse(calculation)), HttpStatus.OK);
    }
}