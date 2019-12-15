# books-springbootadmin
Basic implementation of a Spring Boot Admin project.

Supports sending a JWT token to instances of the books project (see https://github.com/aidanwhiteley/books).
See the books project for how to get a long lived JWT token that allows access to the actuator end points
for that project and then plug that string into the jwt.token.actuator.user property of this project.

Also supports, and now expects, there to be a "service registry" running and available from which this application can
find the details of the applications that it should be monitoring.

Currently this application also registers its details in the service registry and its actuator metrics can be 
viewed within the Spring Boot Admin UI!
