# JDBC Notes

- Install Dependency
  - mysql driver
- Basics Running Query in Java
  - Connection
    - used DriverManager to create
    - Class.forName("...") is no longer necessary
  - Statement
  - ResultSet
- Prepared Statement
  - instead of just Statement
  - prevent SQL Injection attacks (hacks)
  - allow placeholders (?) in queries
- try catch finally
  - should close Autocloseable resources in the finally block including:
    - Connection, Statement, ResultSet
- try catch (with resource block)
  - allows us to autoclose things and not have a finally
- use DataSource
  - instead of DriverManager to create Connection
- DataManager
  - pulling your jdbc code out of main
  - pass the DataSource in the constructor
  - contains all queries in your application
- Data Access Objects (DAOs)
  - one DAO per database table
  - methods are CRUD operations
    - CRUD (Create, Read, Update, Delete)
    - getAll, search (READ)
      - return data as a List<Model> objects
        - Ex. List<Product> getAll() on productsDAO
    - create
    - update
    - delete


6/11/2025
Exercise 1
- NorthwindTraders
1. display all products using a prepared statement
2. add a try catch block with a resource block to autoclose: connection, preparedstatement, resultset
3. search products use a placeholder ? to search for product by what it starts with
   - be sure to autoclose everything using resource block
   - you will need to use two nested try blocks
   - use this example: https://github.com/craigmckeachie/workbook-8/blob/main/sakila/src/main/java/com/pluralsight/Main.java