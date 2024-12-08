# API for a checkout system

This API is a checkout system for a fictional supermarket. The idea is that the API allows the total cost of a basket, considering prices and promotions.

## Technologies

- **Spring Boot**
- **Hibernate / JPA**
- **PostgreSQL**
- **JUnit**
- **MapStruct**
- **Swagger**

## Overview of the API

The API is designed to offer flexibility and scalability for tenant-specific configurations. It ensures seamless integration, modularity, and isolation between tenants. Below are the key features:

1. Flexible Data Format Configuration
The API allows each tenant to configure specific data formats tailored to their needs.
Formats are mapped independently, simplifying integration with various systems.
2. Reusable Format Mappings
Tenants with the same data format can reuse the existing format mapping implementation.
This reduces duplication and streamlines the onboarding process for new tenants.
3. Easy Addition of Promotions
New types of promotions can be added effortlessly.
Custom promotion rules can be implemented without major changes to the existing codebase.
4. Dynamic Tenant Management
Tenants can be dynamically configured and managed via specific endpoints.
This enables rapid deployment and scalability for new tenants.
5. Modular Architecture
The solution is built with a modular architecture, allowing for: Easy addition of new modules, such as mapping formats or promotion types.
Minimal effort and impact on existing functionality when extending features.

6. Tenant Isolation
Tenants are completely isolated from one another.
Changes or data from one tenant do not interfere with other tenants, ensuring data integrity and system reliability.


## API Endpoints

This document provides details on the most important API endpoints for managing baskets and performing checkout operations. Additional endpoints and configurations can be found in the Swagger documentation.

### BasketController API

### Base URL
`/api`

### Endpoints

#### 1. Get All Baskets
- **Endpoint**: `GET /{tenantName}/baskets`
- **Description**: Retrieves all baskets for the specified tenant.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
- **Response**: A list of `BasketResponseDTO`.

#### 2. Create Basket
- **Endpoint**: `POST /{tenantName}/baskets`
- **Description**: Creates a new basket with the specified items for the tenant.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
- **Request Body**: A list of `BasketItemRequestDTO`.
- **Response**: The created `BasketResponseDTO`.
- **Note**: Duplicate items in the request body will be grouped together, and their quantities will be summed.
  
#### 3. Detail Basket
- **Endpoint**: `GET /{tenantName}/baskets/{basketId}`
- **Description**: Retrieves the details of a specific basket.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Response**: The details of the `BasketResponseDTO`.

#### 4. Increase Item Quantity
- **Endpoint**: `PUT /{tenantName}/baskets/{basketId}/items/increase-quantity`
- **Description**: Increases the quantity of specified items in the basket.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Request Body**: A list of `BasketItemRequestDTO`.
- **Response**: The updated `BasketResponseDTO`.

#### 5. Decrease Item Quantity
- **Endpoint**: `PUT /{tenantName}/baskets/{basketId}/items/decrease-quantity`
- **Description**: Decreases the quantity of specified items in the basket.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Request Body**: A list of `BasketItemRequestDTO`.
- **Response**: The updated `BasketResponseDTO`.

#### 6. Add or Update Items
- **Endpoint**: `PUT /{tenantName}/baskets/{basketId}/items/add-or-update`
- **Description**: Adds or updates items in the basket.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Request Body**: A list of `BasketItemRequestDTO`.
- **Response**: The updated `BasketResponseDTO`.
- **Note**:
    - Duplicate items that do not yet belong to the basket will be grouped together and their quantities will be added together.
    - If there are duplicate items that already belong to the basket, only the last item will be considered in the update.

#### 7. Delete Basket
- **Endpoint**: `DELETE /{tenantName}/baskets/{basketId}`
- **Description**: Deletes the specified basket.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Response**: `204 No Content`.

#### 8. Delete Basket Items
- **Endpoint**: `DELETE /{tenantName}/baskets/{basketId}/items`
- **Description**: Deletes specified items from the basket.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Request Body**: A list of product IDs (String).
- **Response**: The updated `BasketResponseDTO`.

#### 9. Reopen Basket
- **Endpoint**: `PUT /{tenantName}/baskets/{basketId}/reopen`
- **Description**: Reopens a previously closed basket.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Response**: `200 OK`.

#### 10. Perform Checkout
- **Endpoint**: `PUT /{tenantName}/baskets/{basketId}/checkout`
- **Description**: Performs the checkout operation for the specified basket.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Response**: `CheckoutBasketModelResponseDTO`.

#### 11. Show Checkout
- **Endpoint**: `GET /{tenantName}/baskets/{basketId}/checkout`
- **Description**: Shows the checkout details for the specified basket without performing the checkout.
- **Path Variables**:
  - `tenantName` (String): The name of the tenant.
  - `basketId` (Long): The ID of the basket.
- **Response**: `CheckoutBasketModelResponseDTO`.




## Follow up questions
### 1. How long did you spend on the test? What would you add if you had more time?
  Due to my routine, specifically at the end of November, it took me a little over a week to complete the challenge. I believe that with more time I could have implemented a larger battery of tests.
  
### 2. What was the most useful feature that was added to the latest version of your chosen language? Please include a snippet of code that
shows how you've used it.
  In the latest version of Java (Java 17), no new features were explicitly used in the project. However, there are several new enhancements available, such as sealed classes.

### 3. What did you find most difficult?
  Create a well-structured system in such a way that minimal changes needed to be made when importing new tenants, as well as changes to the code base itself.
### 4. What mechanism did you put in place to track down issues in production on this code? If you didn’t put anything, write down what you could do.
  Currently, no specific mechanisms have been implemented to track issues in production. However, logging frameworks like Logback or Log4j2 could be used to capture detailed application logs, including errors, request information, and stack traces. These logs can be centralized using tools like ELK Stack (Elasticsearch, Logstash, Kibana).
  
### 5. Explain your interpretation of the list of requirements and what was delivered or not delivered and why.
  My interpretation was that an API should be created that calculates discounts on products. However, the products and their promotions must be consulted on another service. The API would be like a centralized calculation mechanism and the data (products and promotions) could be consulted from several other services (tenants). All requirements have been completed except Internationalization.
