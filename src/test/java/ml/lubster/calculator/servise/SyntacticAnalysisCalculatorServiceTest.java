package ml.lubster.calculator.servise;

import ml.lubster.calculator.exception.ParseException;
import ml.lubster.calculator.service.SyntacticAnalysisCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SyntacticAnalysisCalculatorServiceTest {
    @Autowired
    private MessageSource messageSource;
    private SyntacticAnalysisCalculatorService service = new SyntacticAnalysisCalculatorService(messageSource);

    @Test
    void evaluate_shouldReturnResult() throws ParseException {
        assertEquals(-17D, service.evaluate("-9+(-8)"));
    }
}