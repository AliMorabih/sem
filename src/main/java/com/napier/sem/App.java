package com.napier.sem;

import java.sql.*;

public class App
{
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        // Get Employee
        Employee emp = a.getEmployee();
       //Employee emp = a.getEmployee(255530 );
        // Display results
        a.displayEmployee(emp);

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
    public Employee getEmployee()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            /**String strSelect = " SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary, titles.to_date  "
                    +" FROM employees, salaries,  titles, dept_emp "
                    +" WHERE employees.emp_no = salaries.emp_no"
                    +" AND employees.emp_no = dept_emp.emp_no"
                    +" AND employees.emp_no = titles.emp_no"
                    +" AND salaries.to_date = '9999-01-01'"
                    +" AND titles.to_date = '9999-01-01'"; **/

            String strSelect = " SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                 + " FROM employees, salaries, titles "
                 + " WHERE employees.emp_no = salaries.emp_no "
                 + "AND employees.emp_no = titles.emp_no "
                 + "AND salaries.to_date = '9999-01-01' "
                 + "AND titles.to_date = '9999-01-01' "
                 + "AND titles.title = 'Engineer' "
                 + "ORDER BY employees.emp_no ASC ";




            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                return emp;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }
    /**************************************************************************************************
    * We cannot really test our get employee functionality until we display the output. At the moment,
    * we will just display to the console. The displayEmployee method for our App is below:
    * ***********************************************************************************************/
    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }
}