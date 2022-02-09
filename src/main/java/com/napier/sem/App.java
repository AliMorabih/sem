package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args) {
        // Create new Application
        App a = new App();
        // Connect to database
        a.connect();
        // Get Employee
        //Employee emp = a.getEmployee();
       //Employee emp = a.getEmployee(255530 );
        // Display results
        ArrayList<Employee> employees = a.getEmployee();

        a.printEpmplyeeByRole(employees);

        // Disconnect from database
        a.disconnect();
    }
     /**
     * Connection to MySQL database.
     */
   private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(){

        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    /******************************************************************************************************************
     *  Create a SQL statement - a Statement object from the database connection.
    Define the SQL query string to execute.
    Execute a query (executeQuery) to extract data from the database. This will return a ResultSet object.
    Test that the ResultSet has a value - call next on the ResultSet and check this is true.
    Extract the information from the current record in the ResultSet using getInt for integer data, getString for string data, etc.
    *******************************************************************************************************************/
    /**
     * Salaries by Role Feature
     * @return A list of all employees and salaries by Role and we filter by Engineer
     */
    public ArrayList<Employee> getEmployee()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                  " SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                + " FROM employees, salaries, titles "
                + " WHERE employees.emp_no = salaries.emp_no "
                + " AND employees.emp_no = titles.emp_no "
                + " AND salaries.to_date = '9999-01-01' "
                + " AND titles.to_date = '9999-01-01' "
                + " AND titles.title = 'Engineer' "
                + " ORDER BY employees.emp_no ASC " ;


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Prints a list of employees.
     * @param employees The list of employees to print.
     */
    public void printEpmplyeeByRole(ArrayList<Employee> employees)
    {
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees)
        {
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }


}