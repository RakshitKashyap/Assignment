Employee Management API
This is a RESTful API developed using Java with Spring Boot framework. The API allows performing CRUD (Create, Read, Update, Delete) 
operations on Employee entities, as well as managing Employee Addresses. Below are the details of the implementation:

Functionality
1. Create new Employee
Endpoint: POST /v1/employees/add

Creates a new Employee with the provided details.

2. Create Employee Address
Endpoint: POST /api/employees/{employeeId}/addresses/add

Creates a new Address for the specified Employee.

3. Update Employee
Endpoint: PUT /v1/employees/{employeeId}

Updates the details of an existing Employee.

4. Update Employee Address
Endpoint: PUT /v1/employees/{employeeId}/addresses/{addressId}

Updates the details of an existing Address associated with the specified Employee.

5. Fetch Employee by Id
Endpoint: GET /v1/employees/view/{employeeId}

Retrieves the details of a specific Employee by their ID.

6. Fetch All Employees
Endpoint: GET /v1/employees/viewAll

Retrieves all Employees, with options for sorting and searching by name.

7. Fetch all addresses of a particular employee
Endpoint: GET /v1/employees/{employeeId}/addresses

Retrieves all Addresses associated with a specific Employee.

8. Delete Employee
Endpoint: DELETE /v1/employees/delete/{employeeId}

Deletes an Employee and their associated Addresses.

8. Delete Employee address
Endpoint: DELETE /v1/employees/delete/{employeeId}/

Deletes an Employee Addresses from Address List.

8.Fetch Employ by name and sort
Endpoint: POST /api/employees/view/searchAndOrder/
// in here i've mentioned two different method 
> using normal jpa projection
> using stream API 

Technology Stack
Java: Used as the primary programming language for backend development.
Spring Boot: Framework for building Java-based applications with ease.
MySQL: Relational database management system used for storing and managing data.
Spring Data JPA: Part of the larger Spring Data project, used for simplifying the implementation of data access layers.
Spring Security: Provides authentication, authorization, and protection against common security exploits.
Swagger: Used for API documentation to ensure clarity and accessibility.
Lombok: Library used to reduce boilerplate code in Java classes, improving code readability and conciseness.
JUnit: Framework for writing and running automated tests in Java.


Additional Features
Implemented proper validation of incoming requests.
Utilized Spring Data JPA for database interactions.
Implemented appropriate error handling.
Utilized HTTPStatus codes for responses.
Implemented Swagger for API documentation.
Used Lombok to reduce boilerplate code.
Written comprehensive unit tests for repositories, controllers, entities, and services.
Tested API endpoints using Postman.
Implemented basic authentication for securing the API endpoints.


