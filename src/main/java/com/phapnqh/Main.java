package com.phapnqh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    private JTextField dayField;
    private JTextField monthField;
    private JTextField yearField;
    private JButton clearButton;
    private JButton checkButton;

    public Main() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
    }

    private void initializeComponents() {
        // Text fields for input
        dayField = new JTextField(10);
        monthField = new JTextField(10);
        yearField = new JTextField(10);

        // Buttons
        clearButton = new JButton("Clear");
        checkButton = new JButton("Check");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Date Time Checker");
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));

        // Logo placeholder (as specified in requirements)
        JLabel logoLabel = new JLabel("FU Logo");
        logoLabel.setPreferredSize(new Dimension(80, 60));
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBackground(Color.LIGHT_GRAY);
        logoLabel.setOpaque(true);

        titlePanel.add(logoLabel);
        titlePanel.add(Box.createHorizontalStrut(10));
        titlePanel.add(titleLabel);

        // Input panel with left-aligned labels
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Day row
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel dayLabel = new JLabel("Day");
        dayLabel.setHorizontalAlignment(SwingConstants.LEFT);
        inputPanel.add(dayLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(dayField, gbc);

        // Month row
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel monthLabel = new JLabel("Month");
        monthLabel.setHorizontalAlignment(SwingConstants.LEFT);
        inputPanel.add(monthLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(monthField, gbc);

        // Year row
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel yearLabel = new JLabel("Year");
        yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
        inputPanel.add(yearLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(yearField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(clearButton);
        buttonPanel.add(checkButton);

        // Add panels to main frame
        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        // Clear button functionality
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Check button functionality
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkDateTime();
            }
        });

        // Window close event - disable maximize/minimize buttons
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
    }

    private void setupWindow() {
        setTitle("Date Time Checker");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        // Disable maximize button as per requirements
        setExtendedState(JFrame.NORMAL);
    }

    private void clearFields() {
        dayField.setText("");
        monthField.setText("");
        yearField.setText("");
    }

    private void checkDateTime() {
        try {
            // Validate all inputs first
            DateTimeChecker.ValidationResult dayResult = validateDayInput();
            if (!dayResult.isValid()) {
                handleValidationError(dayResult);
                return;
            }

            DateTimeChecker.ValidationResult monthResult = validateMonthInput();
            if (!monthResult.isValid()) {
                handleValidationError(monthResult);
                return;
            }

            DateTimeChecker.ValidationResult yearResult = validateYearInput();
            if (!yearResult.isValid()) {
                handleValidationError(yearResult);
                return;
            }

            // If all inputs are valid, check the date
            validateAndShowDateResult(dayResult.getValue(), monthResult.getValue(), yearResult.getValue());

        } catch (Exception e) {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    private DateTimeChecker.ValidationResult validateDayInput() {
        return DateTimeChecker.validateInput(dayField.getText(), 1, 31, "Day");
    }

    private DateTimeChecker.ValidationResult validateMonthInput() {
        return DateTimeChecker.validateInput(monthField.getText(), 1, 12, "Month");
    }

    private DateTimeChecker.ValidationResult validateYearInput() {
        return DateTimeChecker.validateInput(yearField.getText(), 1000, 3000, "Year");
    }

    private void handleValidationError(DateTimeChecker.ValidationResult result) {
        if (result.getErrorMessage().contains("must be a number")) {
            showErrorMessage(result.getErrorMessage());
        } else {
            showOutOfRangeError(result.getErrorMessage());
        }
    }

    private void validateAndShowDateResult(int day, int month, int year) {
        if (DateTimeChecker.isValidDate(day, month, year)) {
            showValidDateMessage(day, month, year);
        } else {
            showInvalidDateMessage(day, month, year);
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showOutOfRangeError(String message) {
        JOptionPane.showMessageDialog(this, message, "Out of Range Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showValidDateMessage(int day, int month, int year) {
        String dateString = String.format("%02d/%02d/%04d", day, month, year);
        JOptionPane.showMessageDialog(this,
            dateString + " is a valid date",
            "Valid Date",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInvalidDateMessage(int day, int month, int year) {
        String dateString = String.format("%02d/%02d/%04d", day, month, year);
        JOptionPane.showMessageDialog(this,
            dateString + " is not a valid date",
            "Invalid Date",
            JOptionPane.WARNING_MESSAGE);
    }

    private void confirmExit() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Do you want to exit?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
