package ml.lubster.calculator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.lubster.calculator.model.Calculator;
import ml.lubster.calculator.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CalculatorController {
    private final CalculatorService service;

    @PostMapping("/calculator")
    @ResponseBody
    public ResponseEntity<Calculator> show() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}