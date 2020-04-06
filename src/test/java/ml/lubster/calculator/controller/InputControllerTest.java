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
public class InputControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addSymbol_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/add")
                .param("symbol", "-")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(".calculation.expression").value("-"))
                .andExpect(jsonPath(".calculation.status").value("IN_PROGRESS"));
    }

    @Test
    void addSymbol_shouldReturnBedRequest() throws Exception {
        mockMvc.perform(get("/add")
                .param("symbol", "*")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(".calculation.expression").value(""))
                .andExpect(jsonPath(".calculation.status").value("NEW"));
    }
}