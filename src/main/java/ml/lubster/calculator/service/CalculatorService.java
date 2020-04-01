package ml.lubster.calculator.service;

import lombok.RequiredArgsConstructor;
import ml.lubster.calculator.store.Storage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private final Storage storage;


}