package com.sdsv.test.controller;

import com.sdsv.test.model.Customer;
import com.sdsv.test.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Test
    public void should_return_customer_info_in_json_format() {
        Customer fakeCustomer = Customer.builder().name("Minh Dat").number("C001").email("dat@gmail.com").build();
        when(customerService.getCustomer("dat@gmail.com")).thenReturn(fakeCustomer);

        ResponseEntity<Customer> response = customerController.getCustomer("dat@gmail.com");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        Customer result = response.getBody();

        assertThat(result.getName()).isEqualTo("Minh Dat");
        assertThat(result.getNumber()).isEqualTo("C001");
        assertThat(result.getEmail()).isEqualTo("dat@gmail.com");
    }

    @Test
    public void should_return_not_found_for_non_existing_customer() {
        when(customerService.getCustomer("nonecustomer@gmail.com")).thenReturn(null);

        ResponseEntity<Customer> response = customerController.getCustomer("nonecustomer@gmail.com");

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
