package com.napier.sem;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {

        app = new App();
    }

    @Test
    void printSalariesTestNull()
    {

        app.printSalarybydep(null);
    }
    @Test
    void printSalariesTestEmpty()
    {
        ArrayList<Employee> employess = new ArrayList<Employee>();
        app.printSalarybydep(employess);
    }
    @Test
    void printSalariesTestContainsNull()
    {
        ArrayList<Employee> employess = new ArrayList<Employee>();
        employess.add(null);
        app.printSalarybydep(employess);
    }
    @Test
    void printSalaries()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        employees.add(emp);
        app.printSalarybydep(employees);
    }
}