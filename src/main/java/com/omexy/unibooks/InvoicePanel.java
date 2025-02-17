package com.omexy.unibooks;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvoicePanel extends JPanel implements CustomerListener {

    private JTextField invoiceNumberField;
    private JComboBox<String> customerComboBox;
    private JTextField amountField;
    private JDateChooser dateChooser;
    private JTextArea descriptionField;
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private List<Invoice> invoices = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public InvoicePanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel invoiceNumberLabel = new JLabel("Invoice No.:");
        invoiceNumberField = new JTextField();
        JLabel customerLabel = new JLabel("Customer:");
        customerComboBox = new JComboBox<>();
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();
        JLabel dateLabel = new JLabel("Invoice Date:");
        dateChooser = new JDateChooser();
        dateChooser.setLocale(Locale.US);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextArea(3, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
        JButton createInvoiceButton = new JButton("Create Invoice");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(invoiceNumberLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(invoiceNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(customerLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(customerComboBox, gbc);

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
        inputPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        inputPanel.add(descriptionScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        inputPanel.add(createInvoiceButton, gbc);

        // JTable setup
        String[] columnNames = {"Invoice No.", "Customer", "Amount", "Date", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0);
        invoiceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(invoiceTable);

        createInvoiceButton.addActionListener(e -> {
            String invoiceNumber = invoiceNumberField.getText();
            String customer = (String) customerComboBox.getSelectedItem();
            String amountText = amountField.getText();
            Date invoiceDate = dateChooser.getDate();
            String description = descriptionField.getText();

            if (invoiceNumber.trim().isEmpty() || customer == null || amountText.trim().isEmpty() || invoiceDate == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields (Invoice No., Customer, Amount, Date).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Invoice newInvoice = new Invoice(invoiceNumber, customer, amount, invoiceDate, description);
                invoices.add(newInvoice);
                addInvoiceToTable(newInvoice);
                clearInputFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addInvoiceToTable(Invoice invoice) {
        Object[] rowData = {invoice.getInvoiceNumber(), invoice.getCustomer(), invoice.getAmount(), dateFormat.format(invoice.getInvoiceDate()), invoice.getDescription()};
        tableModel.addRow(rowData);
    }

    private void clearInputFields() {
        invoiceNumberField.setText("");
        customerComboBox.setSelectedItem(null);
        amountField.setText("");
        dateChooser.setDate(null);
        descriptionField.setText("");
    }

    public void clearInvoices() {
        invoices.clear();
        tableModel.setRowCount(0);
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
        addInvoiceToTable(invoice);
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    @Override
    public void customersUpdated(List<String> customerNames) {
        customerComboBox.removeAllItems();
        for (String name : customerNames) {
            customerComboBox.addItem(name);
        }
    }
} 