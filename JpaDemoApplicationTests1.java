package com.javatechie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.controller.ProductController;
import com.javatechie.entity.Product;
import com.javatechie.service.ProductService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class JpaDemoApplicationTests {

    private static final String ENDPOINT_URL = "/products";

    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.productController).build();
    }

    @Test
    public void addProductTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(ENDPOINT_URL)
                        .content(asJsonString(new Product("Mobile", 30000, "samsung galaxy", "electronics")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void shouldReturnAllProductsFromDB() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*.id").isNotEmpty());
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("duke"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("duke@spring.io"));
    }

    @Test
    public void getProductsById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ENDPOINT_URL + "/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }


    @Test
    public void updateProductTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put(ENDPOINT_URL + "/{id}", 4)
                        .content(asJsonString(new Product("Mobile", 20000, "samsung galaxy", "electronics")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(20000));
    }


    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL+"/{id}", 5)
                )
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
