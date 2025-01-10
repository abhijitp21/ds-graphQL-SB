package com.fastlight.demo.datafetchers;

import com.fastlight.demo.model.Account;
import com.fastlight.demo.model.AccountInput;
import com.fastlight.demo.model.Customer;
import com.fastlight.demo.model.CustomerInput;
import com.fastlight.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerDataFetcherTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerDataFetcher customerDataFetcher;

    public CustomerDataFetcherTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomerWithValidInput() {
        // Arrange
        CustomerInput customerInput = CustomerInput.builder()
                .name("John Doe")
                .contact(1234567890L)
                .gender("Male")
                .mail("johndoe@example.com")
                .accounts(Collections.singletonList(AccountInput.builder()
                        .accountNumber(12345L)
                        .accountBalance(1000.0)
                        .accountStatus("Active")
                        .build()))
                .build();

        Customer expectedCustomer = Customer.builder()
                .customerNumber("1001")
                .name("John Doe")
                .contact(1234567890L)
                .gender("Male")
                .mail("johndoe@example.com")
                .accounts(Collections.singletonList(Account.builder()
                        .accountNumber(12345L)
                        .accountBalance(1000.0)
                        .accountStatus("Active")
                        .build()))
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        // Act
        Customer result = customerDataFetcher.createCustomer(customerInput);

        // Assert
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(customerCaptor.capture());

        Customer capturedCustomer = customerCaptor.getValue();
        assertEquals(customerInput.getName(), capturedCustomer.getName());
        assertEquals(customerInput.getContact(), capturedCustomer.getContact());
        assertEquals(customerInput.getGender(), capturedCustomer.getGender());
        assertEquals(customerInput.getMail(), capturedCustomer.getMail());
        assertEquals(1, capturedCustomer.getAccounts().size());
        assertEquals(12345L, capturedCustomer.getAccounts().get(0).getAccountNumber());
        assertEquals(expectedCustomer, result);
    }

    @Test
    void testCreateCustomerWithNoAccounts() {
        // Arrange
        CustomerInput customerInput = CustomerInput.builder()
                .name("Jane Doe")
                .contact(12345L)
                .gender("Female")
                .mail("janedoe@example.com")
                .accounts(Collections.emptyList())
                .build();

        Customer expectedCustomer = Customer.builder()
                .customerNumber("1002")
                .name("Jane Doe")
                .contact(12345L)
                .gender("Female")
                .mail("janedoe@example.com")
                .accounts(Collections.emptyList())
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        // Act
        Customer result = customerDataFetcher.createCustomer(customerInput);

        // Assert
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(customerCaptor.capture());

        Customer capturedCustomer = customerCaptor.getValue();
        assertEquals(customerInput.getName(), capturedCustomer.getName());
        assertEquals(customerInput.getContact(), capturedCustomer.getContact());
        assertEquals(customerInput.getGender(), capturedCustomer.getGender());
        assertEquals(customerInput.getMail(), capturedCustomer.getMail());
        assertEquals(0, capturedCustomer.getAccounts().size());
        assertEquals(expectedCustomer, result);
    }
}