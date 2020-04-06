package ml.lubster.calculator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void calculate_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/calculate")
                .param("exp", "15-76")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(".calculation.status").value("DONE"))
                .andExpect(jsonPath(".error").value(""));
    }

    @Test
    void calculate_shouldReturnBedRequest() throws Exception {
        mockMvc.perform(get("/calculate")
                .param("exp", "15-")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(".calculation.status").value("ERROR"))
                .andExpect(jsonPath(".error").value("Syntax Error"));
    }

    @Test
    void calculate_shouldReturnDivisionByZero() throws Exception {
        mockMvc.perform(get("/calculate")
                .param("exp", "15/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(".calculation.status").value("ERROR"))
                .andExpect(jsonPath(".error").value("Division by Zero"));
    }
}