package com.phapnqh;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for DateTimeChecker application
 * Testing all functionality as specified in the requirements document
 */
public class DateTimeCheckerTest {

    @Test
    public void testValidDates() {
        // Test valid dates
        assertTrue("29/02/2020 should be valid (leap year)",
                   DateTimeChecker.isValidDate(29, 2, 2020));
        assertTrue("28/02/2021 should be valid (non-leap year)",
                   DateTimeChecker.isValidDate(28, 2, 2021));
        assertTrue("31/01/2023 should be valid",
                   DateTimeChecker.isValidDate(31, 1, 2023));
        assertTrue("30/04/2023 should be valid",
                   DateTimeChecker.isValidDate(30, 4, 2023));
        assertTrue("31/12/2999 should be valid",
                   DateTimeChecker.isValidDate(31, 12, 2999));
        assertTrue("01/01/1000 should be valid",
                   DateTimeChecker.isValidDate(1, 1, 1000));
    }

    @Test
    public void testInvalidDates() {
        // Test invalid dates - February 29 in non-leap year
        assertFalse("29/02/2021 should be invalid (non-leap year)",
                    DateTimeChecker.isValidDate(29, 2, 2021));

        // Test invalid dates - February 30
        assertFalse("30/02/2020 should be invalid",
                    DateTimeChecker.isValidDate(30, 2, 2020));

        // Test invalid dates - April 31
        assertFalse("31/04/2023 should be invalid",
                    DateTimeChecker.isValidDate(31, 4, 2023));

        // Test invalid dates - June 31
        assertFalse("31/06/2023 should be invalid",
                    DateTimeChecker.isValidDate(31, 6, 2023));

        // Test invalid dates - September 31
        assertFalse("31/09/2023 should be invalid",
                    DateTimeChecker.isValidDate(31, 9, 2023));

        // Test invalid dates - November 31
        assertFalse("31/11/2023 should be invalid",
                    DateTimeChecker.isValidDate(31, 11, 2023));
    }

    @Test
    public void testLeapYears() {
        // Test leap year logic
        assertTrue("2000 should be a leap year", DateTimeChecker.isLeapYear(2000));
        assertTrue("2004 should be a leap year", DateTimeChecker.isLeapYear(2004));
        assertTrue("2020 should be a leap year", DateTimeChecker.isLeapYear(2020));

        assertFalse("1900 should not be a leap year", DateTimeChecker.isLeapYear(1900));
        assertFalse("2001 should not be a leap year", DateTimeChecker.isLeapYear(2001));
        assertFalse("2100 should not be a leap year", DateTimeChecker.isLeapYear(2100));
    }

    @Test
    public void testDaysInMonth() {
        // Test 31-day months
        assertEquals("January should have 31 days", 31, DateTimeChecker.getDaysInMonth(1, 2023));
        assertEquals("March should have 31 days", 31, DateTimeChecker.getDaysInMonth(3, 2023));
        assertEquals("May should have 31 days", 31, DateTimeChecker.getDaysInMonth(5, 2023));
        assertEquals("July should have 31 days", 31, DateTimeChecker.getDaysInMonth(7, 2023));
        assertEquals("August should have 31 days", 31, DateTimeChecker.getDaysInMonth(8, 2023));
        assertEquals("October should have 31 days", 31, DateTimeChecker.getDaysInMonth(10, 2023));
        assertEquals("December should have 31 days", 31, DateTimeChecker.getDaysInMonth(12, 2023));

        // Test 30-day months
        assertEquals("April should have 30 days", 30, DateTimeChecker.getDaysInMonth(4, 2023));
        assertEquals("June should have 30 days", 30, DateTimeChecker.getDaysInMonth(6, 2023));
        assertEquals("September should have 30 days", 30, DateTimeChecker.getDaysInMonth(9, 2023));
        assertEquals("November should have 30 days", 30, DateTimeChecker.getDaysInMonth(11, 2023));

        // Test February
        assertEquals("February should have 28 days in non-leap year", 28, DateTimeChecker.getDaysInMonth(2, 2021));
        assertEquals("February should have 29 days in leap year", 29, DateTimeChecker.getDaysInMonth(2, 2020));
    }

    @Test
    public void testValidInputValidation() {
        // Test valid day input
        DateTimeChecker.ValidationResult result = DateTimeChecker.validateInput("15", 1, 31, "Day");
        assertTrue("Valid day input should pass validation", result.isValid());
        assertEquals("Parsed value should be correct", 15, result.getValue());
        assertNull("Error message should be null for valid input", result.getErrorMessage());

        // Test valid month input
        result = DateTimeChecker.validateInput("12", 1, 12, "Month");
        assertTrue("Valid month input should pass validation", result.isValid());
        assertEquals("Parsed value should be correct", 12, result.getValue());

        // Test valid year input
        result = DateTimeChecker.validateInput("2023", 1000, 3000, "Year");
        assertTrue("Valid year input should pass validation", result.isValid());
        assertEquals("Parsed value should be correct", 2023, result.getValue());
    }

    @Test
    public void testEmptyInputValidation() {
        // Test empty input
        DateTimeChecker.ValidationResult result = DateTimeChecker.validateInput("", 1, 31, "Day");
        assertFalse("Empty input should fail validation", result.isValid());
        assertEquals("Error message should indicate empty field", "Day field cannot be empty", result.getErrorMessage());

        // Test null input
        result = DateTimeChecker.validateInput(null, 1, 31, "Day");
        assertFalse("Null input should fail validation", result.isValid());
        assertEquals("Error message should indicate empty field", "Day field cannot be empty", result.getErrorMessage());

        // Test whitespace-only input
        result = DateTimeChecker.validateInput("   ", 1, 31, "Day");
        assertFalse("Whitespace-only input should fail validation", result.isValid());
        assertEquals("Error message should indicate empty field", "Day field cannot be empty", result.getErrorMessage());
    }

