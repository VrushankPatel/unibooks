package com.omexy.unibooks.ui;

import com.omexy.unibooks.eventhandler.VendorListener;
import com.omexy.unibooks.model.Expense;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Panel for managing expense information.
 * Allows adding, displaying, and validating expense details.
 * 
 * @author VrushankPatel
 * @purpose Manage expense data in the application.
 */
public class ExpensePanel extends JPanel implements VendorListener {

    private JComboBox<String> vendorComboBox;
    private JTextField categoryField;
    private JTextField amountField;
    private JDateChooser dateChooser;
    private JTextArea notesField;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private List<Expense> expenses = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public ExpensePanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel vendorLabel = new JLabel("Vendor:");
        vendorComboBox = new JComboBox<>();
        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField(15);
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(15);
        JLabel dateLabel = new JLabel("Expense Date:");
        dateChooser = new JDateChooser();
        dateChooser.setLocale(Locale.US);
        JLabel notesLabel = new JLabel("Notes:");
        notesField = new JTextArea(3, 20);
        JScrollPane notesScrollPane = new JScrollPane(notesField);
        JButton createExpenseButton = new JButton("Create Expense");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(vendorLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(vendorComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(amountLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(notesLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        inputPanel.add(notesScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        inputPanel.add(createExpenseButton, gbc);

        // JTable setup
        String[] columnNames = {"Vendor", "Category", "Amount", "Date", "Notes"};
        tableModel = new DefaultTableModel(columnNames, 0);
        expenseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(expenseTable);

        createExpenseButton.addActionListener(e -> {
            String vendor = (String) vendorComboBox.getSelectedItem();
            String category = categoryField.getText();
            String amountText = amountField.getText();
            Date expenseDate = dateChooser.getDate();
            String notes = notesField.getText();

            if (vendor == null || category.trim().isEmpty() || amountText.trim().isEmpty() || expenseDate == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields (Vendor, Category, Amount, Date).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Expense newExpense = new Expense(vendor, category, amount, expenseDate, notes);
                expenses.add(newExpense);
                addExpenseToTable(newExpense);
                clearInputFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Adds an expense to the table.
     * 
     * @param expense The expense to add.
     */
    private void addExpenseToTable(Expense expense) {
        Object[] rowData = {expense.getVendor(), expense.getCategory(), expense.getAmount(), dateFormat.format(expense.getExpenseDate()), expense.getNotes()};
        tableModel.addRow(rowData);
    }

    /**
     * Clears the input fields.
     */
    private void clearInputFields() {
        vendorComboBox.setSelectedItem(null);
        categoryField.setText("");
        amountField.setText("");
        dateChooser.setDate(null);
        notesField.setText("");
    }

    /**
     * Clears all expenses from the table.
     */
    public void clearExpenses() {
        expenses.clear();
        tableModel.setRowCount(0);
    }

    /**
     * Adds an expense to the list and table.
     * 
     * @param expense The expense to add.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        addExpenseToTable(expense);
    }

    /**
     * Gets the list of expenses.
     * 
     * @return The list of expenses.
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Updates the vendor list in the dropdown.
     * 
     * @param vendorNames The list of vendor names with IDs.
     */
    @Override
    public void vendorsUpdated(List<String> vendorNames) {
        vendorComboBox.removeAllItems();
        for (String name : vendorNames) {
            vendorComboBox.addItem(name);
        }
    }
} 