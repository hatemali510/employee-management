# Employee Management API

This project is an Employee Management API built using Spring Boot. The API allows you to manage employee records by performing basic CRUD operations and notifies employees via email for certain actions.

## Setup and Configuration

1. Clone the repository.
2. Configure the email settings in `src/main/resources/application.properties` with your SMTP server details.
3. Build and run the application:
   ```bash
   ./mvnw spring-boot:run
   
## API Endpoints

1. **Create Employee**
    - URL: /api/employees
    
    - Method: POST
    
    - Description: Creates a new employee, validates email and department with third-party APIs, and saves the record to the database. Sends a notification email upon successful creation.
    
    - Request Body:`{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "department": "Engineering",
    "salary": 75000
    }`
    - Sample curl Command:
`curl -X POST http://localhost:${CONFIGURED_PORT}/api/employees \
    -H "Content-Type: application/json" \
    -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "department": "Engineering",
    "salary": 75000
    }'`
2. **Get Employee by ID**
   - URL: /api/employees/{id} 
   - Method: GET 
   - Description: Retrieves employee details by their unique ID. 
   - Path Parameters:
     - id: The unique identifier of the employee. 
   - Sample curl Command:
     - curl -X GET http://localhost:8080/api/employees/{id}


3. Update Employee
   - URL: /api/employees/{id} 
   - Method: PUT 
   - Description: Updates the specified employee’s details. Sends a notification email upon successful update. 
   - Path Parameters:
     - id: The unique identifier of the employee.
   - Request Body:
`{
"firstName": "Jane",
"lastName": "Doe",
"email": "jane.doe@example.com",
"department": "Finance",
"salary": 80000
}`
   - Sample curl Command:

    `curl -X PUT http://localhost:8080/api/employees/{id} \
    -H "Content-Type: application/json" \
    -d '{
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe@example.com",
    "department": "Finance",
    "salary": 80000
    }'`
4. Delete Employee
   - URL: /api/employees/{id} 
   - Method: DELETE 
   - Description: Deletes the specified employee record from the database. Sends a notification email upon successful deletion. 
   - Path Parameters:
     - id: The unique identifier of the employee. 
   - Sample curl Command:
     - curl -X DELETE http://localhost:8080/api/employees/{id}
5. List All Employees
   - URL: /api/employees 
   - Method: GET 
   - Description: Retrieves a list of all employees. 
   - Sample curl Command:
   - curl -X GET http://localhost:8080/api/employees

## Swagger 
   - you can find the swagger link to test this APIs  : http://localhost:9090/swagger-ui/index.html

## postman collection : 
   - you can find the postman collection ready to use to test the project in this path : 