    @Test
    public void testNonNumericInputValidation() {
        // Test non-numeric day input
        DateTimeChecker.ValidationResult result = DateTimeChecker.validateInput("abc", 1, 31, "Day");
        assertFalse("Non-numeric input should fail validation", result.isValid());
        assertEquals("Error message should indicate number required", "Day must be a number", result.getErrorMessage());

        // Test non-numeric month input
        result = DateTimeChecker.validateInput("1.5", 1, 12, "Month");
        assertFalse("Decimal input should fail validation", result.isValid());
        assertEquals("Error message should indicate number required", "Month must be a number", result.getErrorMessage());

        // Test non-numeric year input
        result = DateTimeChecker.validateInput("20xx", 1000, 3000, "Year");
        assertFalse("Alphanumeric input should fail validation", result.isValid());
        assertEquals("Error message should indicate number required", "Year must be a number", result.getErrorMessage());
    }

    @Test
    public void testOutOfRangeInputValidation() {
        // Test day out of range (below minimum)
        DateTimeChecker.ValidationResult result = DateTimeChecker.validateInput("0", 1, 31, "Day");
        assertFalse("Day below range should fail validation", result.isValid());
        assertEquals("Error message should indicate range", "Day must be between 1 and 31", result.getErrorMessage());

        // Test day out of range (above maximum)
        result = DateTimeChecker.validateInput("32", 1, 31, "Day");
        assertFalse("Day above range should fail validation", result.isValid());
        assertEquals("Error message should indicate range", "Day must be between 1 and 31", result.getErrorMessage());

        // Test month out of range (below minimum)
        result = DateTimeChecker.validateInput("0", 1, 12, "Month");
        assertFalse("Month below range should fail validation", result.isValid());
        assertEquals("Error message should indicate range", "Month must be between 1 and 12", result.getErrorMessage());

        // Test month out of range (above maximum)
        result = DateTimeChecker.validateInput("13", 1, 12, "Month");
        assertFalse("Month above range should fail validation", result.isValid());
        assertEquals("Error message should indicate range", "Month must be between 1 and 12", result.getErrorMessage());

        // Test year out of range (below minimum)
        result = DateTimeChecker.validateInput("999", 1000, 3000, "Year");
        assertFalse("Year below range should fail validation", result.isValid());
        assertEquals("Error message should indicate range", "Year must be between 1000 and 3000", result.getErrorMessage());

        // Test year out of range (above maximum)
        result = DateTimeChecker.validateInput("3001", 1000, 3000, "Year");
        assertFalse("Year above range should fail validation", result.isValid());
        assertEquals("Error message should indicate range", "Year must be between 1000 and 3000", result.getErrorMessage());
    }

    @Test
    public void testBoundaryValues() {
        // Test boundary values for day
        assertTrue("Day 1 should be valid", DateTimeChecker.isValidDate(1, 1, 2023));
        assertTrue("Day 31 should be valid in January", DateTimeChecker.isValidDate(31, 1, 2023));

        // Test boundary values for month
        assertTrue("Month 1 should be valid", DateTimeChecker.isValidDate(15, 1, 2023));
        assertTrue("Month 12 should be valid", DateTimeChecker.isValidDate(15, 12, 2023));

        // Test boundary values for year
        assertTrue("Year 1000 should be valid", DateTimeChecker.isValidDate(15, 6, 1000));
        assertTrue("Year 3000 should be valid", DateTimeChecker.isValidDate(15, 6, 3000));

        // Test February 28/29 boundary
        assertTrue("Feb 28 should be valid in non-leap year", DateTimeChecker.isValidDate(28, 2, 2021));
        assertFalse("Feb 29 should be invalid in non-leap year", DateTimeChecker.isValidDate(29, 2, 2021));
        assertTrue("Feb 29 should be valid in leap year", DateTimeChecker.isValidDate(29, 2, 2020));
        assertFalse("Feb 30 should be invalid in leap year", DateTimeChecker.isValidDate(30, 2, 2020));
    }

    @Test
    public void testSpecialLeapYearCases() {
        // Test century years that are not leap years
        assertFalse("1700 should not be a leap year", DateTimeChecker.isLeapYear(1700));
        assertFalse("1800 should not be a leap year", DateTimeChecker.isLeapYear(1800));
        assertFalse("1900 should not be a leap year", DateTimeChecker.isLeapYear(1900));
        assertFalse("2100 should not be a leap year", DateTimeChecker.isLeapYear(2100));

        // Test century years that are leap years (divisible by 400)
        assertTrue("1600 should be a leap year", DateTimeChecker.isLeapYear(1600));
        assertTrue("2000 should be a leap year", DateTimeChecker.isLeapYear(2000));
        assertTrue("2400 should be a leap year", DateTimeChecker.isLeapYear(2400));

        // Test February 29 in these special cases
        assertFalse("29/02/1900 should be invalid", DateTimeChecker.isValidDate(29, 2, 1900));
        assertTrue("29/02/2000 should be valid", DateTimeChecker.isValidDate(29, 2, 2000));
    }
}
