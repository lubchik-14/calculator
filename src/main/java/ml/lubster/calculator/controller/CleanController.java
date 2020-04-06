package ml.lubster.calculator.controller;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.model.Calculation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CleanController {
    private final Calculation calculation;

    @GetMapping("/clean")
    public String clean() {
        calculation.reset();
        return "calculator";
    }
}
