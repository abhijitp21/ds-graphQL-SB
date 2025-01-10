package com.fastlight.demo.datafetchers;

import com.fastlight.demo.model.Account;
import com.fastlight.demo.model.AccountInput;
import com.fastlight.demo.model.Customer;
import com.fastlight.demo.model.CustomerInput;
import com.fastlight.demo.repository.AccountRepository;
import com.fastlight.demo.repository.CustomerRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class serves as a data fetcher responsible for handling GraphQL queries and mutations
 * related to customers and their associated accounts. It integrates with the underlying
 * repositories to facilitate data retrieval and persistence.
 */
@DgsComponent
public class CustomerDataFetcher {

    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account not found: ";
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    // Constructor injection for better testability
    @Autowired
    public CustomerDataFetcher(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    // Fetch all customers
    @DgsData(parentType = "Query", field = "customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Fetch accounts for a given customer
    @DgsData(parentType = "Customer", field = "accounts")
    public List<Account> getCustomerAccounts(DgsDataFetchingEnvironment environment) {
        Customer customer = environment.getSource();
        return customer.getAccounts().stream()
                .map(account -> fetchAccountById(account.getAccountId()))
                .collect(Collectors.toList());
    }

    // Create a new customer with provided input
    @DgsMutation
    public Customer createCustomer(CustomerInput customerInput) {
        Customer customer = Customer.builder()
                .contact(customerInput.getContact())
                .name(customerInput.getName())
                .gender(customerInput.getGender())
                .mail(customerInput.getMail())
                .accounts(convertToAccountEntities(customerInput.getAccounts()))
                .build();
        return customerRepository.save(customer);
    }

    // Transform CustomerInput accounts into Account entities
    private List<Account> convertToAccountEntities(List<AccountInput> accountInputs) {
        return accountInputs.stream()
                .map(input -> Account.builder()
                        .accountBalance(input.getAccountBalance())
                        .accountNumber(input.getAccountNumber())
                        .accountStatus(input.getAccountStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // Retrieve an Account by its ID with error handling
    private Account fetchAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException(ACCOUNT_NOT_FOUND_MESSAGE + accountId));
    }
}