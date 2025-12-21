# eBanking Backend – Project Documentation

## Overview
This project is a backend system for an e-banking application, built with Java and Spring Boot. It manages customers, bank accounts, transactions, and security, and exposes RESTful APIs for frontend or external use.

## How the Code Works
- The application starts from `EbankingBackendApplication.java`.
- It uses Spring Boot to auto-configure, wire dependencies, and expose REST endpoints.
- Data is managed using JPA repositories, with DTOs for data transfer and mappers for entity conversion.
- Controllers expose API endpoints for customer and bank account operations.
- Security is handled via configuration and controllers for authentication/authorization.
- Business logic is encapsulated in service classes.
- Properties and settings are in `application.properties`.

## Directory Structure

- **src/main/java/org/sid/ebankingbackend/**
  - **EbankingBackendApplication.java**: Main entry point for the Spring Boot application.
  - **dtos/**: Data Transfer Objects for moving data between layers (e.g., `CustomerDTO`, `BankAccountDTO`).
  - **entities/**: JPA entities representing database tables (e.g., `Customer`, `BankAccount`).
  - **enums/**: Enum types for domain-specific constants (e.g., `AccountStatus`, `OperationType`).
  - **exceptions/**: Custom exception classes for error handling (e.g., `CustomerNotFoundException`).
  - **mappers/**: Mapper classes to convert between entities and DTOs.
  - **repositories/**: Spring Data JPA repositories for database access.
  - **security/**: Security configuration and controllers for authentication and authorization.
  - **services/**: Service interfaces and implementations containing business logic.
  - **web/**: REST controllers exposing API endpoints (e.g., `CustomerRestController`, `BankAccountRestApi`).

- **src/main/resources/**
  - **application.properties**: Configuration for Spring Boot (DB connection, server port, etc.).
  - **static/**: (If used) Static resources for the application.
  - **templates/**: (If used) Template files, e.g., for emails or web pages.

- **src/test/java/**: Test source code.

- **pom.xml**: Maven project configuration (dependencies, build settings).
- **README.MD**: Basic project overview (if present).
- **HELP.md**: Additional help or getting started information.

## Directory and File Details

### `dtos/` – Data Transfer Objects
These classes are simple containers used to transfer data between layers (e.g., service to controller) without exposing the internal entity structure.

- **AccountHistoryDTO.java**  
  Holds details about the history of a bank account, including the list of operations and pagination info.
  - Example field:  
    - `List<AccountOperationDTO> accountOperationDTOS;`  
      Contains a paginated list of operations performed on the account.

- **AccountOperationDTO.java**  
  Represents a single operation (transaction) on an account, such as a credit or debit.
  - Example field:  
    - `OperationType type;`  
      Indicates if the operation is a debit or credit.

- **BankAccountDTO.java**  
  Base DTO for bank accounts, used for type abstraction.
  - Example field:  
    - `String type;`  
      Specifies the type of bank account (e.g., current, savings).

- **CreditDTO.java**  
  Used when performing a credit operation (adding money) to an account.
  - Example field:  
    - `double amount;`  
      The amount to credit to the account.

- **CurrentBankAccountDTO.java**  
  DTO for current bank accounts, extends `BankAccountDTO` and adds specific fields.
  - Example field:  
    - `double overDraft;`  
      The allowed overdraft limit for the account.

### `entities/` – Domain Model Entities
These classes represent the main business objects and are mapped to database tables using JPA annotations.

- **AccountOperation.java**  
  Represents a single transaction (credit or debit) performed on a bank account.
  - Example field:  
    - `private OperationType type;`  
      Specifies whether the operation is a credit or debit.

- **BankAccount.java**  
  Abstract base class for all bank accounts, with common fields like balance, creation date, and customer.
  - Example field:  
    - `private List<AccountOperation> accountOperations;`  
      List of all operations (transactions) performed on this account.

- **CurrentAccount.java**  
  Subclass of BankAccount for current accounts, adds overdraft capability.
  - Example field:  
    - `private double overDraft;`  
      The overdraft limit for this current account.

- **Customer.java**  
  Represents a bank customer, with name, email, and a list of their bank accounts.
  - Example field:  
    - `private List<BankAccount> bankAccounts;`  
      All accounts owned by this customer.

- **SavingAccount.java**  
  Subclass of BankAccount for savings accounts, adds interest rate.
  - Example field:  
    - `private double interestRate;`  
      The interest rate applied to this savings account.

### `enums/` – Enumerations
Defines fixed sets of constants used throughout the application to represent specific states or types.

- **AccountStatus.java**  
  Enum representing the status of a bank account.
  - Example value:  
    - `CREATED, ACTIVATED, SUSPENDED`  
      Used to track the lifecycle state of an account.

- **OperationType.java**  
  Enum representing the type of operation performed on an account.
  - Example value:  
    - `DEBIT, CREDIT`  
      Indicates whether an operation is a withdrawal or deposit.

### `exceptions/` – Custom Exceptions
Defines custom exception classes for specific error scenarios in the application.

- **BalanceNotSufficientException.java**  
  Thrown when an account does not have enough balance to complete a transaction.
  - Example constructor:  
    - `public BalanceNotSufficientException(String message)`  
      Passes a custom error message to the exception.

- **BankAccountNotFoundException.java**  
  Thrown when a requested bank account cannot be found in the system.
  - Example constructor:  
    - `public BankAccountNotFoundException(String message)`  
      Used to signal missing bank accounts.

- **CustomerNotFoundException.java**  
  Thrown when a requested customer does not exist in the database.
  - Example constructor:  
    - `public CustomerNotFoundException(String message)`  
      Used to signal missing customers.

### `mappers/` – Entity/DTO Conversion
Contains classes that convert between entity objects and Data Transfer Objects (DTOs), helping to keep domain logic separate from API data contracts.

- **BankAccountMapperImpl.java**  
  Provides methods to map between entities (e.g., `Customer`, `SavingAccount`) and their respective DTOs.
  - Example method:  
    - `public CustomerDTO fromCustomer(Customer customer)`  
      Converts a `Customer` entity to a `CustomerDTO` using Spring’s `BeanUtils.copyProperties`.

### `repositories/` – Data Access
Spring Data JPA repositories for accessing and querying the database.

- **AccountOperationRepository.java**  
  Repository for `AccountOperation` entities, provides methods to find operations by account ID.
  - Example method:  
    - `List<AccountOperation> findByBankAccountId(String accountId)`  
      Retrieves all operations for a given bank account.

- **BankAccountRepository.java**  
  Repository for `BankAccount` entities, supports standard CRUD operations.
  - Example:  
    - Inherits all basic CRUD methods from `JpaRepository`.

- **CustomerRepository.java**  
  Repository for `Customer` entities, with a custom search method.
  - Example method:  
    - `List<Customer> searchCustomer(String keyword)`  
      Finds customers whose names match a given keyword using a JPQL query.

### `security/` – Security Configuration and Endpoints
Handles authentication, authorization, and security settings for the backend.

- **SecurityConfig.java**  
  Configures Spring Security, JWT, password encoding, CORS, and authentication providers.
  - Example method:  
    - `@Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)`  
      Defines security rules for HTTP requests, session management, and JWT settings.

- **SecurityController.java**  
  REST controller for authentication endpoints (login, profile).
  - Example method:  
    - `@PostMapping("/login") public Map<String, String> login(...)`  
      Authenticates a user and returns a JWT token if credentials are valid.

### `services/` – Business Logic Layer
Contains interfaces and classes that implement the core business logic for banking operations.

- **BankAccountSericeImpl.java**  
  Implements `BankAccountService` with methods for customer/account management, transactions, and history.
  - Example method:  
    - `public CustomerDTO saveCustomer(CustomerDTO customerDTO)`  
      Saves a new customer and returns the saved DTO.

- **BankAccountService.java**  
  Interface defining all operations related to customers and bank accounts (CRUD, transactions, history).
  - Example method:  
    - `void transfer(String accountIdSource, String accountIdDestination, double amount)`  
      Transfers money between two accounts.

- **BankService.java**  
  Contains utility methods for consulting (displaying) account details and operations, mainly for internal/testing use.
  - Example method:  
    - `public void consulter()`  
      Prints details about a specific bank account and its operations to the console.

### `web/` – REST Controllers
Exposes HTTP endpoints for interacting with bank accounts and customers.

- **BankAccountRestApi.java**  
  REST controller for bank account operations (view, list, transaction history, debit, credit, transfer).
  - Example method:  
    - `@GetMapping("/accounts/{accountId}")`  
      Returns details of a specific bank account by ID.

- **CustomerRestController.java**  
  REST controller for customer operations (list, search, get, create, update, delete).
  - Example method:  
    - `@GetMapping("/customers")`  
      Returns a list of all customers.
