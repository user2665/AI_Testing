package com.phapnqh;

/**
 * DateTimeChecker utility class for validating dates
 * Implements the date validation logic according to the flowchart specifications
 */
public class DateTimeChecker {

    /**
     * Validates if the given day, month, and year form a valid date
     * @param day The day (1-31)
     * @param month The month (1-12)
     * @param year The year (1000-3000)
     * @return true if the date is valid, false otherwise
     */
    public static boolean isValidDate(int day, int month, int year) {
        // First check if values are in basic ranges
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1000 || year > 3000) {
            return false;
        }

        // Check days in month according to the flowchart logic
        int daysInMonth = getDaysInMonth(month, year);
        return day <= daysInMonth;
    }

    /**
     * Gets the number of days in a given month and year
     * @param month The month (1-12)
     * @param year The year
     * @return The number of days in the month
     */
    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 0;
        }
    }

    /**
     * Checks if a year is a leap year
     * @param year The year to check
     * @return true if the year is a leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Validates input string as integer within specified range
     * @param input The input string
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @param fieldName Name of the field for error messages
     * @return ValidationResult containing the parsed value or error information
     */
    public static ValidationResult validateInput(String input, int min, int max, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            return new ValidationResult(false, 0, fieldName + " field cannot be empty");
        }

        try {
            int value = Integer.parseInt(input.trim());
            if (value < min || value > max) {
                return new ValidationResult(false, value,
                    fieldName + " must be between " + min + " and " + max);
            }
            return new ValidationResult(true, value, null);
        } catch (NumberFormatException e) {
            return new ValidationResult(false, 0, fieldName + " must be a number");
        }
    }

    /**
     * Result class for input validation
     */
    public static class ValidationResult {
        private final boolean isValid;
        private final int value;
        private final String errorMessage;

        public ValidationResult(boolean isValid, int value, String errorMessage) {
            this.isValid = isValid;
            this.value = value;
            this.errorMessage = errorMessage;
        }

        public boolean isValid() {
            return isValid;
        }

        public int getValue() {
            return value;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
