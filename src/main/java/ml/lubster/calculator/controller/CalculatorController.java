package ml.lubster.calculator.controller;

import ml.lubster.calculator.model.Calculator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CalculatorController {
//    private final CalculatorService service;

    @GetMapping(value = "/calculator")
    @ResponseBody
    public ModelAndView getCalculator(@RequestParam Map<String,String> allParams) {
        System.out.println("all params " + allParams.toString());
        Calculator calculator = new Calculator();
        calculator.setExpression("4+5");
        calculator.setResult(12);

        ModelAndView modelAndView = new ModelAndView("calculator",
                new HashMap<>(Map.of("calc", calculator, "1", "1")),
                HttpStatus.OK);
        modelAndView.addObject("ottr", "ottr");
        return modelAndView;
    }
}