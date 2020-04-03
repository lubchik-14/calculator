package ml.lubster.calculator.config;

import ml.lubster.calculator.model.Calculator;
import ml.lubster.calculator.store.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Storage getStorage() {
        Storage storage = new Storage();
        System.out.println("Getting storage from container " + storage.getStack());
        return storage;
    }

    @Bean
    public Calculator getCalculator() {
        return new Calculator();
    }
}
