package br.com.willianantunes.controllers;

import br.com.willianantunes.services.ProductService;
import br.com.willianantunes.services.dtos.ProductCreateDTO;
import br.com.willianantunes.services.dtos.ProductDTO;
import br.com.willianantunes.services.dtos.ProductUpdateDTO;
import br.com.willianantunes.support.ScenarioBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.willianantunes.controllers.components.UrlBuilder.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ScenarioBuilder scenarioBuilder;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return a list of products bound to a specific user by his id")
    public void a() throws Exception {

        String fakeUserId = "fake-id";

        when(productService.getAllProductsFromUser(eq(fakeUserId))).thenReturn(scenarioBuilder.getMockedProductDTOs());

        mockMvc.perform(get(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_PRODUCT_GET_ALL_FROM_USER).expand(fakeUserId)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        verify(productService).getAllProductsFromUser(eq(fakeUserId));
    }

    @Test
    @DisplayName("Should return an product by its id")
    public void b() throws Exception {

        String fakeProductId = "fake-id";

        when(productService.getProductById(eq(fakeProductId)))
            .thenReturn(Optional.of(scenarioBuilder.getMockedProductDTOs().stream().findAny().orElseThrow()));

        mockMvc.perform(get(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_PRODUCT_GET_OR_DELETE).expand(fakeProductId)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        verify(productService).getProductById(eq(fakeProductId));
    }

    @Test
    @DisplayName("Should return 404 when no product is found by its id")
    public void c() throws Exception {

        String fakeProductId = "fake-id";

        when(productService.getProductById(eq(fakeProductId))).thenReturn(Optional.empty());

        mockMvc.perform(get(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_PRODUCT_GET_OR_DELETE).expand(fakeProductId)))
            .andDo(print())
            .andExpect(status().isNotFound());

        verify(productService).getProductById(eq(fakeProductId));
    }

    @Test
    @DisplayName("Should return 201 when a new product is bound with an user")
    public void d() throws Exception {

        String fakeUserId = "fake-id";

        ProductCreateDTO productCreateDTO = ProductCreateDTO.builder()
            .code("SAMPLE-CODE")
            .name("SAMPLE-NAME")
            .details("SAMPLE-DETAILS")
            .price(new BigDecimal("15.30"))
            .imageLink("https://img.elo7.com.br/product/main/8D2C19/ursao-ursinho-de-pelucia.jpg")
            .build();

        ProductDTO anyProductDTO = scenarioBuilder.getMockedProductDTOs().stream().findAny().orElseThrow();

        when(productService.bindProductToUser(eq(productCreateDTO), eq(fakeUserId))).thenReturn(anyProductDTO);

        mockMvc.perform(post(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_PRODUCT_POST).expand(fakeUserId))
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(productCreateDTO)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", equalTo(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_PRODUCT_GET_OR_DELETE).expand(anyProductDTO.getId()).toString())));

        verify(productService).bindProductToUser(eq(productCreateDTO), eq(fakeUserId));
    }

    @Test
    @DisplayName("Should return 200 when an updated is executed in a product's properties")
    public void f() throws Exception {

        ProductUpdateDTO productUpdateDTO = ProductUpdateDTO.builder()
            .id("fake-id")
            .code("SAMPLE-CODE")
            .name("SAMPLE-NAME")
            .details("SAMPLE-DETAILS")
            .price(new BigDecimal("15.30"))
            .imageLink("https://img.elo7.com.br/product/main/8D2C19/ursao-ursinho-de-pelucia.jpg")
            .build();

        mockMvc.perform(put(REQUEST_PATH_API + REQUEST_PATH_PRODUCT_PUT)
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(productUpdateDTO)))
            .andDo(print())
            .andExpect(status().isOk());

        verify(productService).updateProductFromUser(eq(productUpdateDTO));
    }

    @Test
    @DisplayName("Should return 200 when the product is deleted")
    public void h() throws Exception {

        String fakeProductId = "fake-id";

        mockMvc.perform(delete(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_PRODUCT_GET_OR_DELETE).expand(fakeProductId)))
            .andDo(print())
            .andExpect(status().isOk());

        verify(productService).deleteProductFromUser(eq(fakeProductId));
    }
}
