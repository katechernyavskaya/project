Shop application with different user types.
Specification: https://bit.ly/2YCgFQl

3-level three-module development architecture with the following technologies:
* Java 8
* Log4j2
* Spring Boot 2
* Spring 5
* Hibernate 5
* MySQL 8
  + Liquibase for migration

Users with its createntials and different roles:
* ADMINISTRATOR (admin@user.com/admin)
* CUSTOMER_USER (customer@user.com/customer)
* SALE_USER (sale@user.com/sale)
* SECURE_API_USER (secure@user.com/secure)

Before starting please set necessary properties in application.properties file (web-module):
* Connection to db: spring.datasource.*
* Smtp server for email sending: spring.mail.*
* Hibernate properties: spring.jpa.*

For testing purposes you could set necessary properties in application-integration.properties


Enjoy!
