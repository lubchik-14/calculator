package ml.lubster.calculator.controller;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.model.Calculator;
import ml.lubster.calculator.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService service;

    @GetMapping(value = "/calculator")
    @ResponseBody
    public ModelAndView getCalculator(@RequestParam Map<String,String> allParams, ModelAndView model) {
        System.out.println("all params " + allParams.toString());
        ModelAndView modelAndView = new ModelAndView("calculator", new HashMap<>(Map.of("ottr", "ottr")), HttpStatus.OK);
        if (allParams.size() != 0) {
            String exp = allParams.get("exp");
            service.setExpression(exp);
            modelAndView.addObject("expression", service.getExpression());
            modelAndView.addObject("result", service.calculate());
        }
        return modelAndView;
    }

    @GetMapping("/")
    public String getHomePage(){
        return "redirect:/calculator";
    }
}