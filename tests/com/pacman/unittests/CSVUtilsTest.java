package com.pacman.unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.pacman.utils.CSVUtils;


public class CSVUtilsTest 
{
    @Test
    public void test_no_quote() 
    {
        String line = "10,AU,Australia";
        List<String> result = CSVUtils.parseLine(line);
        
        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }

    @Test
    public void test_no_quote_but_double_quotes_in_column() 
    {
        String line = "10,AU,Aus\"\"tralia";
        List<String> result = CSVUtils.parseLine(line);
        
        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus\"tralia");
    }

    @Test
    public void test_double_quotes()
    {
        String line = "\"10\",\"AU\",\"Australia\"";
        List<String> result = CSVUtils.parseLine(line);

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }

    @Test
    public void test_double_quotes_but_comma_in_column() 
    {
        String line = "\"10\",\"AU\",\"Aus,tralia\"";
        List<String> result = CSVUtils.parseLine(line);

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus,tralia");
    }
    
    @Test
    public void test_custom_separator()
    {
        String line = "10|AU|Australia";
        List<String> result = CSVUtils.parseLine(line, '|');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }

    @Test
    public void test_custom_separator_and_quote()
    {
        String line = "'10'|'AU'|'Australia'";
        List<String> result = CSVUtils.parseLine(line, '|', '\'');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }

    @Test
    public void test_custom_separator_and_quote_but_custom_quote_in_column() 
    {
        String line = "'10'|'AU'|'Aus|tralia'";
        List<String> result = CSVUtils.parseLine(line, '|', '\'');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus|tralia");
    }

    @Test
    public void test_custom_separator_and_quote_but_double_quotes_in_column() 
    {
        String line = "'10'|'AU'|'Aus\"\"tralia'";
        List<String> result = CSVUtils.parseLine(line, '|', '\'');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus\"tralia");
    }

}