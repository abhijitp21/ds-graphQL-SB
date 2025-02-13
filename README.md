

# CustomerDataFetcher Service + DGS Framework

The `CustomerDataFetcher` class is a **GraphQL Data Fetcher** that handles queries and mutations related to `Customer` and `Account` entities within the application. This service integrates with `CustomerRepository` and `AccountRepository` to perform database operations and is annotated as a `@DgsComponent`, making it part of the Netflix DGS framework for GraphQL.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Endpoints](#endpoints)
- [Dependencies](#dependencies)
- [Usage](#usage)
- [Error Handling](#error-handling)
- [How to Test](#how-to-test)
- [Improvement Suggestions](#improvement-suggestions)

---

## Overview

The `CustomerDataFetcher` supports the following GraphQL operations:
1. Querying all customers (`Query.customers`).
2. Querying accounts associated with a specific customer (`Customer.accounts`).
3. Creating new customers through a `createCustomer` mutation.

It utilizes Spring Data repositories for persistent storage and retrieval while providing streamlined mapping of input data objects to entity models.

---

## Features

- **Query Customers**: Retrieve a list of all customers.
- **Fetch Customer Accounts**: Fetch all associated accounts for a customer.
- **Create Customer**: Create a new customer using a mutation with `CustomerInput`.
- **Error Handling**: Validates account existence and provides helpful error messages for non-existing accounts.

---

## Endpoints

### GraphQL Query: Fetch Customers

- **Field**: `Query.customers`
- **Description**: Fetches all customers from the database.
- **Sample Query**:
```graphql
query {
      customers {
        customerNumber
        name
        gender
        mail
      }
    }
```

### GraphQL Query: Fetch Customer Accounts

- **Field**: `Customer.accounts`
- **Description**: Fetches accounts associated with a specific customer.
- **Sample Query**:
```graphql
query {
      customers {
        name
        accounts {
          accountNumber
          accountBalance
        }
      }
    }
```

### GraphQL Mutation: Create Customer

- **Field**: `Mutation.createCustomer`
- **Description**: Creates a new customer in the database using `CustomerInput`.
- **Sample Mutation**:
```graphql
mutation {
      createCustomer(customerInput: {
        name: "John Doe",
        gender: "Male",
        contact: 123456789,
        mail: "john.doe@example.com",
        accounts: [
          {
            accountNumber: 987654321,
            accountStatus: "ACTIVE",
            accountBalance: 5000.00
          }
        ]
      }) {
        customerNumber
        name
      }
    }
```

---

## Dependencies

- **Spring Boot**
  - Used for dependency injection and repository integration.
- **Netflix DGS Framework**
  - Used for setting up and resolving GraphQL queries and mutations.
- **MongoDB**
  - Used as the underlying database for customer and account data persistence.
- **Lombok**
  - Used to reduce boilerplate code (e.g., getters, setters, constructors, and builders).

---

## Usage

### Setup and Run

Ensure the following:
1. **MongoDB** database is running.
2. Proper database configurations are set in `application.properties`.

Start the application using:
```shell script
./mvnw spring-boot:run
```

---

## Error Handling

### Invalid Account Reference

When a customer references non-existent accounts, the service throws the following exception:
- Exception: `IllegalArgumentException`
- Message: `"Account not found: <accountId>"`

---

## How to Test

#### Unit Tests
Unit tests should be written for each method in `CustomerDataFetcher`:
- Mock the `CustomerRepository` and `AccountRepository` to validate method functionality.
- Test cases should cover the following:
  - Successful customer fetch and account retrieval.
  - Valid customer creation.
  - Error scenarios for non-existent accounts.

#### Sample Test Tools
- **Junit 5**: Test framework.
- **Mockito**: Mocking framework for dependencies.

#### Example Test Cases:
1. **Test `getCustomerAccounts()`**:
   - Case: Customer has valid accounts.
   - Case: Account is missing, should throw `IllegalArgumentException`.

2. **Test `createCustomer()`**:
   - Case: Valid `CustomerInput` creates a new customer.
   - Case: Accounts without required fields result in validation error.

#### Integration Testing:
- Use Postman or GraphQL Playground to query and test GraphQL endpoints.

---

## Improvement Suggestions

1. Add proper validation for inputs:
   - Ensure `CustomerInput` and `AccountInput` fields are validated against null or invalid data.
2. Implement caching for account retrieval to improve performance for large datasets.
3. Add pagination support for fetching customers and accounts.
4. Use custom exceptions for improved error messaging and debugging.

---

This readme provides a comprehensive overview of the `CustomerDataFetcher`. Additional improvements can be made based on specific application needs or scaling requirements.


Mongo Start / Stop

brew services start mongodb-community@8.0
brew services stop mongodb-community@8.0
