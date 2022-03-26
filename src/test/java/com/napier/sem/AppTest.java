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
    void printEmployeeInfo()
    {
        app.getEmployee("10002");

    }



}