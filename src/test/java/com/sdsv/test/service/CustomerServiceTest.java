package com.sdsv.test.service;

import com.sdsv.test.model.Customer;
import com.sdsv.test.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void should_return_customer_exists_message_for_duplicate_email() {
        Customer existingCustomer = Customer.builder().name("Minh Dat").number("C001").email("dat@gmail.com").build();
        when(customerRepository.getCustomerByEmail("dat@gmail.com")).thenReturn(existingCustomer);

        Customer newCustomer = Customer.builder().name("Dinh Dat").number("C002").email("dat@gmail.com").build();
        String result = customerService.addCustomer(newCustomer);

        assertThat(result).isEqualTo("Customer with this email already exists.");
    }

    @Test
    public void should_return_successful_message_when_customer_added() {
        Customer newCustomer = Customer.builder().name("Dinh Dat").number("C002").email("ddat@gmail.com").build();
        when(customerRepository.getCustomerByEmail("ddat@gmail.com")).thenReturn(null);
        when(customerRepository.addCustomer(newCustomer)).thenReturn(true);

        String result = customerService.addCustomer(newCustomer);

        assertThat(result).isEqualTo("Customer added successfully.");
    }

    @Test
    public void should_return_null_for_non_existing_customer() {
        when(customerRepository.getCustomerByEmail("nonecustomer@gmail.com")).thenReturn(null);

        Customer result = customerService.getCustomer("nonecustomer@gmail.com");

        assertThat(result).isNull();
    }

    @Test
    public void should_return_customer_when_exists() {
        Customer existingCustomer = Customer.builder().name("Minh Dat").number("C001").email("dat@gmail.com").build();
        when(customerRepository.getCustomerByEmail("dat@gmail.com")).thenReturn(existingCustomer);

        Customer result = customerService.getCustomer("dat@gmail.com");

        assertThat(result).isEqualTo(existingCustomer);
    }
}
