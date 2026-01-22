package com.elliekavanagh.rummikub.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void validateRun_withJoker_returnsValidTrue() throws Exception {
        String body = "{\n" +
                "  \"type\": \"RUN\",\n" +
                "  \"tiles\": [\n" +
                "    {\"color\":\"RED\",\"value\":5,\"joker\":false},\n" +
                "    {\"joker\":true},\n" +
                "    {\"color\":\"RED\",\"value\":7,\"joker\":false}\n" +
                "  ]\n" +
                "}";

        mockMvc.perform(post("/api/v1/melds/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.valid").value(true));
    }
}
