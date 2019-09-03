package com.pacman.unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.pacman.utils.CSVUtils;


public class CSVUtilsTest 
{
    @Test
    public void test_object_construction() 
    {        
        CSVUtils result = new CSVUtils();
        assertNotEquals(result, null);
    }
	
    @Test
    public void test_null_line() 
    {
        String line = null;
        List<String> result = CSVUtils.parseLine(line);
        
        assertNotEquals(result, null);
        assertEquals(result.size(), 0);
    }
	
    @Test
    public void test_empty_line() 
    {
        String line = "";
        List<String> result = CSVUtils.parseLine(line);
        
        assertNotEquals(result, null);
        assertEquals(result.size(), 0);
    }
    
    @Test
    public void test_end_of_line_lf() 
    {
        String line = "10,AU,Australia\n11,CA,Canada";
        List<String> result = CSVUtils.parseLine(line);
        
        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }
    
    @Test
    public void test_end_of_line_cr() 
    {
        String line = "10,AU,Australia,\r11,CA,Canada";
        List<String> result = CSVUtils.parseLine(line);
        
        assertNotEquals(result, null);
        assertEquals(result.size(), 6);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
        assertEquals(result.get(3), "11");
        assertEquals(result.get(4), "CA");
        assertEquals(result.get(5), "Canada");
    }
    
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
    public void test_custom_empty_space_separator()
    {
        String line = "10,AU,Australia";
        List<String> result = CSVUtils.parseLine(line, ' ');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }
    
    @Test
    public void test_custom_empty_separator_and_quote()
    {
        String line = "10,AU,Australia";
        List<String> result = CSVUtils.parseLine(line, ' ', ' ');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
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