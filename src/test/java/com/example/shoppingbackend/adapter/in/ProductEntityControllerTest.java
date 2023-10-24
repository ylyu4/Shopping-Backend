package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.application.port.in.GetProductListUseCase;
import com.example.shoppingbackend.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
public class ProductEntityControllerTest {

    @MockBean
    private GetProductListUseCase getProductListUseCase;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void should_get_all_product_list_successfully() throws Exception {

        doReturn(List.of(new Product(1L, "product1", 0.3, 10.0))).when(getProductListUseCase).getProductList();

        mockMvc.perform(get("/product/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name", containsString("product1")))
                .andExpect(jsonPath("$.[0].discountRate", is(0.3)))
                .andExpect(jsonPath("$.[0].originalPrice", is(10.0)))
                .andExpect(jsonPath("$.[0].finalPrice", is(3.0)));
    }
}
