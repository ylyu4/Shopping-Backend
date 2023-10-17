package com.example.shoppingbackend.adapter.in;

import com.example.shoppingbackend.application.port.in.GetCustomerProfileUseCase;
import com.example.shoppingbackend.domain.Customer;
import com.example.shoppingbackend.exception.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
public class CustomerEntityControllerTest {

    @MockBean
    private GetCustomerProfileUseCase customerUseCase;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void should_find_customer_profile_successfully() throws Exception {

        doReturn(new Customer(1L, "Jack")).when(customerUseCase).getCustomerProfile(1L);

        mockMvc.perform(get("/customer/profile/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", containsString("Jack")));
    }

    @Test
    void should_throw_customer_not_found_successfully() throws Exception {

        doThrow(new CustomerNotFoundException("not found")).when(customerUseCase).getCustomerProfile(1L);

        mockMvc.perform(get("/customer/profile/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("not found")));
    }
}