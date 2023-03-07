package com.nullsca.clirunner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CommandLineRunnerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRunCommand() throws Exception {
        String command = "echo hello world"; // no quotes - os independent
        String expectedOutput = "hello world" + System.lineSeparator();
        String expectedRC = "0";
        mockMvc.perform(MockMvcRequestBuilders.post("/nullsca/wscli/v1/run")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\"" + command + "\"}"))
                .andDo(result -> System.out.println("Test 0 Produced Result:" + result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.output").value(expectedOutput))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rc").value(expectedRC));
    }

    @Test
    public void testMissingCommandParameter() throws Exception {
        String expectedOutput = "Command parameter not supplied";
        mockMvc.perform(MockMvcRequestBuilders.post("/nullsca/wscli/v1/run")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(result -> System.out.println("Test 1 Produced Result:" + result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(expectedOutput));
    }

    @Test
    public void testEmptyCommand0() throws Exception {
        String expectedOutput = "Command parameter not supplied";
        mockMvc.perform(MockMvcRequestBuilders.post("/nullsca/wscli/v1/run")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\"\"}"))
                .andDo(result -> System.out.println("Test 2 Produced Result:" + result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(expectedOutput));
    }

    @Test
    public void testEmptyCommand1() throws Exception {
        String expectedOutput = "Command parameter not supplied";
        mockMvc.perform(MockMvcRequestBuilders.post("/nullsca/wscli/v1/run")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\" \"}"))
                .andDo(result -> System.out.println("Test 3 Produced Result:" + result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(expectedOutput));
    }

    @Test
    public void testInvalidCommand() throws Exception {
        String command = "invalidcommand";
        mockMvc.perform(MockMvcRequestBuilders.post("/nullsca/wscli/v1/run")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\"" + command + "\"}"))
                .andDo(result -> System.out.println("Test 4 Produced Result:" + result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
