package br.com.willianantunes.configuration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.willianantunes.controllers.components.UrlBuilder.REQUEST_PATH_API;
import static br.com.willianantunes.controllers.components.UrlBuilder.REQUEST_PATH_USER_GET_ALL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilterConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should CORS configuration allow request from another origin")
    public void a() throws Exception {

        mockMvc.perform(options(REQUEST_PATH_API + REQUEST_PATH_USER_GET_ALL)
            .header("Access-Control-Request-Method", "GET")
            .header("Origin", "http://www.salt-shaker.com"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string("Access-Control-Allow-Methods", Matchers.equalTo("GET")));
    }
}